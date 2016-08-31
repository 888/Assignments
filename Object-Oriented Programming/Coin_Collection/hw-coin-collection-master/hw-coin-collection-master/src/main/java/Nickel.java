/**
*subclass of Coin, of value 5
*@author achiang31
*/
public class Nickel extends Coin {
    /**
    *Nickel's constructor
    *@param year    the year of the Nickel
    */
    public Nickel(int year) {
        super(5, year);
    }

    /**
    *converts a Nickel into its String representation
    *@return a String version of Nickel
    */
    @Override
    public String toString() {
        return ("Nickel: " + this.getValue() + " " + this.getYear());
    }
}