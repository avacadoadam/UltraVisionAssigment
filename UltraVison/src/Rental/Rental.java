package Rental;

import java.time.LocalDate;

public class Rental {

    private int rentalID;
    private LocalDate dateRented;
    private LocalDate dateReturned;
    private boolean returned;
    private int titleID;
    private int customerID;

    public Rental(int rentalID, LocalDate dateRented, LocalDate dateReturned, boolean returned, int titleID, int customerID) {
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

    public LocalDate getDateRented() {
        return dateRented;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public boolean isReturned() {
        return returned;
    }

    public int getTitleID() {
        return titleID;
    }
}
