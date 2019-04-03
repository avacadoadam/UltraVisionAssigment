package Customer.Card;

/**
 * A callback to the payment System as some time may take place
 */
public interface CardPaymentCallback {


    void successfullyPayment();

    void unsuccessfulyPayment();



}
