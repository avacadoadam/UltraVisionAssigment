package Customer.AccessPlans;

import Titles.ProductType;

import java.math.BigDecimal;

/**
 *
 * A class to represent an accessPlan A customer can buy to determine what type of products they have access to in terms of rent
 * and how many they can rent at any given time.
 */

public abstract class AccessPlan {
//TODO most likly needs to be enum

    private final String accessPlanName;
    private final BigDecimal price;
    private final int maxRentals;


    public AccessPlan(String accessPlanName, BigDecimal price, int maxRentals) {
        this.accessPlanName = accessPlanName;
        this.price = price;
        this.maxRentals = maxRentals;
    }

    public abstract boolean canRent(ProductType type);

    public BigDecimal getPrice(){
        return price;
    }

    public String getAccessPlanName() {
        return accessPlanName;
    }

    public int getMaxRentals() {
        return maxRentals;
    }
}
