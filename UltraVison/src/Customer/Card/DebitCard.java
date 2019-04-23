package Customer.Card;

import errors.CardSecurityError;
import errors.InvalidCard;

import java.math.BigDecimal;

/**
 * *
 * * A simple class to reprsent how a card payment may be processed and may return life like exceptions
 * * this payment InternalSystem will most likely be access through some kind of SocketAPI or invoice system
 * *
 */
public class DebitCard extends Card {


    DebitCard(Long cardNumber) throws InvalidCard {
        super(cardNumber,"Debit Card");
    }

    @Override
    public void requestRefund(BigDecimal amountEuros, CardPaymentCallback callback) {
        boolean b = true;
        if (b) callback.successfullyPayment();
        else callback.unsuccessfulPayment();

    }

    @Override
    public void requestPayment(BigDecimal amountEuros, CardPaymentCallback callback) {
        boolean b = true;
        if (b) callback.successfullyPayment();
        else callback.unsuccessfulPayment();

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
