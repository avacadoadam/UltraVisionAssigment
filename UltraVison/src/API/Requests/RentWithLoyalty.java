package API.Requests;

import API.ServerCallback;
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


/**
 * Will check if Customer can rent that title with locality points
 * then update the database returning the return date
 * Take customerID ID of customer that will be on his membership card
 * Take productID ID of product will be on barcode
 * Takes returns the return date
 */
public class RentWithLoyalty extends Request {
    private int customerID, productID;

    public RentWithLoyalty(ServerCallback serverCallback, BaseDatabase databaseInterface) {
        super(serverCallback, databaseInterface);
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
            e.printStackTrace();
            sendError("Couldn't get Customer Data");
            return false;
        } catch (InvalidCard invalidCard) {
            sendError("invalid card");
            return false;
        }
        Title title;
        try {
            title = databaseInterface.getTitleData(productID);
            if (title == null) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            sendError("error get title Data");
            return false;
        } catch (ParseException e) {
            sendError("couldn't parse date");
            return false;
        }
        if (customer.rentWithLoyaltyPoints()) {
            return true;
        } else {
            sendError("You do not have enough loyalty points you have: "+customer.getMembershipCard().getLoyaltyPoints());
            return false;
        }
    }

    @Override
    protected void perform() {
        String returnDate = TimeConversions.returnDate(3);
        try {
            databaseInterface.executeCommand(DatabaseCommands.createRental(customerID, productID));
            databaseInterface.executeCommand(DatabaseCommands.updateTitleRented(customerID, true));
            databaseInterface.executeCommand(DatabaseCommands.updateLoyaltyPoints(customerID,-100));
            sendSuccess("Created Rental with loyalty");
        } catch (SQLException e) {
            sendError("Error connecting to database");
        }

    }
}