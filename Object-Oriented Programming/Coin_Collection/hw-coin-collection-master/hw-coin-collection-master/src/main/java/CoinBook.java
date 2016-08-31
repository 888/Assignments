import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collections;

/**
 * A CoinBook which you store all the collected Coins.
 *
 * @author achiang31
 */
public class CoinBook {

    private Set<Coin> coinBook;
    /**
    *CoinBook's constructor
    */
    public CoinBook() {
        coinBook = new MySet<Coin>();
    }

    /**
    *adds a Coin to coinBook
    *@param c   the Coin to be added
    *@return true if the Coin is successfully added
    */
    public boolean addCoin(Coin c) {
        return coinBook.add(c);
    }

    /**
    *removes a Coin from the coinBook
    *@param c   the Coin to be removed
    *@return true if the Coin is successfully removed
    */
    public boolean removeCoin(Coin c) {
        return coinBook.remove(c);
    }

    /**
    *sorts the Coins in the coinBook by value
    *@return a sorted List<Coin> in order of value
    */
    public List<Coin> sortByValue() {
        ArrayList<Coin> valueSorted = new ArrayList<Coin>(coinBook);
        Collections.sort(valueSorted);
        return valueSorted;
    }

    private class YearSort implements Comparator<Coin> {

        /**
        *compares two Coins's years to see which is more recent
        *@param a   a Coin
        *@param b   another Coin
        *@return a positive number when a is greater than b,
        *a negative when a is less than b,
        *and zero when the two coins are equal
        */
        public int compare(Coin a, Coin b) {
            if (a.getYear() > b.getYear()) {
                return 1;
            } else if (a.getYear() < b.getYear()) {
                return -1;
            } else {
                if (a.getValue() > b.getValue()) {
                    return 1;
                } else if (a.getValue() < b.getValue()) {
                    return -1;
                } else if (a.getValue() == b.getValue() && a.getValue() == 25) {
                    Quarter aq = (Quarter) a;
                    Quarter bq = (Quarter) b;
                    if ((aq.getState().compareTo(bq.getState())) > 0) {
                        return 1;
                    } else if ((aq.getState().compareTo(bq.getState())) < 0) {
                        return -1;
                    }
                }
            }
            return 0;
        }
    }
    /**
    *sorts Coins in coinBook by year
    *@return a sorted List<Coin> in order of year
    */
    public List<Coin> sortByYear() {
        ArrayList<Coin> yearSorted = new ArrayList<Coin>(coinBook);
        Collections.sort(yearSorted, new YearSort());
        return yearSorted;
    }

    /**
    *converts coinBook to its String representation
    *@return String versions of all the Coins in coinBook
    */
    @Override
    public String toString() {
        String book = "";
        for (Coin strings : coinBook) {
            book = book + strings.toString() + "\n";
        }
        return book;
    }
}
