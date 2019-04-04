package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Rental.Rental;
import Titles.ProductType;
import Titles.Title;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * The Template a class that controls accessing the database must abide by to work with system.
 */

public interface BaseDatabase {

    public static final int MAX_NUMBER_OF_DISPLAYED_TITLES = 100;


    /**
     * Will update rental to show that it is not active will then show that title is back into system
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
    public int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan) throws SQLException;

    /**
     * To fill InternalSystem with Information of the customer
     *
     * @param CustomerID
     * @return
     */
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


}

