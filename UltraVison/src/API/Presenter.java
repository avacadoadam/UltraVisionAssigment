package API;

import Conversions.TimeConversions;
import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Customer.MembershipCard.ChangeAccessPlanCallback;
import Database.BaseDatabase;
import Rental.Rental;
import Titles.ProductType;
import Titles.Title;
import errors.*;

import java.math.BigDecimal;
import java.sql.SQLException;

import static Customer.Customer.validateFname;
import static Customer.Customer.validateLame;

public class Presenter {

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
            Long cardNumber = Long.valueOf(cardNum);
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
    public String rentwithloyaltypoints(int customerID, int productID) throws Exception, InsuffientLoyaltyPoints, InvalidCard {
        Customer customer = database.getCustomerData(customerID);
        Title product = database.getTitleInformation(productID);
        String returnDate = TimeConversions.returnDate(3);
        if (customer.rentWithLoyaltyPoints()) {
            database.rentWithLoyaltyPoints(customerID, productID, TimeConversions.returnTodayDate(), returnDate);
        } else {
            throw new InsuffientLoyaltyPoints();
        }
        return returnDate;
    }

    public String rentOutWithAccessPlan(int customerID, int titleID) throws Exception, InvalidCard, AccessLevelCantRent {
        Customer customer = database.getCustomerData(customerID);
        Title title = database.getTitleInformation(titleID);
        String returnDate = TimeConversions.returnDate(3);
        if (customer.rentWithMemebershipCard(title)) {
            database.rent(customerID, titleID, TimeConversions.returnTodayDate(), returnDate);
        } else {
            throw new AccessLevelCantRent("You have to change your AccessLevel to " + title.getProductType().getType());
        }
        return returnDate;

    }


    public boolean returnProduct(int productID) throws Exception, InvalidCard {

        Rental rental = database.getRentalInformation(productID);
        long dateLate;
        if ((dateLate = TimeConversions.numberOfDaysLate(rental.getDateReturned())) > 0) {
            double lateFee = 0.50f * dateLate;
            BigDecimal Fee = new BigDecimal(lateFee);
            Customer customer = database.getCustomerData(rental.getCustomerID());
            customer.getMembershipCard().getCard().requestPayment(Fee);
            database.updateCustomerLoyaltyPoints(rental.getCustomerID(), -20);
        } else {
            database.updateCustomerLoyaltyPoints(rental.getCustomerID(), 20);
        }
        return database.returnRental(productID);
    }

    public int createTitle(String titleName,String typeOfMovie,String yearOfRelease) throws Exception {
       return database.createNewTitle(new Title(0,titleName,
                TimeConversions.ConvertDOB(yearOfRelease),
                ProductType.IdentifyFromString(typeOfMovie),
                false));
    }


    public boolean updateCustomerAccessPlan(int customerID,String accessPlan) throws InvalidCard, SQLException, CardSecurityError {
        AccessPlan accessPlan1 = AccessPlan.valueOf(accessPlan);

        Customer customer = database.getCustomerData(customerID);
        if(customer.getMembershipCard().changeAccessPlan(accessPlan1)){
                database.updateCustomerAccessPlan(customerID,accessPlan1);
                return true;
        }
        return false;
    }

    public boolean updateCustomerAddress(int customerID,String address) throws SQLException {
            return database.updateCustomerAddress(customerID,address);
    }

    public boolean updateCustomerCard(int customerID,String cardType,String cardNumber) throws InvalidCard, SQLException {
        Card card = Card.CardFactory(cardType,Long.parseLong(cardNumber));
        return database.updateCustomerCard(customerID,card);
    }
}
