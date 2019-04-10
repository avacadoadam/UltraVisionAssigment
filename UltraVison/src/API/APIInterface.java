package API;

import org.json.JSONObject;

public interface APIInterface {


    void sendErrors(String[] errors);
    void sendError(String error);
    void request(JSONObject obj);
    void output(JSONObject obj);


}
