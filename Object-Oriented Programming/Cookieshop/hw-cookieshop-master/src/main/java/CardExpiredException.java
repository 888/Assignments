import java.time.LocalDate;
/**
*exception thrown when expire date is earlier than current date
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