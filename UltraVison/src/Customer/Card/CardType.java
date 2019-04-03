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


    /**
     * Used to indentify Which enum is needed from Sting manly for use in sqlite3 database as there is no Set datatype.
     * @param cardType
     * @return correct type of CardType
     */
    public static CardType IndentifyFromString(String cardType){
        switch (cardType.toLowerCase()) {
            case "credit":
                return CardType.Credit;
            case "debit":
                return CardType.Debit;
            case "visa":
                return CardType.Visa;
            default:
                return null;
        }
    }


}
