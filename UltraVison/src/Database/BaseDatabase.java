package Database;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Customer;
import Titles.ProductType;
import Titles.Title;

import java.util.Calendar;

/**
 * The Template a class that controls accessing the database must abide by to work with system.
 */

public abstract class BaseDatabase {

    protected DatabaseCommands commands = new DatabaseCommands();

    public abstract boolean returnRental(Title title);

    public abstract boolean rent(Title title, Customer customer);

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
    public abstract int registerCustomer(String fname, String lname, String DOB, String address, Card card, AccessPlan accessPlan);


}
/**
 * A class that holds the SQL string commands that will access the database this allows them to be easly changed in
 * the future
 */
class DatabaseCommands{


    public String createUser(String fname, String lname, String address, ProductType type, Card card){
        String sql = "INSERT INTO customer VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\");";
        return String.format(sql,fname,lname,address,type.getType(),card.getCardType(),card.getCardNumber());
    }

    public String createTitle(String titleName,ProductType type,String yearOfRelease){
        String sql = "INSERT INTO title VALUES (\"%s\",\"%s\",\"%s\");";
        return String.format(sql,titleName,type.getType(),yearOfRelease);
    }

    public String createRental(int customerID,int titleID){
        String sql = "INSERT INTO rentals VALUES (%d,%d,\"%s\",NULL,0);";
        return String.format(sql,customerID,titleID,getDate());
    }

    /**
     * Set a title to either rented or not returned(ie was returned by customer)
     * This function shoudl be called when a title is placed back into stock
     * @param titleID
     * @param rented
     * @return
     */
    public String updateTitleRented(int titleID,boolean rented){
        int a = 0;
        if(rented) a = 1;
        String sql = "UPDATE title SET rented = %d WHERE ID = %d";
        return String.format(sql,titleID,a);
    }

    /**
     * Updates the rental to indicate that it is over
     * @param titleID
     * @return
     */
    public String updateRental(int titleID){
        String sql = "UPDATE rentals SET dateReturned = \"%s\",returned = 1 WHERE title_ID = %d";
        return String.format(sql,getDate(),titleID);
    }

    public String getNumberOfActiveRentals(int customerID){
        String sql = "SELECT SUM(*) FROM rentals WHERE customer_ID = %d AND returned = 0";
        return String.format(sql,customerID);
    }

    public String changeCustomerAccessPlan(int customerID,String newAccessPlan){
        String sql = "UPDATE customer SET accessPlan = \"%s\" WHERE ID = %d";
        return String.format(sql,newAccessPlan,customerID);
    }
    public String changeCustomerAddress(int customerID,String address){
        String sql = "UPDATE customer SET address = \"%s\" WHERE ID = %d";
        return String.format(sql,address,customerID);
    }
    public String changeCustomerDOB(int customerID,String DOB){
        String sql = "UPDATE customer SET DOB = \"%s\" WHERE ID = %d";
        return String.format(sql,DOB,customerID);
    }
    public String changeCustomerCardNumber(int customerID,String cardNumber){
        String sql = "UPDATE customer SET cardNumber = \"%s\" WHERE ID = %d";
        return String.format(sql,cardNumber,customerID);
    }
    public String changeCustomerCardType(int customerID,String cardType){
        String sql = "UPDATE customer SET cardType = \"%s\" WHERE ID = %d";
        return String.format(sql,cardType,customerID);
    }

    private String getDate(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime().toString();
    }


}

