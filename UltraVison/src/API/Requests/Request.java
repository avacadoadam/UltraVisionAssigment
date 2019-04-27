package API.Requests;


import API.APIInterface;
import API.Presenter;
import org.json.JSONObject;

public abstract class Request {

    private APIInterface apiInterface;
    protected Presenter presenter;

    public Request(APIInterface apiInterface, Presenter presenter) {
        this.apiInterface = apiInterface;
        this.presenter = presenter;
    }

    public abstract void perform(JSONObject parameters);

    protected void output(JSONObject response) {
        apiInterface.output(response);
    }

    protected void sendError(String failure) {
        apiInterface.sendError(failure);
    }


}

