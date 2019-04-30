package API.Requests;

import API.APIInterface;
import Conversions.TimeConversions;
import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Customer.MembershipCard.MembershipCard;
import Database.BaseDatabase;
import Database.DatabaseCommands;
import errors.CardSecurityError;
import errors.CustomerAccountInformationError;
import errors.InvalidCard;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;



/**
 * If any perimeters are not set or invalid a CustomerAccountInformationError error will be throw detailing why
 * however if the database fails or query fails a SQLException will be thrown
 * @takes fname first name of customer
 * @takes lname last name of customer
 * @takes DOB   Date of birth of customer
 * @takes cardNum  Card Number of customer
 * @takes accessPlan Access Plan customer choose
 * @takes cardType Type of card customer is registering
 * @return the ID of customer from database
 * @throws CustomerAccountInformationError If Customer Information is incorrect
 * @throws SQLException If connection or query to database failed in some way
 * @throws CardSecurityError  If there was a problem with card details
 */
public class CreateUser extends Request {




    private String fname = "";
    private String lname = "";
    private String DOB = "";
    private String address = "";
    private String cardType = "";
    private long cardNumber;
    private AccessPlan accessPlan;
    private Card card;

    public CreateUser(APIInterface apiInterface, BaseDatabase databaseInterface) {
        super(apiInterface, databaseInterface);
    }


    @Override
    protected void decodeParameters(JSONObject parameters) {
        try {
            fname = parameters.getString("fname");
        } catch (JSONException e) {
            sendError("could not get fname");
            return;
        }
        try {
            lname = parameters.getString("lname");
        } catch (JSONException e) {
            sendError("could not get lname");
            return;
        }
        try {
            DOB = parameters.getString("DOB");
        } catch (JSONException e) {
            sendError("could not get DOB");
            return;
        }
        try {
            address = parameters.getString("address");
        } catch (JSONException e) {
            sendError("could not get address");
            return;
        }
        try {
            cardType = parameters.getString("cardType");
        } catch (JSONException e) {
            sendError("could not get cardType");
            return;
        }
        try {
            String cardNumberString = parameters.getString("cardNumber");
            cardNumber = Long.parseLong(cardNumberString);
        } catch (JSONException e) {
            sendError("could not get cardNumber");
            return;
        }
        try {
            String plan = parameters.getString("accessPlan");
            accessPlan = AccessPlan.valueOf(plan);
        } catch (JSONException e) {
            sendError("could not get accessPlan");
            return;
        }
        isValidInput = true;
    }

    @Override
    protected boolean validate() {
        if (!Customer.validateName(fname) && !Customer.validateName(lname)) {
            return false;
        }


        try {
            TimeConversions.ConvertDOB(DOB);
        } catch (ParseException e) {
            sendError("date of birth invalid format must be dd-MM-yyyy");
            return false;
        }
        //checks card information
        try {
            card = Card.CardFactory(cardType, cardNumber);
        } catch (InvalidCard invalidCard) {
            sendError(invalidCard.getMessage());
            return false;
        }
        //request payment from card for accessplan
        try {
            card.requestPayment(accessPlan.getPrice());
        } catch (CardSecurityError cardSecurityError) {
            sendError("Customer card could not complete transcation of " + accessPlan.getPrice().toPlainString());
            cardSecurityError.printStackTrace();
        }

        Customer customer = new Customer(new MembershipCard(0, card, accessPlan),
                fname,
                lname,
                DOB,
                address);

        return true;
    }

    @Override
    protected void perform() {
        try {
            databaseInterface.executeCommand(DatabaseCommands.createCustomer(fname, lname, DOB, address, accessPlan, card));
        } catch (SQLException e) {
            sendError("Could not process request to database");
        }
    }
}
