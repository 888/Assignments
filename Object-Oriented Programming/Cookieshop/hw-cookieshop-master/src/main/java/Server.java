import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Server class represents the convenience store's server. Can be used to get
 * valid items and get location. All methods may throw a ServerException at
 * random, because the server is kind of unreliable.
 *
 * @author dmeyer31
 */
public class Server {
    private static final int PERCENT_REQUESTS_FAIL = 5;

    private static Random r = new Random();

    /**
     * Creates a new Server.
     */
    private Server() {

    }

    /**
     * Simulates a call to the server which may
     * throw a ServerException.
     * @throws ServerException May occur at random.
     */
    private static void doServerThingsAndMaybeFail() throws ServerException {
        if (r.nextInt(100) < PERCENT_REQUESTS_FAIL) {
            throw new ConnectionFailedException("The server encountered a"
                + " wireless connection issue when trying to connect "
                + "to GTWifi.");
        }
    }

    /**
     * Returns an ArrayList of valid items read from the database (csv file).
     * @return ArrayList of valid items.
     * @throws ServerException May occur at random (or if there is an error
     * reading the csv file).
     */
    public static ArrayList<Cookie> getValidCookies() throws ServerException {
        doServerThingsAndMaybeFail();

        try {
            ArrayList<Cookie> cookies = new ArrayList<>();
            Scanner csv = new Scanner(
                    new File("../resources/valid-cookies.csv"));
            csv.useDelimiter("[,\\v] *");

            csv.nextLine(); // Skip header of csv file

            while (csv.hasNext()) {
                Cookie i = new Cookie(csv.next(), csv.nextDouble(),
                    csv.nextDouble());
                csv.nextLine(); // clear new line

                cookies.add(i);
            }
            return cookies;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException("Error processing cookies database "
                + "(csv file).");
        }
    }
}
