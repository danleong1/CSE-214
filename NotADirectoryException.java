package hw5;

/**
 * The hw5.NotADirectoryException class handles the hw5.NotADirectoryException thrown
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class NotADirectoryException extends Exception {

    /**
     * A method that calls super() to display the error message when the exception is handled
     * @param message
     *      the error message to be displayed
     */
    public NotADirectoryException(String message) {
        super(message);
    }
}
