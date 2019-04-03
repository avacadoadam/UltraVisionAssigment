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

    public Customer(String lname, String DOB, String address,int customerID, String fname, int numOfActiveRentals, AccessPlan plan, Card card) {
        this.customerID = customerID;
        this.fname = fname;
        this.numOfActiveRentals = numOfActiveRentals;
        this.membershipCard = new MembershipCard(card,plan);
        this.lname = lname;
        this.DOB = DOB;
        this.address = address;
    }
    /**
     * First Ensures customer is under Max Rental then checks if there is the memberShipCard has enough loyalty points
     * @param title the ID of the title looking to be rented
     * @return true if can be rented
     */
    public boolean rentWithLoyalityPoints(Title title) {
        if (checkUnderMaxRentals()) {
            return this.membershipCard.canRentWithLoyalityPoints();
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
            return this.membershipCard.canRentWtithAccessLevel(title.getProductType());
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


}
