package API.Requests;

import API.APIInterface;
import Conversions.TimeConversions;
import Customer.Customer;
import Database.BaseDatabase;
import Database.DatabaseCommands;
import Rental.Rental;
import errors.CardSecurityError;
import errors.InvalidCard;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;

public class ReturnRental extends Request {

    private int productID;

    public ReturnRental(APIInterface apiInterface, BaseDatabase databaseInterface) {
        super(apiInterface, databaseInterface);
    }


    @Override
    protected void decodeParameters(JSONObject parameters) {
        try {
            productID = parameters.getInt("productID");
            isValidInput = true;
        }catch (JSONException e){
            sendError("productID was not correct should be int");
        }
    }

    @Override
    protected boolean validate() {
        return isValidInput;
    }

    @Override
    protected void perform() {
        try {
            Rental rental = databaseInterface.getRentalData(productID);
            long dateLate;
            if ((dateLate = TimeConversions.numberOfDaysLate(rental.getDateReturned())) > 0) {
                double lateFee = 0.50f * dateLate;
                BigDecimal Fee = new BigDecimal(lateFee);
                Customer customer = databaseInterface.getCustomerData(rental.getCustomerID());
                try {
                    customer.getMembershipCard().getCard().requestPayment(Fee);
                } catch (CardSecurityError cardSecurityError) {
                    sendError("Could not charge late penalty");
                    return;
                }
                databaseInterface.executeCommand(DatabaseCommands.updateLoyaltyPoints(rental.getCustomerID(), -20));
            } else {
                databaseInterface.executeCommand(DatabaseCommands.updateLoyaltyPoints(rental.getCustomerID(), 20));
            }
            databaseInterface.executeCommand(DatabaseCommands.updateTitleRented(productID,false));
            databaseInterface.executeCommand(DatabaseCommands.updateRentalToReturned(productID));
        } catch (SQLException | ParseException e){
            sendError("Error get rental from database");
        } catch (InvalidCard invalidCard) {
            sendError("Invalid customer card");
        }

    }

}
