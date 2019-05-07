package API;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Server {

    protected static boolean SERVER_ON = true;
    protected static final int PORT = 3234;
    protected static final int MAX_AMOUNT_OF_CLIENTS = 20;

    protected ExecutorService pool = Executors.newFixedThreadPool(Server.MAX_AMOUNT_OF_CLIENTS);

    protected RequestInterface requestInterface;

    public Server(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
        StartServer();
    }

    protected abstract void StartServer();


    protected void StopServers(){
        SERVER_ON = false;
    }

//    public static Server serverBuilder(){
//
//    }
//todo add server builder for coptions from command line




}
