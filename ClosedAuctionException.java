package hw6;

/**
 * The hw6.ClosedAuctionException class handles the hw6.ClosedAuctionException thrown
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class ClosedAuctionException extends Exception {

    /**
     * A method that calls super() to display the error message when the exception is handled
     * @param message
     *      the error message to be displayed
     */
    public ClosedAuctionException(String message) {
        super(message);
    }
}
