package hw3;

/**
 * The  Complexity class implements  Complexity objects and makes their
 * corresponding attributes accessible and mutable.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
  */
public class Complexity {
    private int n_power;
    private int log_power;

    /**
     * This is a constructor used to create a new  Complexity object
     * @param n_power
     *      The exponent which n is exponentiated by
     * @param log_power
     *      The exponent which log(n) is exponentiated by
     */
    public Complexity(int n_power, int log_power) {
        this.n_power = n_power;
        this.log_power = log_power;
    }

    /**
     * A getter method that returns the exponent which n is exponentiated by
     * @return
     *      n_power
     */
    public int getN_power() {
        return n_power;
    }

    /**
     * A getter method that returns the exponent which log(n) is exponentiated by
     * @return
     *      log_power
     */
    public int getLog_power() {
        return log_power;
    }

    /**
     * A mutator method that changes the exponent which n is exponentiated by
     * @param n_power
     *      the new exponent which n is exponentiated by
     */
    public void setN_power(int n_power) {
        this.n_power = n_power;
    }

    /**
     * A mutator method that changes the exponent which log(n) is exponentiated by
     * @param log_power
     *      the new exponent which log(n) is exponentiated by
     */
    public void setLog_power(int log_power) {
        this.log_power = log_power;
    }

    /**
     * A String representation of Big-Oh notation
     * @return
     *      String representation of Big-Oh notation
     */
    public String toString() {
        String output;
        if(n_power==0 && log_power==0)
            output = "O(1)";
        else if(n_power==1 && log_power==1)
            output = "O(n*log(n))";
        else if(n_power>0 && log_power==0)
            if(n_power==1)
                output = "O(n)";
            else
                output = "O(n^" + n_power + ")";
        else if(n_power==0 && log_power>0) {
            if (log_power == 1)
                output = "O(log(n))";
            else
                output = "O(log(n^" + log_power + ")";
        }
        else
            output = "O(n^" + n_power + " * log(n)^" + log_power + ").";
        return output;
    }
}
