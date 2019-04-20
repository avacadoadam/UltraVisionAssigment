package API;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * The class responsible for open a SocketAPI and format them requests then to be passed to InternalSystem Class.
 * Theses request will also be string objects.
 */


public class SocketAPI implements Runnable {

    private ServerSocket socket;
    private Presenter system;
    private static boolean SERVER_ON = true;
    private static final int PORT = 3234;
    private ExecutorService pool = Executors.newFixedThreadPool(20);

    public SocketAPI(Presenter system) {
        this.system = system;
        try {
            this.socket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        while (SERVER_ON) {
            try {
                Socket clientSocket = this.socket.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

//
//                while (in.hasNextLine()) {
//                    out.println(in.nextLine().toUpperCase());
//                }
//                this.pool.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
