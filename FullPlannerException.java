package hw1;

/**
 * The <code>FullPlannerException</code> class handles the FullPlannerException thrown.
 * @author Daniel Leong
 *      114430807
 *      Recitation 02
 **/

public class FullPlannerException extends Exception {

    /**
     * A method that calls super() to handle the exception
     */
    public FullPlannerException(){
        super();
    }

    /**
     * A method that calls super() to display an error message when the exception is handled
     * @param message
     *      the error message to be displayed
     */
    public FullPlannerException(String message) {
        super(message);
    }
}
