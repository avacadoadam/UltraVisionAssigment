package API.Requests;

import API.APIInterface;
import API.Presenter;
import errors.InsuffientLoyaltyPoints;
import errors.InvalidCard;
import org.json.JSONObject;

import java.sql.SQLException;

public class RentWithLoyalty extends Request
{
    public RentWithLoyalty(APIInterface apiInterface, Presenter presenter) {
        super(apiInterface, presenter);
    }

    @Override
    public void perform(JSONObject parameters) {
        try {
            String dueDate = presenter.rentwithloyaltypoints(parameters.getInt("customerID"), parameters.getInt("productID"));
            JSONObject obj = new JSONObject();
            obj.put("success", "true");
            obj.put("returnDate", dueDate);
            output(obj);
        } catch (InsuffientLoyaltyPoints e) {
            sendError(e.getMessage());
        } catch (SQLException e) {
            sendError("Error in database");
        } catch (InvalidCard invalidCard) {
            sendError("Customer must update card");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
