package hw3;

import java.util.Stack;

/**
 * The  BlockStack class implements a stack of  CodeBlock objects and
 * provides methods to perform various operations on the stack.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class BlockStack {
    private Stack<CodeBlock> stack;
    private int size;

    /**
     * This is a constructor used to create a new  BlockStack object.
     */
    public BlockStack() {
        Stack<CodeBlock> blockStack = new Stack<>();
    }

    /**
     * This is a method that performs the push operation on the  BlockStack
     * @param block
     *      the  CodeBlock object to be pushed onto the  BlockStack
     */
    public void push(CodeBlock block) {
        stack.push(block);
        size++;
    }

    /**
     * This is a method that performs the pop operation on the  BlockStack
     * @return
     *      removes and returns the  CodeBlock object at the top of the  BlockStack
     */
    public CodeBlock pop() {
        size--;
//        Stack< CodeBlock> temp = stack;
//        stack.pop();
//        return temp.pop();
        return stack.pop();
    }

    /**
     * This is a method that performs the peek operation on the  BlockStack
     * @return
     *      returns the  CodeBlock object at the top of the  BlockStack
     */
    public CodeBlock peek() {
        return stack.peek();
    }

    /**
     * This is a method that returns the size of the  BlockStack
     * @return
     *      size of the  BlockStack
     */
    public int size() {
        return size;
    }

    /**
     * This is a method that determines whether the  BlockStack is empty
     * @return
     *      true if  BlockStack is empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

}

