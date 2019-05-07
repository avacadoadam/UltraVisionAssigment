package API;

import org.json.JSONObject;

/**
 * Used for the system to callback the socket that created to inform on response and weather it was a success or a failure
 */
public interface ServerCallback {

    void sendError(String error);
    void output(JSONObject obj);

}
