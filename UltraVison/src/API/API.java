package API;

import API.Requests.Request;
import API.Requests.RequestFactory;
import org.json.JSONObject;


/**
 * The class responsible for open a API and handle them requests. Theses request will also be string objects.
 */


public class API implements APIInterface {

    private Presenter presenter;

    public API(Presenter presenter) {
        this.presenter = presenter;
    }


    public void request(JSONObject incomingRequest){
        String command;

        try {
            command = incomingRequest.getString("command");
        } catch (Exception e) {
            sendError("command isn't a string");
            return;
        }
        Request request = RequestFactory.Factory(this,presenter,command);
        if(request != null){
            request.perform(incomingRequest);
        }else{
            sendError("Unknown Command");
        }


    }

    @Override
    public void sendError(String error) {
        JSONObject obj = new JSONObject();
        obj.put("success", false);
        obj.put("error", error);
        output(obj);
    }

    @Override
    public void output(JSONObject object) {


    }

}
