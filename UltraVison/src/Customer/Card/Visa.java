package Customer.Card;

import errors.CardSecurityError;
import errors.InvalidCard;

import java.math.BigDecimal;

public class Visa extends Card {


     Visa(Long cardNumber) throws InvalidCard {
        super(cardNumber, "visa");
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
