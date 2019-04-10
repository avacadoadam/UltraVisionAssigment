package Customer.Card;

import errors.CardSecurityError;
import errors.InvalidCard;

import java.math.BigDecimal;

/**
 * *
 * * A simple class to reprsent how a card payment may be processed and may return life like exceptions
 * * this payment System will most likely be access through some kind of API or invoice system
 * *
 */
public class CreditCard extends Card {

    public CreditCard(Long cardNumber) throws InvalidCard {
        super(cardNumber,"Credit Card");
    }

    @Override
    public void requestRefund(BigDecimal amountEuros, CardPaymentCallback callback) {
        boolean b = true;
        if (b) callback.successfullyPayment();
        else callback.unsuccessfulyPayment();
    }

    @Override
    public void requestPayment(BigDecimal amountEuros, CardPaymentCallback callback) {
        boolean b = true;
        if (b) callback.successfullyPayment();
        else callback.unsuccessfulyPayment();
    }

    @Override
    public boolean requestRefund(BigDecimal amountEuros) throws CardSecurityError {
        return true;
    }

    @Override
    public boolean requestPayment(BigDecimal amountEuros) throws CardSecurityError {
        return true;
    }


}


