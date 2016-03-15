import java.time.LocalDate;

/**
*@author achiang31
*/

public class CreditCard extends Card {
    private LocalDate expirationDate;
    /**
    *CreditCard's constructor
    *@param ownerName       name of owner
    *@param balance         balance on card
    *@param expirationDate  date of expiration
    */
    public CreditCard(String ownerName, double balance,
            LocalDate expirationDate) {
        super(ownerName, balance);
        this.expirationDate = expirationDate;
    }
    @Override
    public void pay(double amount)
        throws CardExpiredException, NotEnoughFundsException {
        if (LocalDate.now().isAfter(expirationDate)) {
            throw new CardExpiredException(expirationDate);
        } else {
            super.pay(amount);
        }
    }
}