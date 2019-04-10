package Customer.MembershipCard;

import Customer.AccessPlans.AccessPlan;
import Customer.Card.Card;
import Customer.Card.CardPaymentCallback;
import Titles.ProductType;

import java.math.BigDecimal;

/**
 * Represent a customer MembershipCard that contains information and checks on what and how many Titles he/she can rent.
 */
public class MembershipCard {

    private int loyalityPoints;
    private Card card;
    private AccessPlan accessPlan;


    public MembershipCard(int loyalityPoints, Card card, AccessPlan accessPlan) {
        this.loyalityPoints = loyalityPoints;
        this.card = card;
        this.accessPlan = accessPlan;
    }

    public boolean canRentWithLoyalityPoints(){
        if (loyalityPoints > 100){
            this.loyalityPoints =- 100;
            return true;
        }
        return false;
    }

    public int getMaxRentals(){
        return this.accessPlan.getMaxRentals();
    }

    public boolean canRentWtithAccessLevel(ProductType type) {
        return (accessPlan.canRent(type));
    }

    /**
     * Will Charge Customer only If the desired action plan costs more then the current one.
     * @param desiredAccessPlan the AccessPlan to change to
     *                          callback successfull will be called if payment was accepted and AccessPlan was changed, unsuccessfull otherwise
     */
    public void changeAccessPlan(AccessPlan desiredAccessPlan, final ChangeAccessPlanCallback callback) {

        if (desiredAccessPlan == this.accessPlan) {
            callback.unsuccessfull();
            return;
        }

        BigDecimal costDifference = this.accessPlan.getPrice().subtract(desiredAccessPlan.getPrice());

        if (costDifference.compareTo(BigDecimal.ZERO) > 0) {

            this.card.requestPayment(costDifference, new CardPaymentCallback() {
                @Override
                public void successfullyPayment() {
                    callback.successfull();
                    accessPlan = desiredAccessPlan;

                }

                @Override
                public void unsuccessfulyPayment() {
                    callback.unsuccessfull();
                }
            });
        } else {
            this.accessPlan = desiredAccessPlan;
            callback.successfull();
        }
    }

    private void addLoyalityPointsforRental(){
        this.loyalityPoints =+ 10;
    }






}
