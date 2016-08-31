/**
*a payment type, Venmo, an alternative to credit card
*@author achiang31
*/

public class Venmo implements PaymentMethod {
    private String username;
    private String password;
    private double balance;
    /**
    *Venmo's constructor
    *@param username    account's username
    *@param password    account's password
    *@param balance     account's balance
    */
    public Venmo(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
    @Override
    public void pay(double amount) throws NotEnoughFundsException {
        if (balance > amount) {
            balance = balance - amount;
        } else {
            throw new NotEnoughFundsException("Insuffient balance.");
        }
    }
}