package hw3;

/**
 * The  CodeBlock class implements  CodeBlock objects and makes their
 * corresponding attributes accessible and mutable.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class CodeBlock {
    public static final String[] BLOCK_TYPES = {"def", "for", "while", "if", "elif", "else"};
    public static final int DEF = 0, FOR = 1, WHILE = 2, IF = 3, ELIF = 4, ELSE = 5;
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String name;
    private String loopVariable;

    /**
     * This is a constructor used to create a new  CodeBlock object
     */
    public CodeBlock() {
        loopVariable = null;
    }

    /**
     * A getter method that returns the block complexity
     * @return
     *      blockComplexity
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * A getter method that returns the high sub_complexity
     * @return
     *      highestSubComplexity
     */
    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    /**
     * A getter method that returns the total complexity
     * @param blockComplexity
     *      block complexity
     * @param highestSubComplexity
     *      highest sub_complexity
     * @return
     *      total complexity by combining log_powers and n_powers of block complexity and highest sub_complexity
     */
    public Complexity getTotalComplexity(Complexity blockComplexity, Complexity highestSubComplexity) {
        int newN_power = 0, newLog_power = 0;
//        if(blockComplexity.getLog_power() > 0 && highestSubComplexity.getLog_power() > 0) {    // have log power
//            if(blockComplexity.getN_power() == 0 && highestSubComplexity.getN_power() == 0) {  // have log power, no n power, compare log power
//                newLog_power += blockComplexity.getLog_power() + highestSubComplexity.getLog_power();
//            }
//            else {  // have log power and n power
//                newLog_power += blockComplexity.getLog_power() + highestSubComplexity.getLog_power();
//                newN_power += blockComplexity.getN_power() + highestSubComplexity.getN_power();
//            }
//        }
        newLog_power += blockComplexity.getLog_power() + highestSubComplexity.getLog_power();
        newN_power += blockComplexity.getN_power() + highestSubComplexity.getN_power();
        return new Complexity(newN_power, newLog_power);
    }

    /**
     * A getter method that returns the block's name
     * @return
     *      name
     */
    public String getName() {
        return name;
    }

    /**
     * A getter method that returns the loop variable
     * @return
     *      loop variable
     */
    public String getLoopVariable() {
        return loopVariable;
    }

    /**
     * A mutator method that changes the block complexity
     * @param blockComplexity
     *      the new block complexity
     */
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    /**
     * A mutator method that changes the highest sub_complexity
     * @param highestSubComplexity
     *      the new highest sub_complexity
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
    }

    /**
     * A mutator method that changes the block's name
     * @param name
     *      the block's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A mutator method that changes the loop variable
     * @param loopVariable
     *      the new loop variable
     */
    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }

    /**
     * A String representation of the block, its complexity, and its highest sub_complexity
     * @return
     *      String representation of the block, its complexity, and its highest sub_complexity
     */
    public String toString() {
        String output = String.format("%16s%36s%49s", "BLOCK " + name + ":", "block complexity = "
                + blockComplexity, "highest sub-complexity = " + highestSubComplexity);
        return output;
    }

}