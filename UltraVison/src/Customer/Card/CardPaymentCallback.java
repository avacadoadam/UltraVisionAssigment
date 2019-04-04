package Customer.Card;

/**
 * A callback to the payment InternalSystem as some time may take place
 */
public interface CardPaymentCallback {


    void successfullyPayment();

    void unsuccessfulyPayment();



}
