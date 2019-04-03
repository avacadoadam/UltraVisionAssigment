package Customer.Card.CardErrors;

/**
 *
 * As most Card payment system will now release a boolean weather the card worked or not this class will
 * represent wrong PIN, not amount money etc..
 *
 */

public class CardSecurityError extends Exception{


    public CardSecurityError(String message) {
        super(message);
    }


}
