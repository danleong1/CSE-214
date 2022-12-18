package hw3;

import java.util.Stack;

/**
 * The  NameStack class implements a stack of Integer objects and
 * provides methods to perform various operations on the stack.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class NameStack {
    private Stack<Integer> intStack;

    /**
     * This is a constructor used to create a new  NameStack object.
     */
    public NameStack() {
        Stack<Integer> intStack = new Stack<Integer>();
    }

    /**
     * This is a method that performs the push operation on the  NameStack
     * @param name
     *      the integer to be pushed onto the  NameStack
     */
    public void push(int name) {
        intStack.push(name);
    }

    /**
     * This is a method that performs the pop operation on the  NameStack
     * @return
     *      removes and returns the Integer objects at the top of the  NameStack
     */
    public int pop() {
        return intStack.pop();
    }

    /**
     * This is a method that performs the peek operation on the NameStack
     * @return
     *      returns the  CodeBlock object at the top of the NameStack
     */
    public int peek() {
        return intStack.peek();
    }
}
