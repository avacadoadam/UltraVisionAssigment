package Customer.Card;

import Customer.Card.CardErrors.CardSecurityError;

import java.math.BigDecimal;

/**
 * *
 * * A simple class to reprsent how a card payment may be processed and may return life like exceptions
 * * this payment InternalSystem will most likely be access through some kind of SocketAPI or invoice system
 * *
 */
public class CreditCard extends Card {

    public CreditCard(String cardNumber)
    {
        super(cardNumber,CardType.Credit);
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


}


