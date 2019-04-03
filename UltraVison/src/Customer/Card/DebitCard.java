package Customer.Card;

import Customer.Card.CardErrors.CardSecurityError;

import java.math.BigDecimal;

/**
 * *
 * * A simple class to reprsent how a card payment may be processed and may return life like exceptions
 * * this payment System will most likely be access through some kind of API or invoice system
 * *
 */
public class DebitCard extends Card {


    public DebitCard(String cardNumber) {
        super(cardNumber,CardType.Debit);
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
