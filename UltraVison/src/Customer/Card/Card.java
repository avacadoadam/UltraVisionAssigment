package Customer.Card;

import errors.CardSecurityError;
import errors.InvalidCard;

import java.math.BigDecimal;

/**
 * A placeholder for furture to allow implementation of card Information this Information should be encrypted even in RAM to ensure that
 * bufferOverflow attacks could not simply extract this information.
 *
 * To create a Card the type of you card must be added to CardType enum then passed to super along with the card number
 */

public abstract class Card {


    private Long cardNumber;
    private String cardType;

    public Card(Long cardNumber,String cardType)throws InvalidCard{
        if(!isValid(cardNumber))throw new InvalidCard();
        this.cardType =cardType;
        this.cardNumber = cardNumber;
    }

    public abstract void requestRefund(BigDecimal amountEuros, CardPaymentCallback callback);

    public abstract void requestPayment(BigDecimal amountEuros, CardPaymentCallback callback);

    public abstract boolean requestRefund(BigDecimal amountEuros) throws CardSecurityError;

    public abstract boolean requestPayment(BigDecimal amountEuros) throws CardSecurityError;

    public Long getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    /**
     * Used to build Card from string type and number and insure correct data
     * @param cardType
     * @return correct type of CardType
     */
    public static Card CardFactory(String cardType,Long cardNumber) throws InvalidCard {
        switch (cardType.toLowerCase()) {
            case "credit":
                return new CreditCard(cardNumber);
            case "debit":
                return new DebitCard(cardNumber);
            case "visa":
                return new Visa(cardNumber);
            default:
                throw new InvalidCard();
        }
    }

    // Return true if the card number is valid
    public static boolean isValid(long number)
    {
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    // Get the result from Step 2
    private static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    private static int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    // Return sum of odd-place digits in number
    private static int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    // Return true if the digit d is a prefix for number
    private static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    // Return the number of digits in d
    private static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    private static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}
//TODO make card somekind of enum with customer Functions