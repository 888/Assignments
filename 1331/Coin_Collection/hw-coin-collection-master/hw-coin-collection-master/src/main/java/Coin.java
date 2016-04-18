/**
 * Abstract Coin class.
 *
 * @author achiang31
 * @version 1.0
 */
public abstract class Coin implements Comparable<Coin> {
    private int value;
    private int year;

    /**
    *Coin's constructor, used in super() calls
    *@param value    the coin's value
    *@param year    the coin's year
    */
    public Coin(int value, int year) {
        this.value = value;
        this.year = year;
    }

    /**
    *getter method for Coin's value
    *@return int, the value of instance variable value
    */
    public int getValue() {
        return this.value;
    }

    /**
    *getter method for Coin's year
    *@return int, the value of instance variable year
    */
    public int getYear() {
        return this.year;
    }

    /**
    *converts a Coin into its String representation
    *@return a String version of Coin
    */
    @Override
    public String toString() {
        return ("Coin: " + this.getValue() + " " + this.getYear());
    }

    /**
    *compares two Coins to see which is greater
    *@param other   the coin compared to this coin
    *@return a positive number when this is greater than other,
    *a negative when this is less than other,
    *and zero when the two coins are equal
    *@throws NullPointerException when the specified coin is null
    *@throws ClassCastException if the specified coin's type is incompatible
    */
    @Override
    public int compareTo(Coin other) throws NullPointerException,
        ClassCastException {
        if (this.getValue() > other.getValue()) {
            return 1;
        } else if (this.getValue() < other.getValue()) {
            return -1;
        } else if (this.getValue() == other.getValue()
            && this.getYear() < other.getYear()) {
            return -1;
        } else if (this.getValue() == other.getValue()
            && this.getYear() > other.getYear()) {
            return 1;
        }
        return 0;
    }

    /**
    *compares this coin and a specified object to determine equality
    *@param other   the specified object to be compared with this coin
    *@return true if the specified object is equal to this coin
    */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof Coin)) {
            return false;
        }
        Coin that = (Coin) other;
        if (this.getValue() == that.getValue()
            && this.getYear() == that.getYear()) {
            return true;
        }
        return false;
    }

    // This method should not be modified
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + year;
        hash = 31 * hash + value;
        return hash;
    }

}
