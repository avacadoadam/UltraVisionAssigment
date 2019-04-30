package API.Requests;

import API.APIInterface;
import Customer.Card.Card;
import Database.BaseDatabase;
import Database.DatabaseCommands;
import errors.InvalidCard;
import org.json.JSONObject;

import java.sql.SQLException;

public class ChangeCustomerCard extends Request {

    private int customerID;
    private String cardType;
    private long cardNumber;

    public ChangeCustomerCard(APIInterface apiInterface, BaseDatabase databaseInterface) {
        super(apiInterface, databaseInterface);
    }


    @Override
    protected void decodeParameters(JSONObject parameters) {
        customerID = parameters.getInt("customerID");
        cardType = parameters.getString("cardType");
        String cardNum = parameters.getString("cardNumber");
        this.cardNumber = Long.parseLong(cardNum);
        isValidInput = true;
    }

    @Override
    protected boolean validate() {
        return isValidInput;
    }

    @Override
    protected void perform() {
        try {
            Card card = Card.CardFactory(cardType, cardNumber);
        } catch (InvalidCard invalidCard) {
            sendError("invalid card");
            return;
        }
        try {
            databaseInterface.executeCommand(DatabaseCommands.changeCustomerCardNumber(customerID, String.valueOf(cardNumber)));
            databaseInterface.executeCommand(DatabaseCommands.changeCustomerCardType(customerID, cardType));
        } catch (SQLException e) {
            sendError("could not update database");
            return;
        }
        sendSuccess("change card success");

    }
}
