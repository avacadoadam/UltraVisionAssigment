package API.Requests;

import API.APIInterface;
import Conversions.TimeConversions;
import Customer.Customer;
import Database.BaseDatabase;
import Database.DatabaseCommands;
import Titles.Title;
import errors.InvalidCard;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;

public class RentWithAccess extends Request {

    private int customerID, productID;

    public RentWithAccess(APIInterface apiInterface, BaseDatabase databaseInterface) {
        super(apiInterface, databaseInterface);
    }


    @Override
    protected void decodeParameters(JSONObject parameters) {
        try {
            customerID = parameters.getInt("customerID");
            productID = parameters.getInt("productID");
            isValidInput = true;
        } catch (JSONException e) {
            sendError("Error getting id of product or customer");
        }
    }

    @Override
    protected boolean validate() {
        if (isValidInput = false) {
            return false;
        }
        Customer customer;
        try {
            customer = databaseInterface.getCustomerData(customerID);
            if (customer == null) return false;
        } catch (SQLException e) {
            sendError("error get Customer Data");
            return false;
        } catch (InvalidCard invalidCard) {
            sendError("error parsing date");
            return false;
        }
        Title title;
        try {
            title = databaseInterface.getTitleData(productID);
            if (title == null) return false;
        } catch (SQLException e) {
            sendError("error get title Data");
            return false;
        } catch (ParseException e) {
            sendError("error parsing date");
            return false;
        }
        if (customer.rentWithMemebershipCard(title)) {
            return true;
        } else {
            sendError("You have to change your AccessLevel to " + title.getProductType().getType());
            return false;
        }
    }

    @Override
    protected void perform() {
        String returnDate = TimeConversions.returnDate(3);
        try {
            databaseInterface.executeCommand(DatabaseCommands.createRental(customerID, productID));
            databaseInterface.executeCommand(DatabaseCommands.updateTitleRented(customerID, true));
        } catch (SQLException e) {
            sendError("Error connecting to database");
        }

    }


}
