import java.time.LocalDate;
/**
*@author achiang31
*/
public class CardExpiredException extends PaymentFailedException {
    /**
    *CardExpiredException's constructor
    *@param expiration      date of expiration
    */
    public CardExpiredException(LocalDate expiration) {
        super("Card expired on " + expiration.toString());
    }

}