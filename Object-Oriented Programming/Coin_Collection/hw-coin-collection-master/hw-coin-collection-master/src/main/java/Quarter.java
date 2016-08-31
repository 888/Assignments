/**
*subclass of Coin, of value 25
*@author achiang31
*/
public class Quarter extends Coin {
    private State state;
    /**
    *Quarter's constructor
    *@param year    the year of the Quarter
    *@param state   the state of the Quarter
    */
    public Quarter(int year, State state) {
        super(25, year);
        this.state = state;
    }

    /**
    *getter method for State
    *@return state of the Quarter
    */
    public State getState() {
        return this.state;
    }

    /**
    *converts a Quarter into its String representation
    *@return a String version of Quarter
    */
    @Override
    public String toString() {
        return ("Quarter: " + this.getValue() + " " + this.getYear() + " "
            + this.getState());
    }

    /**
    *compares two Quarters to see which is greater
    *@param other   the Quarters compared to this Quarters
    *@return a positive number when this is greater than other,
    *a negative when this is less than other,
    *and zero when the two Quarterss are equal
    *@throws NullPointerException when the specified Quarters is null
    *@throws ClassCastException if the specified Quarters's type is incompatible
    */
    @Override
    public int compareTo(Coin other) throws NullPointerException,
        ClassCastException {
        if (!(other instanceof Quarter)) {
            return super.compareTo(other);
        }
        Quarter that = (Quarter) other;
        if (this.getState().compareTo(that.getState()) > 0) {
            return 1;
        } else if (this.getState().compareTo(that.getState()) < 0) {
            return -1;
        } else if (this.getState().compareTo(that.getState()) == 0
            && this.getYear() < that.getYear()) {
            return -1;
        } else if (this.getState().compareTo(that.getState()) == 0
            && this.getYear() > that.getYear()) {
            return 1;
        }
        return 0;
    }

    /**
    *compares two Quarters and a specified object to determine equality
    *@param other   the specified object to be compared with this Quarters
    *@return true if the specified object is equal to this Quarters
    */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof Quarter)) {
            return false;
        }
        Quarter that = (Quarter) other;
        if (this.getState() == that.getState()
            && this.getYear() == that.getYear()) {
            return true;
        }
        return false;
    }
}