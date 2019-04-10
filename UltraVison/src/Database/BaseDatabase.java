package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Titles.ProductType;
import Titles.Title;
import errors.CouldNotFindAccessPlan;
import errors.InvalidCard;

import java.sql.SQLException;
/**
 * The Template a class that controls accessing the database must abide by to work with system.
 */

public interface BaseDatabase {

    public static final int MAX_NUMBER_OF_DISPLAYED_TITLES = 100;


    /**
     * Will update rental to show that it is not active will then show that title is back into system
<<<<<<< HEAD
     * @param productID
     * @return
     * @throws SQLException
     */
    public abstract boolean returnRental(int productID) throws SQLException;


    /**
     * Will Create a Entry of the Rental and also update title
     * @param productID
     * @param customerID
     * @return
     * @throws SQLException
     */
    public abstract void rent(int productID, int customerID, String dateRented, String due) throws SQLException;


    public abstract void rentWithLoyaltyPoints(int titleID, int customerID,String dateRented,String due) throws SQLException;
=======
     *
     * @param title
     * @return
     * @throws SQLException
     */
    public boolean returnRental(Title title) throws SQLException;

    /**
     * Will Create a Entry of the Rental and also update title
     *
     * @param title
     * @param customer
     * @return
     * @throws SQLException
     */
    public boolean rent(Title title, Customer customer) throws SQLException;
>>>>>>> 6187c6674570ebebb9a36a09791419f0db2dddaf

    /**
     * Allows the customer to search all titles
     *
     * @return
     * @throws SQLException
     */
    public Title[] ListAvailableTitles() throws SQLException;

    /**
     * Allows the customer to search based of type
     *
     * @param type
     * @return
     * @throws SQLException
     */
    public Title[] ListAvailableTitles(ProductType type) throws SQLException;

    /**
     * Will create a database Entry for the customer then return the ID
     *
     * @param fname
     * @param lname
     * @param DOB
     * @param address
     * @param card
     * @param accessPlan
     * @return
     */
<<<<<<< HEAD
    public abstract int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) throws SQLException;

    /**
     * To fill System with Information of the customer
     * @param CustomerID
     * @return
     */
    public abstract Customer getCustomerData(int CustomerID) throws SQLException, CouldNotFindAccessPlan, InvalidCard;

    /**
     * To get information about a title
     * @param titleID
     * @return
     */
    public abstract Title getTitleInformation(int titleID) throws SQLException;




}

/**
 * A class that holds the SQL string commands that will access the database this allows them to be easly changed in
 * the future
 */
final class DatabaseCommands {


    public String createCustomer(String fname, String lname, String DOB, String address, AccessPlan accessPlan, Card card) {
        String sql = "INSERT INTO customer(fName, lName, DOB, address, accessPlan, cardType, cardNumber) VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\");";
        return String.format(sql, fname, lname, DOB, address, accessPlan.getAccessPlanName(), card.getCardType(), card.getCardNumber());
    }

    public String createTitle(String titleName, ProductType type, String yearOfRelease) {
        String sql = "INSERT INTO title(titleName,typeOfMovie,yearOfRelease) VALUES (\"%s\",\"%s\",v%s\");";
        return String.format(sql, titleName, type.getType(), yearOfRelease);
    }

    public String createRental(int customerID, int titleID,String dateRented,String dateDue) {
        String sql = "INSERT INTO rentals(customer_ID, title_ID, dateRented, dateDue) VALUES (%d,%d,\"%s\",\"%s\");";
        return String.format(sql, customerID,titleID, dateRented, dateDue);
    }

    /**
     * Set a title to either rented or not returned(ie was returned by customer)
     * This function shoudl be called when a title is placed back into stock
     *
     * @param titleID
     * @param rented
     * @return
     */
    public String updateTitleRented(int titleID, boolean rented) {
        int a = 0;
        if (rented) a = 1;
        String sql = "UPDATE title SET rented = %d WHERE ID = %d;";
        return String.format(sql, titleID, a);
    }

    public String updateLoyaltyPoints(int customerID,int amount){
        String sql = "UPDATE customer SET loyaltyPoints  = loyaltyPoints + %d WHERE ID = %d;";
        return String.format(sql, amount, customerID);
    }

    /**
     * Updates the rental to indicate that it is over
=======
    public int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) throws SQLException;

    /**
     * To fill InternalSystem with Information of the customer
>>>>>>> 6187c6674570ebebb9a36a09791419f0db2dddaf
     *
     * @param CustomerID
     * @return
     */
<<<<<<< HEAD
    public String updateRental(int titleID,String dateReturned) {
        String sql = "UPDATE rentals SET dateReturned = \"%s\",returned = 1 WHERE title_ID = %d;";
        return String.format(sql, dateReturned, titleID);
    }

    public String getAllAvaibleTitles() {
        return "SELECT * FROM title WHERE rented = 0;";
    }

    public String getAllAvaibleTitles(String productType) {
        String sql = "SELECT * FROM title WHERE rented = 0 AND typeOfMovie = \"%s\";";
        return String.format(sql, productType);
    }


    public String getNumberOfActiveRentals(int customerID) {
        String sql = "SELECT COUNT(ID) FROM rentals WHERE customer_ID = %d AND returned = 0;";
        return String.format(sql, customerID);
    }

    public String changeCustomerAccessPlan(int customerID, String newAccessPlan) {
        String sql = "UPDATE customer SET accessPlan = \"%s\" WHERE ID = %d;";
        return String.format(sql, newAccessPlan, customerID);
    }

    public String changeCustomerAddress(int customerID, String address) {
        String sql = "UPDATE customer SET address = \"%s\" WHERE ID = %d;";
        return String.format(sql, address, customerID);
    }

    public String changeCustomerDOB(int customerID, String DOB) {
        String sql = "UPDATE customer SET DOB = \"%s\" WHERE ID = %d;";
        return String.format(sql, DOB, customerID);
    }

    public String changeCustomerCardNumber(int customerID, String cardNumber) {
        String sql = "UPDATE customer SET cardNumber = \"%s\" WHERE ID = %d;";
        return String.format(sql, cardNumber, customerID);
    }

    public String changeCustomerCardType(int customerID, String cardType) {
        String sql = "UPDATE customer SET cardType = \"%s\" WHERE ID = %d;";
        return String.format(sql, cardType, customerID);
    }

    public String getCustomerInformation(int customerID){
        String sql = "SELECT * FROM customer WHERE ID = %d;";
        return String.format(sql,customerID);
    }
    public String getProductInformation(int productID){
        String sql = "SELECT * FROM title WHERE ID = %d;";
        return String.format(sql,productID);
    }
    public String getNumberOfRentals(int customerID){
        String sql = "SELECT COUNT(ID) FROM rentals WHERE returned = 0 AND customer_ID = %d;";
        return String.format(sql,customerID);
    }


=======
//    public Customer getCustomerData(String CustomerID) throws SQLException;
//
//    /**
//     * To get information about a title
//     *
//     * @param titleID
//     * @return
//     */
//    public Title getTitleInformation(String titleID);
//
//    /**
//     * To get Information About a rental
//     *
//     * @param titleID
//     * @return
//     */
//    public Rental getRentalInformation(String titleID);
>>>>>>> 6187c6674570ebebb9a36a09791419f0db2dddaf


}

