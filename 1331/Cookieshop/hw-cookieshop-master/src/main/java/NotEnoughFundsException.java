/**
*thrown when user doesn't have enough money
*@author achiang31
*/

public class NotEnoughFundsException extends PaymentFailedException {
    /**
    *NotEnoughFundsException's constructor
    *@param message     the exception's message
    */
    public NotEnoughFundsException(String message) {
        super(message);
    }
}