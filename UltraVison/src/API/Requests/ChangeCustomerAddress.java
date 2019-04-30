package API.Requests;

import API.APIInterface;
import Database.BaseDatabase;
import Database.DatabaseCommands;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class ChangeCustomerAddress extends Request {

    private int customerID;
    private String address;

    public ChangeCustomerAddress(APIInterface apiInterface, BaseDatabase databaseInterface) {
        super(apiInterface, databaseInterface);
    }


    @Override
    protected void decodeParameters(JSONObject parameters) {
        try {
            customerID = parameters.getInt("customerID");
            address = parameters.getString("address");
            isValidInput = true;
        } catch (JSONException e) {
            sendError("Could not get customer ID or accessPlan");
        }
    }

    @Override
    protected boolean validate() {
        return isValidInput;
    }

    @Override
    protected void perform() {

        try {
            databaseInterface.executeCommand(DatabaseCommands.changeCustomerAddress(customerID, address));
            sendSuccess("change address to " + address);
        } catch (SQLException e) {
            sendError("Could not change address in database");
        }
    }


}
