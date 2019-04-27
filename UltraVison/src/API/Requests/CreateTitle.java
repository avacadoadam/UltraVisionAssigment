package API.Requests;

import API.APIInterface;
import API.Presenter;
import org.json.JSONObject;

public class CreateTitle extends Request {
    public CreateTitle(APIInterface apiInterface, Presenter presenter) {
        super(apiInterface, presenter);
    }

    @Override
    public void perform(JSONObject parameters) {
        try {
            presenter.createTitle(parameters.getString("titleName")
                    ,parameters.getString("typeOfMovie")
                    ,parameters.getString("yearOfRelease"));
        } catch (Exception e) {
            sendError(e.getMessage());
        }
    }
}
