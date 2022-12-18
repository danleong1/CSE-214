package hw4;

/**
 * The hw4.FullBufferException class handles the hw4.FullBufferException thrown
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class FullBufferException extends Exception {

    /**
     * A method that calls super() to handle the exception
     */
    public FullBufferException() {
        super();
    }

    /**
     * A method that calls super() to display the error message when the exception is handled
     * @param message
     *      the error message to be displayed
     */
    public FullBufferException(String message) {
        super(message);
    }
}
