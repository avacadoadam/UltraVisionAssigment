package API;

import org.json.JSONObject;

public interface APIInterface {


    void sendError(String error);
    void output(JSONObject obj);


}
