/**
 * PaymentFailedException gets thrown when an attempt to pay fails.
 *
 * @author dmeyer31
 */
public class PaymentFailedException extends Exception {

    /**
     * Creates a new PaymentFailedException with the given message.
     * @param msg The message of the exception.
     */
    public PaymentFailedException(String msg) {
        super(msg);
    }
}
