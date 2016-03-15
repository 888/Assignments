import java.time.LocalDate;

/**
*@author <achiang31>
*/

public class Demo {
    /**
    * the main method
    *@param args    the default String array, args
    */
    public static void main(String[] args) {
        try {
            System.out.println("Testing valid cookie 1.");
            CheckoutPage page1 = new CheckoutPage();
            Cookie test1 = new Cookie("Mixed Bundle", 12.0, 18.0);
            page1.addToCart(test1);
            System.out.println("You have 1 Mixed Bundle cookie.");
        } catch (InvalidCookieException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Testing valid cookie 2.");
            CheckoutPage page2 = new CheckoutPage();
            Cookie test2 = new Cookie("BigWich Deluxe", 7777777, 12.99);
            page2.addToCart(test2);
            System.out.println("You have 1 BigWich Deluxe cookie.");
        } catch (InvalidCookieException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Testing invalid cookie 1.");
            CheckoutPage page3 = new CheckoutPage();
            Cookie test3 = new Cookie("Muta/Ling/Bane", 200, 200);
            page3.addToCart(test3);
            System.out.println("You have 1 Muta/Ling/Bane cookie.");
        } catch (InvalidCookieException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Testing valid credit card.");
            LocalDate testDate1 = LocalDate.of(2021, 3, 15);
            CreditCard test4 = new CreditCard("Artanis", 1000.0, testDate1);
            test4.pay(10.0);
            System.out.println("Artanis's credit card is valid.");
        } catch (CardExpiredException e) {
            System.out.println(e.getMessage());
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Testing valid credit card w/o enough funds.");
            LocalDate testDate2 = LocalDate.of(2020, 3, 15);
            CreditCard test5 = new CreditCard("Zeratul", 5.0, testDate2);
            test5.pay(10.0);
            System.out.println("Zeratul's credit card is valid.");
        } catch (CardExpiredException e) {
            System.out.println(e.getMessage());
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Testing invalid credit card.");
            LocalDate testDate3 = LocalDate.of(2001, 3, 15);
            CreditCard test6 = new CreditCard("Tassadar", 100.0, testDate3);
            test6.pay(10.0);
            System.out.println("Tassadar's credit card is valid.");
        } catch (CardExpiredException e) {
            System.out.println(e.getMessage());
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Testing valid Venmo.");
            Venmo test7 = new Venmo("Raynor", "Raiders", 100.0);
            test7.pay(10.0);
            System.out.println("Raynor's Venmo is valid.");
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Testing invalid Venmo.");
            Venmo test8 = new Venmo("Mengsk", "Dominion", 1.0);
            test8.pay(10.0);
            System.out.println("Mengsk's Venmo is valid");
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}