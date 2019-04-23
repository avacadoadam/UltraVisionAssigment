package Customer.MembershipCard;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Card.CardPaymentCallback;
import Titles.ProductType;
import errors.CardSecurityError;

import java.math.BigDecimal;

/**
 * Represent a customer MembershipCard that contains information and checks on what and how many Titles he/she can rent.
 */
public class MembershipCard {

    private int localityPoints;
    private Card card;
    private AccessPlan accessPlan;


    public MembershipCard(int localityPoints, Card card, AccessPlan accessPlan) {
        this.localityPoints = localityPoints;
        this.card = card;
        this.accessPlan = accessPlan;
    }

    public boolean canRentWithLoyaltyPoints(){
        if (localityPoints > 100){
            this.localityPoints =- 100;
            return true;
        }
        return false;
    }

    public int getMaxRentals(){
        return this.accessPlan.getMaxRentals();
    }

    public boolean canRentWithAccessLevel(ProductType type) {
        return (accessPlan.canRent(type));
    }

    /**
     * Will Charge Customer only If the desired action plan costs more then the current one.
     * @param desiredAccessPlan the AccessPlan to change to
     *                          callback successful will be called if payment was accepted and AccessPlan was changed, unsuccessfull otherwise
     */
    public boolean changeAccessPlan(AccessPlan desiredAccessPlan) throws CardSecurityError {
        if (desiredAccessPlan == this.accessPlan) {
            return false;
        }
        BigDecimal costDifference = this.accessPlan.getPrice().subtract(desiredAccessPlan.getPrice());
        if (costDifference.compareTo(BigDecimal.ZERO) > 0) {
            this.card.requestPayment(costDifference);
            return true;
        } else {
            this.accessPlan = desiredAccessPlan;
            return true;
        }

    }

    private void addLoyalityPointsforRental(){
        this.localityPoints =+ 10;
    }

    public Card getCard() {
        return card;
    }
}
