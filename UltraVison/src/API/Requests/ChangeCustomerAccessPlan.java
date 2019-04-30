package API.Requests;

import API.APIInterface;
import Customer.AccessPlans.AccessPlan;
import Customer.Customer;
import Database.BaseDatabase;
import Database.DatabaseCommands;
import errors.CardSecurityError;
import errors.InvalidCard;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class ChangeCustomerAccessPlan extends Request {
    private int customerID;
    AccessPlan plan;

    public ChangeCustomerAccessPlan(APIInterface apiInterface, BaseDatabase databaseInterface) {
        super(apiInterface, databaseInterface);
    }


    @Override
    protected void decodeParameters(JSONObject parameters) {
        try {
            customerID = parameters.getInt("customerID");
            String accessplan = parameters.getString("accessPlan");
            plan = AccessPlan.valueOf(accessplan);
            isValidInput = true;
        }catch(JSONException e){
            sendError("Could not get customer ID or accessPlan");
        }
    }

    @Override
    protected boolean validate() {
        return isValidInput;
    }

    @Override
    protected void perform() {
        Customer customer = null;
        try {
            customer = databaseInterface.getCustomerData(customerID);
        } catch (SQLException e) {
            sendError("Could not get Customer Data");
            return;
        } catch (InvalidCard invalidCard) {
            sendError("Could not parse date");
        }
        try {
            if(customer.getMembershipCard().changeAccessPlan(plan)){
                databaseInterface.executeCommand(DatabaseCommands.changeCustomerAccessPlan(customerID,plan.getAccessPlanName()));
            }
        } catch (CardSecurityError cardSecurityError) {
            sendError("Could not process the card payment");
        } catch (SQLException e) {
            sendError("Could not change accessplan in database");
        }


    }


}
