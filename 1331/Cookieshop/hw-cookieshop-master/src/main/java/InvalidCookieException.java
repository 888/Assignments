/**
*@author achiang31
*/

public class InvalidCookieException extends Exception {
    /**
    *InvalidCookieException's no-arg constructor
    */
    public InvalidCookieException() {
        this("This Cookie is not on our menu...");
    }
    /**
    *InvalidCookieException's one-arg constructor
    *@param message     the exception's message
    */
    public InvalidCookieException(String message) {
        super(message);
    }

}