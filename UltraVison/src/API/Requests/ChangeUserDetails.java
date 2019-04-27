package API.Requests;

import API.APIInterface;
import API.Presenter;
import errors.CardSecurityError;
import errors.InvalidCard;
import org.json.JSONObject;

import java.sql.SQLException;

public class ChangeUserDetails extends Request {
    public ChangeUserDetails(APIInterface apiInterface, Presenter presenter) {
        super(apiInterface, presenter);
    }

    @Override
    public void perform(JSONObject parameters) {
        int id = parameters.getInt("customerID");
        switch (parameters.getString("key")) {
            case "accessPlan":
                try {
                    presenter.updateCustomerAccessPlan(id, parameters.getString("accessPlan"));
                } catch (InvalidCard | SQLException | CardSecurityError e) {
                    sendError(e.getMessage());
                    break;
                }
                output(new JSONObject().put("success", "true").put("updated", parameters.getString("key")));
                break;
            case "address":
                try {
                    presenter.updateCustomerAddress(id, parameters.getString("address"));
                } catch (SQLException e) {
                    sendError(e.getMessage());
                    break;
                }
                output(new JSONObject().put("success", "true").put("updated", parameters.getString("key")));
                break;
            case "card":
                try {
                    presenter.updateCustomerCard(id, parameters.getString("cardType"),parameters.getString("cardNumber"));
                } catch (SQLException | InvalidCard e) {
                    sendError(e.getMessage());
                    break;
                }
                output(new JSONObject().put("success", "true").put("updated", parameters.getString("key")));
                break;
            default:
                sendError("Could not determine key or what is trying to be changed");
        }
    }
}
