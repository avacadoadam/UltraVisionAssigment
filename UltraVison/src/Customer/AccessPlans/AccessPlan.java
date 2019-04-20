package Customer.AccessPlans;

import Titles.ProductType;

import java.math.BigDecimal;

/**
 *
 * A class to represent an accessPlan A customer can buy to determine what type of products they have access to in terms of rent
 * and how many they can rent at any given time.
 */

public enum AccessPlan {
//TODO most likly needs to be enum

    TV("TV lovers",BigDecimal.valueOf(30),4){
        @Override
        public boolean canRent(ProductType type) {
            return (type == ProductType.TV);
        }
    },
    VL("Video Lover", BigDecimal.valueOf(20),4){
        @Override
        public boolean canRent(ProductType type) {
            return (type == ProductType.Video);
        }
    },
    ML("Music Lover", BigDecimal.valueOf(10),4){
        @Override
        public boolean canRent(ProductType type) {
            return (type == ProductType.Music);
        }
    },
    PR("Premium", BigDecimal.valueOf(40),4){
        @Override
        public boolean canRent(ProductType type) {
            return true;
        }
    }
    ;

    private final String accessPlanName;
    private final BigDecimal price;
    private final int maxRentals;


    AccessPlan(String accessPlanName, BigDecimal price, int maxRentals) {
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
