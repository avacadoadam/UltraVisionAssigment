package API.Requests;

import API.APIInterface;
import API.Presenter;
import errors.AccessLevelCantRent;
import errors.InvalidCard;
import org.json.JSONObject;

import java.sql.SQLException;

public class RentWithAccess extends Request{


    public RentWithAccess(APIInterface apiInterface, Presenter presenter) {
        super(apiInterface, presenter);
    }

    @Override
    public void perform(JSONObject parameters) {
        try{
            String dueDate = presenter.rentOutWithAccessPlan(parameters.getInt("customerID"), parameters.getInt("productID"));
            JSONObject obj = new JSONObject();
            obj.put("success", "true");
            obj.put("returnDate", dueDate);
            output(obj);
        } catch (SQLException e) {
            sendError("Error in database");
        } catch (AccessLevelCantRent accessLevelCantRent) {
            sendError(accessLevelCantRent.getMessage());
        } catch (InvalidCard invalidCard) {
            sendError("Customer must update card");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
