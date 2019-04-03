package Customer.AccessPlans;

import Titles.ProductType;

import java.math.BigDecimal;

public class ML extends AccessPlan {


    public ML() {
        super("Music Lover", BigDecimal.valueOf(10),4);
    }

    @Override
    public boolean canRent(ProductType type) {
        return (type == ProductType.Music);

    }

}
