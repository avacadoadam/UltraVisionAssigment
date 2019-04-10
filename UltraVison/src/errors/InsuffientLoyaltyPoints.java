package errors;

public class InsuffientLoyaltyPoints extends Throwable{


    public InsuffientLoyaltyPoints() {
        super("Insufficient Loyalty points in account");
    }
}
