package Customer.Card;

import Customer.Card.CardErrors.CardSecurityError;

import java.math.BigDecimal;

/**
 * A placeholder for furture to allow implementation of card Information this Information should be encrypted even in RAM to ensure that
 * bufferOverflow attacks could not simply extract this information.
 *
 * To create a Card the type of you card must be added to CardType enum then passed to super along with the card number
 */

public abstract class Card {


    private String cardNumber;
    private CardType cardType;

    public Card(String cardNumber,CardType cardType) {
        this.cardType =cardType;
        this.cardNumber = cardNumber;
    }

    public abstract void requestRefund(BigDecimal amountEuros, CardPaymentCallback callback);

    public abstract void requestPayment(BigDecimal amountEuros, CardPaymentCallback callback);

    public String getCardNumber() {
        return cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }
}
//TODO make card somekind of enum with customer Functions