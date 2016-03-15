/**
*@author achiang31
*/
public abstract class Card implements PaymentMethod {
    private String ownerName;
    private double balance;
    /**
    *Card's constructor, only used in super calls
    *@param ownerName       name of owner
    *@param balance         balance on card
    */
    public Card(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }
    @Override
    public void pay(double amount)
        throws NotEnoughFundsException, CardExpiredException {
        if (balance > amount) {
            balance = balance - amount;
        } else {
            throw new NotEnoughFundsException("Insuffient balance.");
        }
    }
}