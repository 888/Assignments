/**
 * ConnectionFailedException gets thrown
 * by the server when something goes wrong.
 *
 * @author dmeyer31
 */
public class ConnectionFailedException extends ServerException {
    /**
     * Creates a new ServerException with the given message.
     * @param  msg The message of the exception.
     */
    public ConnectionFailedException(String msg) {
        super(msg);
    }

    /**
     * Creates a new ServerException.
     */
    public ConnectionFailedException() {
        super();
    }
}
