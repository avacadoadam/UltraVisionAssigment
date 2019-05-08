package Database;

import Conversions.TimeConversions;
import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Titles.ProductType;

import java.util.Calendar;

/**
 * A class that holds the SQL string commands that will access the database this allows them to be easly changed in
 * the future
 */
public class DatabaseCommands {


    public static String createCustomer(String fname, String lname, String DOB, String address, AccessPlan accessPlan, Card card) {
        String sql = "INSERT INTO customer(fName, lName, DOB, address, accessPlan, cardType, cardNumber) VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\");";
        return String.format(sql, fname, lname, DOB, address, accessPlan.getAccessPlanName(), card.getCardType(), card.getCardNumber());
    }

    public static String createTitle(String titleName, ProductType type, String yearOfRelease) {
        String sql = "INSERT INTO title(titleName,typeOfMovie,yearOfRelease) VALUES (\"%s\",\"%s\",\"%s\");";
        return String.format(sql, titleName, type.getType(), yearOfRelease);
    }

    public static String createRental(int customerID, int titleID) {
        String sql = "INSERT INTO rentals(customer_ID, title_ID, dateRented,dateReturned,returned) VALUES (%d,%d,\"%s\",NULL,0);";
        return String.format(sql, customerID, titleID, TimeConversions.returnTodayDate());
    }

    public static String updateTitleRented(int titleID, boolean rented) {
        int a = 0;
        if (rented) a = 1;
        String sql = "UPDATE title SET rented = %d WHERE ID = %d;";
        return String.format(sql, titleID, a);
    }

    public static String updateRentalToReturned(int titleID) {
        String sql = "UPDATE rentals SET dateReturned = \"%s\",returned = 1 WHERE title_ID = %d;";
        return String.format(sql, TimeConversions.returnTodayDate(), titleID);
    }

    public static String updateLoyaltyPoints(int customerID, int amount) {
        String sql = "UPDATE customer SET loyaltyPoints  = loyaltyPoints + %d WHERE ID = %d;";
        return String.format(sql, amount, customerID);
    }

    public static String getAllAvaibleTitles() {
        return "SELECT * FROM title WHERE rented = 0";
    }

    public static String getAllAvaibleTitles(String productType) {
        String sql = "SELECT * FROM title WHERE rented = 0 AND typeOfMovie = %s";
        return String.format(sql, productType);
    }


    public static String getNumberOfActiveRentals(int customerID) {
        String sql = "SELECT COUNT(ID) as NumOfRentals FROM rentals WHERE customer_ID = %d AND returned = 0;";
        return String.format(sql, customerID);
    }

    public static String changeCustomerAccessPlan(int customerID, String newAccessPlan) {
        String sql = "UPDATE customer SET accessPlan = \"%s\" WHERE ID = %d;";
        return String.format(sql, newAccessPlan, customerID);
    }

    public static String changeCustomerAddress(int customerID, String address) {
        String sql = "UPDATE customer SET address = \"%s\" WHERE ID = %d;";
        return String.format(sql, address, customerID);
    }

    public static String changeCustomerDOB(int customerID, String DOB) {
        String sql = "UPDATE customer SET DOB = \"%s\" WHERE ID = %d;";
        return String.format(sql, DOB, customerID);
    }

    public static String changeCustomerCardNumber(int customerID, String cardNumber) {
        String sql = "UPDATE customer SET cardNumber = \"%s\" WHERE ID = %d;";
        return String.format(sql, cardNumber, customerID);
    }

    public static String changeCustomerCardType(int customerID, String cardType) {
        String sql = "UPDATE customer SET cardType = \"%s\" WHERE ID = %d;";
        return String.format(sql, cardType, customerID);
    }

    public static String getCustomerInformation(int customerID) {
        String sql = "SELECT * FROM customer WHERE ID = %d;";
        return String.format(sql, customerID);
    }

    public static String getProductInformation(int productID) {
        String sql = "SELECT * FROM title WHERE ID = %d;";
        return String.format(sql, productID);
    }




}


