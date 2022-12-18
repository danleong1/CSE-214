package hw2;

/**
 * The EmptyListException class handles the EmptyListException thrown.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class EmptyListException extends Exception {

    /**
     * A method that calls super() to handle the exception
     */
    public EmptyListException() {
        super();
    }

    /**
     * A method that calls super() to display the error message when the exception is handled
     * @param message
     *      The error message to be displayed
     */
    public EmptyListException(String message) {
        super(message);
    }
}
