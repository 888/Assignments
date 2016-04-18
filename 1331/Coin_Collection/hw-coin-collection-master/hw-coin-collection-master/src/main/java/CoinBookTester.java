/**
 * A tester provided for you to test CoinBook and different Coins.
 * You can add more tests if you wish.
 *
 * @author xhan65
 */
public class CoinBookTester {

    /**
     * Main method to test all the Coins and CoinBook
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Coin c1 = new Penny(1995);
        Coin c2 = new Penny(1997);
        Coin c3 = new Nickel(1996);
        Coin c4 = new Nickel(1980);
        Coin c5 = new Dime(2013);
        Coin c6 = new Dime(1908);
        Coin c7 = new MagicCoin(1995);
        Coin c8 = new MagicCoin(1988);
        Coin c9 = new MagicCoin(2007);
        Coin c10 = new Quarter(2013, State.GEORGIA);
        Coin c11 = new Quarter(1997, State.TEXAS);
        Coin c12 = new Quarter(1980, State.VIRGINIA);
        Coin c13 = new Quarter(1980, State.CALIFORNIA);
        Coin c14 = new Quarter(1995, State.GEORGIA);

        Coin[] coins = new Coin[]{c1, c2, c3, c4, c5, c6, c7,
            c8, c9, c10, c11, c12, c13, c14};

        CoinBook collector = new CoinBook();
        for (Coin c: coins) {
            collector.addCoin(c);
        }

        System.out.println(collector.sortByValue());
        System.out.println(collector.sortByYear());
    }
}
