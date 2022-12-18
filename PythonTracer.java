package hw3;

import java.io.File;
import java.util.Scanner;

/**
 * The  PythonTracer class implements an application which imports a Python file
 * and determine its overall Big-Oh order of complexity.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class PythonTracer {
    public static final int SPACE_COUNT = 4;
    public CodeBlock oldTop;

    /**
     * This is a method that opens the indicated file, trace through the Python function within the file,
     * and determine details to find its overall order of complexity
     * @param fileName
     *      the name of the file to be traced
     * @return
     *      details of the file trace and the overall complexity of the file
     * @throws IllegalArgumentException
     *      when the fileName is null
     */
    public static Complexity traceFile(String fileName) throws Exception { // taken from hw file
        if(fileName == null)
            throw new IllegalArgumentException("filename is null");
        BlockStack stack = new BlockStack();
        NameStack nameStack = new NameStack();
        int name = 1;
        int namePush_counter = 0;
        Scanner reader = new Scanner(new File(fileName));
        Complexity currComplexity = new Complexity(0, 0);
        CodeBlock block = new CodeBlock();
        Boolean changed = false;
        block.setBlockComplexity(currComplexity);
        block.setHighestSubComplexity(currComplexity);

        while(reader.hasNextLine()) {
            String line = reader.nextLine();
            if(line.trim().isEmpty())
                continue;
            if(line.trim().charAt(0) =='#')
                continue;
            else {
                int indents = line.indexOf(line.trim()) / SPACE_COUNT;  // from HW file
                while(indents < stack.size()) {
                    if(indents==0) {   // outside of def in py file, stops reading
                        reader.close();
                        return stack.peek().getHighestSubComplexity();
                    }
                    else {
                        CodeBlock oldTop = stack.peek();
                        Complexity oldTopComplexity = oldTop.getTotalComplexity(oldTop.getBlockComplexity(), oldTop.getHighestSubComplexity());
                        if(compareOrder(oldTopComplexity, stack.peek().getHighestSubComplexity()) == 1) {
                            stack.peek().setHighestSubComplexity(oldTopComplexity);
                            changed = true;
                        }
                    }
                }
                String[] line_arr = line.trim().split(" ");
                String keyword;
   //             checkForKeyword(line_arr);
   //             String keyword = returnKeyword(checkForKeyword(line_arr), line_arr);
                for(String wordInLine: line_arr) {
                    for(String blockType: CodeBlock.BLOCK_TYPES) {
                        if(wordInLine.equals(blockType)) {
                            keyword = wordInLine;
                            if (keyword.equals(CodeBlock.BLOCK_TYPES[1])) { // "for"
                                if (line.contains("N:")) {
                                    int updateN_power = currComplexity.getN_power() + 1;
                                    currComplexity.setN_power(updateN_power);
                                } else if (line.contains("log_N:")) {
                                    int updateLog_power = currComplexity.getLog_power() + 1;
                                    currComplexity.setLog_power(updateLog_power);
                                }
                                block.setBlockComplexity(currComplexity);
                                stack.push(block);
                                namePush_counter++;
                                nameStack.push(name); // from 1 to 1.1
                                block.setName("1." + name);
                                System.out.println("\nEntering block: " + block.getName() + "'for':\n" + block);
                            } else if (keyword.equals(CodeBlock.BLOCK_TYPES[2])) { // while
                                char loopVariable_char = line.trim().charAt(7);
                                block.setLoopVariable(String.valueOf(loopVariable_char));
                                CodeBlock whileBlock = new CodeBlock();
                                whileBlock.setBlockComplexity(new Complexity(0 ,0));   // O(1)
                                whileBlock.setHighestSubComplexity(new Complexity(0 ,0));
                                stack.push(whileBlock);
                                namePush_counter++;
                                nameStack.push(name);
                                block.setName("1." + name);
                                System.out.println("\nEntering block: " + block.getName() + "'while':\n" + whileBlock);
                            } else {
                                CodeBlock otherBlock = new CodeBlock();
                                otherBlock.setBlockComplexity(new Complexity(0 ,0));   // O(1)
                                otherBlock.setHighestSubComplexity(new Complexity(0 ,0));
                                stack.push(otherBlock);
                                nameStack.push(name);
                                block.setName("1." + name);
                                namePush_counter++;
                                if(keyword.equals(CodeBlock.BLOCK_TYPES[0])) {
                                    System.out.println("\nEntering block: " + block.getName() + "'def':\n" + otherBlock);
                                }
                                else if(keyword.equals(CodeBlock.BLOCK_TYPES[3])) {
                                    System.out.println("\nEntering block: " + block.getName() + "'if':\n" + otherBlock);
                                }
                                else if(keyword.equals(CodeBlock.BLOCK_TYPES[4])) {
                                    System.out.println("\nEntering block: " + block.getName() + "'elif':\n" + otherBlock);
                                }
                                else if(keyword.equals(CodeBlock.BLOCK_TYPES[5])) {
                                    System.out.println("\nEntering block: " + block.getName() + "'else':\n" + otherBlock);
                                }
                            }
                        } else if (line.trim().contains(CodeBlock.BLOCK_TYPES[2]) && line.trim().contains(block.getLoopVariable())) {
                            if(checkForString(line_arr, "/=")) {
                                stack.peek().setBlockComplexity(new Complexity(stack.peek().getBlockComplexity().getN_power(), Integer.valueOf(line.charAt(line.indexOf("=") + 2))));
                            }
                            else if(checkForString(line_arr, "-=")) {
                                stack.peek().setBlockComplexity(new Complexity((stack.peek().getBlockComplexity().getN_power() - 1), stack.peek().getBlockComplexity().getLog_power()));
                            }
                            else if(checkForString(line_arr, "+="))
                                stack.peek().setBlockComplexity(new Complexity((stack.peek().getBlockComplexity().getN_power()+1), stack.peek().getBlockComplexity().getLog_power()));
                            System.out.println("Found update statement, updating block" + stack.peek().getName() + "\n" + stack.peek());
                        }
                        if(!changed) {
                            name++;
                            System.out.println("Leaving block " + stack.peek().getName() + ", nothing to update.");
                        }
                        else{
                            name++;
                            System.out.println("Leaving block " + stack.peek().getName() + ", updating block " + name + ":");
                        }
                    }
                }
                if(indents < stack.size()) {
                    namePop(namePush_counter, nameStack);
                    stack.pop();
                }
            }
        }
        while(stack.size()>1) {
            CodeBlock oldTop = stack.pop();
            Complexity oldTopComplexity = oldTop.getTotalComplexity(oldTop.getBlockComplexity(), oldTop.getHighestSubComplexity());
            if(compareOrder(oldTopComplexity, stack.peek().getHighestSubComplexity()) == 1)
                stack.peek().setHighestSubComplexity(oldTopComplexity);
        }

        System.out.println("\nLeaving block " + stack.peek().getName() + ".");
        return stack.pop().getHighestSubComplexity();  // high sub complexity of whole block/stack
    }

    /**
     * This method pops the NameStack for the amount of times NameStack is pushed
     * @param namePush_counter
     *      number of times NameStack is popped
     * @param stack
     *      the NameStack
     */
    public static void namePop(int namePush_counter, NameStack stack) {
        for(int i=0; i<namePush_counter; i++)
            stack.pop();
    }

    /**
     * This is a helper method that compares two  Complexity objects and sees which one is higher order
     * @param complex1
     *      A  Complexity object
     * @param complex2
     *      A  Complexity object
     * @return
     *      returns 1 if complex1 has higher order than complex2, 2 vice versa, 0 if they are equal
     */
    public static int compareOrder(Complexity complex1, Complexity complex2) {
        int result = 0;
        if(complex1.getLog_power() > 0 && complex2.getLog_power() > 0) {    // have log power
            if(complex1.getN_power() == 0 && complex2.getN_power() == 0) {  // have log power, no n power, compare log power
                if(complex1.getLog_power() > complex2.getLog_power())
                    result = 1;
                else if(complex1.getLog_power() < complex2.getLog_power())
                    result = 2;
            }
            else {  // have log power and n power
                if(complex1.getLog_power() > complex2.getLog_power())   // log power 1 > log power 2
                    result = 1;
                else if(complex1.getLog_power() < complex2.getLog_power())  // log power 2 > log power 1
                    result = 2;
                else {                                                      // log power 1 = log power 2, compare n
                    if(complex1.getN_power() > complex2.getN_power())
                        result = 1;
                    else if (complex1.getN_power() < complex2.getN_power())
                        result = 2;
                }
            }
        }
        else {  // no log power, n power
            if(complex1.getN_power() > complex2.getN_power())
                result = 1;
            else if(complex1.getN_power() < complex2.getN_power())
                result = 2;
        }
        return result;
    }

    /**
     * This is a method that checks if a string is contained in the line
     * @param line_arr
     *      line string split by spaces into an array line_arr
     * @param str
     *      the string to search for
     * @return
     *      true if string is found, false otherwise
     */
    public static Boolean checkForString(String[] line_arr, String str) {
        Boolean result = false;
        for(String wordInLine: line_arr) {
            if(wordInLine.equals(str))
                result = true;
        }
        return result;
    }

    /**
     * This is a helper method that checks if the indicated line contains a keyword
     * @param line_arr
     *      The line separated by spaces into an array
     * @return
     *      true if keyword is found, false otherwise
     */
    public static Boolean checkForKeyword(String[] line_arr) {
        Boolean result = false;
        for(String wordInLine: line_arr) {
            for(String blockType: CodeBlock.BLOCK_TYPES) {
                if(wordInLine.equals(blockType))
                    result = true;
            }
        }
        return result;
    }

    /**
     * This is a helper method that returns the keyword in the indicated line if one is found
     * @param check
     *      Boolean that determines if keyword is found in line
     * @param line_arr
     *      The line separated by spaces into an array
     * @return
     *      The keyword found in the line
     */
    public static String returnKeyword(Boolean check, String[] line_arr) {
        String keyword = "";
        if(!check)
            return keyword;
        for(String wordInLine: line_arr) {
            for(String blockType : CodeBlock.BLOCK_TYPES) {
                if(wordInLine.equals(blockType))
                    keyword = wordInLine;
            }
        }
        return keyword;
    }

    public static void main(String[] args) {
        try {
            System.out.print("Please enter a file name (or 'quit' to quit): ");
            Scanner input = new Scanner(System.in);
            String fileName = input.nextLine();
            if (fileName.equalsIgnoreCase("quit")) {
                System.out.println("Program terminating successfully...");
                System.exit(0);
            } else {
                System.out.println("\n Overall complexity of " + fileName.substring(0, fileName.length()-3) + ": " + traceFile(fileName));
                input.close();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}