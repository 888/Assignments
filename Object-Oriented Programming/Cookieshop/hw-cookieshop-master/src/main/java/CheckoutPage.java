import java.util.ArrayList;

/**
*adds cookies to cart and pays for them
*@author achiang31
*/

public class CheckoutPage {
    private ArrayList<Cookie> validCookies;
    private ArrayList<Cookie> cart;
    private boolean again = true;
    /**
    *CheckoutPage's constructor
    */
    public CheckoutPage() {
        while (again) {
            try {
                this.validCookies = Server.getValidCookies();
                again = false;
            } catch (ServerException e) {
                e.getMessage();
            }
        }
        cart = new ArrayList<Cookie>();
    }
    /**
    *@param cookie              cookie to add
    *@throws InvalidCookieException     if the cookie is invalid
    */
    public void addToCart(Cookie cookie) throws InvalidCookieException {
        if (validCookies.contains(cookie)) {
            int index = validCookies.indexOf(cookie);
            cart.add(validCookies.get(index));
        } else {
            throw new InvalidCookieException();
        }
    }
    /**
    *@return the total price of all cookies in the cart
    */
    public double getTotalPrice() {
        double total = 0.0;
        for (Cookie cookie : cart) {
            total = total + cookie.getPrice();
        }
        return total;
    }
    /**
    *@param method      the customer's payment method, either credit or venmo
    *@throws PaymentFailedException     if the transaction can't be completed
    */
    public void payForCart(PaymentMethod method) throws PaymentFailedException {
        method.pay(this.getTotalPrice());
        cart.clear();
    }
}