/**
*subclass of Coin, of varying value, default 63
*@author achiang31
*/
public class MagicCoin extends Coin {
    private int value;

    /**
    *MagicCoin's first constructor, for just year input
    *@param year    the MagicCoin's year
    */
    public MagicCoin(int year) {
        this(63, year);
    }
    /**
    *MagicCoin's second constructor, for year and value input
    *@param year    the MagicCoin's year
    *@param value   the MagicCoin's value
    */
    public MagicCoin(int value, int year) {
        super(value, year);
        if (value < 26) {
            this.value = 26;
        } else if (value > 100) {
            this.value = 100;
        }
    }
    /**
    *converts a MagicCoin into its String representation
    *@return a String version of MagicCoin
    */
    @Override
    public String toString() {
        return ("MagicCoin: " + this.getValue() + " " + this.getYear());
    }
}