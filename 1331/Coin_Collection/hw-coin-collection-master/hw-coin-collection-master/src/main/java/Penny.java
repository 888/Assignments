/**
*subclass of Coin, of value 1
*@author achiang31
*/
public class Penny extends Coin {
    /**
    *Penny's constructor
    *@param year    the year of the Penny
    */
    public Penny(int year) {
        super(1, year);
    }

    /**
    *converts a Penny into its String representation
    *@return a String version of Penny
    */
    @Override
    public String toString() {
        return ("Penny: " + this.getValue() + " " + this.getYear());
    }
}