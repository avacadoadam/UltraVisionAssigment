package API.Socket;

import API.Server;
import API.ServerCallback;
import API.RequestInterface;
import jdk.nashorn.internal.runtime.ECMAException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * The class is responsible for opening a ServerSocket and handling response, formatting them into JSONOBjects that will then be passed to API.
 */


public class SocketAPI extends Server implements Runnable {


    private ServerSocket socket;

    public SocketAPI(RequestInterface requestInterface) {
        super(requestInterface);

    }

    @Override
    protected void StartServer() {
        System.out.println("Starting socket");
        try {
            this.socket = new ServerSocket(Server.PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        Thread thread = new Thread(this);
        thread.start();
    }
    //todo insert threads and use from pool.

    /**
     * When a client connects to socket the request will be parsed from byte[] to a string then convert into a JSON
     * If the JSON is not formatted correctly a error response will be sent.
     * If it is a thread will be started and then the request will be sent to the API to be processed.
     */
    @Override
    public void run() {
        while (Server.SERVER_ON) {
            try {
                Socket clientSocket = this.socket.accept();
//                InputStream inStream = clientSocket.getInputStream();
//                String jsonString = parseStringFromStream(inStream);
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String str = br.readLine();
                System.out.println("From SERVER " + str);
                SocketRequest callback = new SocketRequest(clientSocket);

                final JSONObject json;
                try {
                    json = new JSONObject(str);
                    requestInterface.request(json, callback);
                } catch (JSONException e) {
                    System.err.println("error parsing json from socket");
                    JSONObject obj = new JSONObject();
                    obj.put("error", "Not valid JSON");
                    callback.output(obj);
                }
            } catch (IOException e) {
                System.err.println("Could not handle response");
                e.printStackTrace();
            }
        }

    }

    /**
     * Parse String from Stream of bytes.
     *
     * @param stream the stream from the clientSocket
     * @return returns the Stream of bytes the client sent in a String object
     * @throws IOException If the stream could not be formatted into a string
     */
    private String parseStringFromStream(InputStream stream) throws IOException {
        System.out.println("parseStringFromStream Called");

        int data;
        InputStreamReader input = new InputStreamReader(stream, StandardCharsets.UTF_8);
        byte[] bytes = new byte[1024];
        int counter = 0;
        while((data= input.read()) != -1){
            bytes[counter] = (byte) data;
            counter++;
        }
        byte[] stringbytes = new byte[counter];
        for (int i = 0; i < counter; i++) {
            stringbytes[i] = bytes[i];
        }

        return new String(stringbytes);
    }


}
