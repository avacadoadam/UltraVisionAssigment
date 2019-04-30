package Customer;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.MembershipCard.MembershipCard;
import Titles.Title;

/**
 * Customer will follow the design pattern of  --- so that
 */


public class Customer {

    private int customerID;
    private MembershipCard membershipCard;
    private String fname,lname,DOB,address;
    private int numOfActiveRentals;
    private boolean rentedWithLoyality = false;

    public Customer(String lname, String DOB, String address,int customerID, String fname, int numOfActiveRentals, AccessPlan plan, Card card,int localityPoints) {
        this.customerID = customerID;
        this.fname = fname;
        this.numOfActiveRentals = numOfActiveRentals;
        this.membershipCard = new MembershipCard(localityPoints,card,plan);
        this.lname = lname;
        this.DOB = DOB;
        this.address = address;
    }

    public Customer(MembershipCard membershipCard, String fname, String lname, String DOB, String address ) {
        this.membershipCard = membershipCard;
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        this.address = address;
        this.numOfActiveRentals = 0;
        this.rentedWithLoyality = false;
    }

    /**
     * First Ensures customer is under Max Rental then checks if there is the memberShipCard has enough loyalty points
     * @return true if can be rented
     */
    public boolean rentWithLoyaltyPoints() {
        if (checkUnderMaxRentals()) {
            return this.membershipCard.canRentWithLoyaltyPoints();
        }
        return false;
    }
    /**
     * calls checkUnderMaxRentals then checks if there is the memberShipCard has enough loyalty points
     * @param title the Title object of the title looking to be rented
     * @return true if can be rented
     */
    public boolean rentWithMemebershipCard(Title title) {
        if (checkUnderMaxRentals()) {
            return this.membershipCard.canRentWithAccessLevel(title.getProductType());
        }
        return false;
    }


    /**
     * Ensures customer is under Max Rental
     * @return true if customer has under 5 rentals
     */
    private boolean checkUnderMaxRentals(){
        return(this.numOfActiveRentals < this.membershipCard.getMaxRentals());
    }

    public int getCustomerID() {
        return customerID;
    }

    public static boolean validateName(String fname) {
        return !fname.contains("[0-9]+");
    }


    public MembershipCard getMembershipCard() {
        return membershipCard;
    }
}
