package API;

import Conversions.TimeConversions;
import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Database.BaseDatabase;
import Titles.Title;
import errors.*;

import java.sql.SQLException;

import static Customer.Customer.validateFname;
import static Customer.Customer.validateLame;

public class Presenter   {

    private BaseDatabase database;


    public Presenter(BaseDatabase database) {
        if (database == null) System.exit(2);
        this.database = database;
    }

    /**
     * If any peremters are not set or invalid a CustomerAccountInformationError error will be throw detailing why
     * however if the database fails or query fails a SQLException will be thrown
     *
     * @param fname
     * @param lname
     * @param DOB
     * @param cardNum
     * @param accessPlan
     * @return
     * @throws CustomerAccountInformationError
     */
    public int newCustomer(String fname, String lname, String DOB, String address, String cardType, String cardNum, String accessPlan) throws CustomerAccountInformationError, SQLException, CardSecurityError {
        if (!validateFname(fname)) throw new CustomerAccountInformationError("first name cannot contain numbers");
        if (!validateLame(lname)) throw new CustomerAccountInformationError("last name cannot contain numbers");
        //checks accessplan
        AccessPlan plan;

        plan = AccessPlan.valueOf(accessPlan);

        //checks date of birth
        try {
            TimeConversions.ConvertDOB(DOB);
        } catch (Exception e) {
            throw new CustomerAccountInformationError("date of birth invalid format must be dd-MM-yyyy");
        }
        //checks card information
        Card card;
        try {
            Long cardNumber = new Long(cardNum);
            card = Card.CardFactory(cardType, cardNumber);
        } catch (InvalidCard invalidCard) {
            throw new CustomerAccountInformationError(invalidCard.getMessage());
        }
        //request payment from card for accessplan
        card.requestPayment(plan.getPrice());
        try {
            return database.registerCustomer(fname, lname, DOB, address, card, plan);
        } catch (SQLException e) {
            throw new SQLException("Could not register Customer Database error");
        }
    }


    /**
     * Will check if Customer can rent that title with loyality points
     * then update the database returning the return date
     *
     * @param customerID
     * @param productID
     * @return due date for rental
     * @throws SQLException
     */
    public String rentwithloyaltypoints(int customerID, int productID) throws SQLException, InsuffientLoyaltyPoints, InvalidCard, CouldNotFindAccessPlan {
        Customer customer = database.getCustomerData(customerID);
        Title product = database.getTitleInformation(productID);
        String returnDate = TimeConversions.returnDate(3);
        if (customer.rentWithLoyalityPoints()) {
            database.rentWithLoyaltyPoints(customerID, productID,TimeConversions.returnTodayDate(),returnDate);
        }else{
            throw new InsuffientLoyaltyPoints();
        }
        return returnDate;
    }

    public boolean rentOutWithAccessPlan(int CustomerID, int ProductID) throws SQLException {
        return false;
    }

    public boolean returnProduct(int productID) {
        return false;
    }


}
