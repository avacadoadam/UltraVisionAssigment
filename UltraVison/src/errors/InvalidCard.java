package errors;

public class InvalidCard extends Throwable {

    public InvalidCard() {
        super("Invalid cardType or Card Number");
    }


}
