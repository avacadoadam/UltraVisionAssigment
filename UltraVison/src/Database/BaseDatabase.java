package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Rental.Rental;
import Titles.ProductType;
import Titles.Title;
import errors.InvalidCard;

import java.sql.SQLException;
/**
 * The Template a class that controls accessing the database must abide by to work with system.
 */

public interface BaseDatabase {

    int MAX_NUMBER_OF_DISPLAYED_TITLES = 100;


    /**
     * Will update rental to show that it is not active will then show that title is back into system
     * @param productID
     * @return
     * @throws SQLException
     */
    boolean returnRental(int productID) throws SQLException;


    /**
     * Will Create a Entry of the Rental and also update title
     * @param productID
     * @param customerID
     * @return
     * @throws SQLException
     */
    void rent(int productID, int customerID, String dateRented, String due) throws SQLException;


    void rentWithLoyaltyPoints(int titleID, int customerID, String dateRented, String due) throws SQLException;


    /**
     * Allows the customer to search all titles
     *
     * @return
     * @throws SQLException
     */
    Title[] ListAvailableTitles() throws SQLException;

    /**
     * Allows the customer to search based of type
     *
     * @param type
     * @return
     * @throws SQLException
     */
    Title[] ListAvailableTitles(ProductType type) throws SQLException;

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
    int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) throws SQLException;

    /**
     * To fill System with Information of the customer
     * @param CustomerID
     * @return
     */
    Customer getCustomerData(int CustomerID) throws SQLException, InvalidCard;

    /**
     * To get information about a title
     * @param titleID
     * @return
     */
    Title getTitleInformation(int titleID) throws Exception;

    Rental getRentalInformation(int rentalID) throws Exception;

    void updateCustomerLoyaltyPoints(int customerID, int amount) throws SQLException;

    int createNewTitle(Title title) throws SQLException;

    boolean updateCustomerAccessPlan(int CustomerID,AccessPlan accessPlan) throws SQLException;
    boolean updateCustomerAddress(int CustomerID,String address) throws SQLException;
    boolean updateCustomerCard(int CustomerID,Card card) throws SQLException;


}

