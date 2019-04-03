package Customer.AccessPlans;

import Titles.ProductType;

import java.math.BigDecimal;

public class PR extends AccessPlan{

    public PR() {
        super("Premium", BigDecimal.valueOf(40),4);
    }

    @Override
    public boolean canRent(ProductType type) {
        return true;
    }
}
