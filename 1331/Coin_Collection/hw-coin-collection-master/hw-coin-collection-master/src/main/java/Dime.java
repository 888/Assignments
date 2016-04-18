/**
*subclass of Coin, of value 10
*@author achiang31
*/
public class Dime extends Coin {
    /**
    *Dime's constructor
    *@param year    the year of the Dime
    */
    public Dime(int year) {
        super(10, year);
    }

    /**
    *converts a Dime into its String representation
    *@return a String version of Dime
    */
    @Override
    public String toString() {
        return ("Dime: " + this.getValue() + " " + this.getYear());
    }
}