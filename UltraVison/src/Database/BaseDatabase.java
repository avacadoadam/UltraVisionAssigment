package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Rental.Rental;
import Titles.ProductType;
import Titles.Title;
import errors.InvalidCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * The Template a class that controls accessing the database must abide by to work with system.
 */

public interface BaseDatabase {

    int MAX_NUMBER_OF_DISPLAYED_TITLES = 100;



    void executeCommand(String SQL) throws SQLException;

    ResultSet excuteQuery(String SQL) throws SQLException;

    Customer getCustomerData(int customerID)throws SQLException, InvalidCard;


    Rental getRentalData(int rentalID) throws SQLException, ParseException;

    Title getTitleData(int titleID) throws SQLException,ParseException;

    Title[] ListAvailableTitles()throws SQLException;

    Title[] ListAvailableTitles(ProductType productType)throws SQLException;





}

