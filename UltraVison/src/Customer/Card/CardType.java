package Customer.Card;

import java.math.BigDecimal;

public enum CardType{
    Credit("Credit"),Debit("Debit"),Visa("Visa");


    private String cardType, cardNumber;

    CardType(String cardType) {
        this.cardType = cardType;

    }

    public String getCardType() {
        return cardType;
    }


}
