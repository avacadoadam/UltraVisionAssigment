package API.Requests;


import API.APIInterface;
import Database.BaseDatabase;
import org.json.JSONObject;

public abstract class Request {

    private APIInterface apiInterface;
    protected BaseDatabase databaseInterface;
    protected boolean isValidInput;

    public Request(APIInterface apiInterface, BaseDatabase databaseInterface) {
        this.apiInterface = apiInterface;
        this.databaseInterface = databaseInterface;
    }

    /**
     * Responsible for extracting the code,validation the storing.
     *
     * @param parameters
     */
    protected abstract void decodeParameters(JSONObject parameters);

    /**
     * Responsible for validating the data and ensuring there was no error in decodeParemter
     */
    protected abstract boolean validate();

    /**
     * Responsible for performing the actions on the database.
     */
    protected abstract void perform();

    /**
     * Enforces a template design pattern for all request so that they first
     * decode incoming API parameters encode in json then store then locally so perform can use them
     *
     * @param parameters the parameters send by the API for a request
     */
    public final void performRequest(JSONObject parameters) {
        decodeParameters(parameters);
        if (validate()) {
            perform();
        }
    }

    protected final void output(JSONObject response) {
        apiInterface.output(response);
    }

    protected final void sendSuccess(String success) {
        isValidInput = true;
        apiInterface.output(new JSONObject().put("success", success));
    }

    protected final void sendError(String failure) {
        isValidInput = false;
        apiInterface.sendError(failure);
    }


}

