package API.Requests;

import API.APIInterface;
import API.Presenter;
import errors.CardSecurityError;
import errors.CustomerAccountInformationError;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class CreateUser extends Request {


    public CreateUser(APIInterface apiInterface, Presenter presenter) {
        super(apiInterface, presenter);
    }

    @Override
    public void perform(JSONObject parameters) {
        int customerID;
        try {
            customerID = presenter.newCustomer(parameters.getString("fname"),
                    parameters.getString("lname"),
                    parameters.getString("DOB"),
                    parameters.getString("address"),
                    parameters.getString("cardType"),
                    parameters.getString("cardNumber"),
                    parameters.getString("accessPlan"));
            //on success
            JSONObject obj = new JSONObject();
            obj.put("success", "true");
            obj.put("customerID", customerID);
            output(obj);
        } catch (JSONException e) {
            sendError("Incorrect perimeters");
        } catch (CustomerAccountInformationError e) {
            sendError(e.getMessage());
        } catch (SQLException e) {
            sendError("Error in database");
        } catch (CardSecurityError cardSecurityError) {
            sendError(cardSecurityError.getMessage());
        }
    }
}
