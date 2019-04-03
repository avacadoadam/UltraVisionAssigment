package Customer.AccessPlans;

import Titles.ProductType;

import java.math.BigDecimal;

public class VL extends AccessPlan {

    public VL() {
        super("Video Lover", BigDecimal.valueOf(20),4);
    }

    @Override
    public boolean canRent(ProductType type) {
        return (type == ProductType.Video);
    }
}
