package Rental;

import java.util.Date;

public class Rental {

    private int rentalID;
    private Date dateRented;
    private Date dateReturned;
    private boolean returned;
    private int titleID;
    private int customerID;

    public Rental(int rentalID, Date dateRented, Date dateReturned, boolean returned, int titleID, int customerID) {
        this.rentalID = rentalID;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
        this.returned = returned;
        this.titleID = titleID;
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getRentalID() {
        return rentalID;
    }

    public Date getDateRented() {
        return dateRented;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public boolean isReturned() {
        return returned;
    }

    public int getTitleID() {
        return titleID;
    }
}
