package API;

import org.json.JSONObject;

public interface RequestInterface {

    void request(JSONObject incomingRequest, ServerCallback serverCallback);

}
