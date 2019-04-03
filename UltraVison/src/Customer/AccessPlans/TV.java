package Customer.AccessPlans;

import Titles.ProductType;

import java.math.BigDecimal;

public class TV extends AccessPlan{


    public TV() {
        super("TV lovers", BigDecimal.valueOf(30),4);
    }

    @Override
    public boolean canRent(ProductType type) {
        return (type == ProductType.TV);
    }


}
