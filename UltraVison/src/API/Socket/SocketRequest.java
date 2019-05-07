package API.Socket;

import API.ServerCallback;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class SocketRequest implements ServerCallback {


private Socket socket;

    public SocketRequest(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void sendError(String error) {
        JSONObject obj = new JSONObject();
        obj.put("success", false);
        obj.put("error", error);
        output(obj);
    }

    @Override
    public void output(JSONObject obj) {
        try {
            String jsonString = obj.toString()+"\n";
            System.out.println("in out +" + jsonString);
            socket.getOutputStream().write(jsonString.getBytes());
            socket.close();
        } catch (IOException e) {
            System.err.println("Failed to sent ");
            e.printStackTrace();
        }

    }
}
