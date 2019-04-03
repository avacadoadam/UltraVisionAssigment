package Rental;

import java.util.Calendar;

public class Rental {

    private int rentalID;
    private Calendar dateRented;
    private Calendar dateReturned;
    private boolean returned;
    private int titleID;
    private int customerID;

    public Rental(int rentalID, Calendar dateRented, Calendar dateReturned, boolean returned, int titleID, int customerID) {
        this.rentalID = rentalID;
        this.dateRented = dateRented;
        this.dateReturned = dateReturned;
        this.returned = returned;
        this.titleID = titleID;
        this.customerID = customerID;
    }
}
