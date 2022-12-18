package hw5;

/**
 * The hw5.FullDirectoryException class handles the hw5.FullDirectoryException thrown
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class FullDirectoryException extends Exception {

    /**
     * A method that calls super() to handle the exception
     */
    public FullDirectoryException() {
        super();
    }

    /**
     * A method that calls super() to display the error message when the exception is handled
     * @param message
     *      the error message to be displayed
     */
    public FullDirectoryException(String message) {
        super(message);
    }
}
