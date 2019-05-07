package API;

import API.Requests.Request;
import API.Requests.RequestFactory;
import API.Socket.SocketAPI;
import Database.BaseDatabase;
import org.json.JSONObject;


/**
 * The class responsible for calling and passing the correct json to the right Classes.
 */

public class API implements RequestInterface {

    private BaseDatabase DB;

    public API(BaseDatabase DB) {
        this.DB = DB;
        initRequestListeners();
    }

    /**
     * Used to initialise all class that create a listen for requests from clients through socket,http etc
     */
    private void initRequestListeners(){
        new SocketAPI(this);
    }

    /**
     * Used to direct the request to the correct request handler
     * @param incomingRequest the JSON sent by the client
     * @param serverCallback the callback for after the request is processed
     */
    public void request(JSONObject incomingRequest, ServerCallback serverCallback){
        String command;
        try {
            command = incomingRequest.getString("command");
        } catch (Exception e) {
            serverCallback.sendError("command isn't a string");
            return;
        }
        Request request = RequestFactory.Factory(serverCallback,DB,command);
        if(request != null){
            request.performRequest(incomingRequest);
        }else{
            serverCallback.sendError("Unknown Command");
        }
    }


}
