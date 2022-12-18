package hw5;

/**
 * The hw5.DirectoryNode class implements hw5.DirectoryNode objects, makes their
 * corresponding attributes accessible and mutable, and provides methods to
 * perform various operations.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class DirectoryNode {
    private String name;
    private DirectoryNode left, middle, right;
    private boolean isFile;

    /**
     * This is a constructor used to create a new hw5.DirectoryNode object
     * @param name
     *      The name of the new hw5.DirectoryNode object
     */
    public DirectoryNode(String name) {
        this.name = name;
        left = null;
        middle = null;
        right = null;
    }

    /**
     * This is a constructor used to create a new hw5.DirectoryNode object
     */
    public DirectoryNode() {
        name = null;
        left = null;
        middle = null;
        right = null;
    }

    /**
     * This is a constructor used to create a new hw5.DirectoryNode object
     * @param name
     *      The name of the new hw5.DirectoryNode object
     * @param isFile
     *      Boolean value of whether the hw5.DirectoryNode object is a file
     */
    public DirectoryNode(String name, boolean isFile) {
        this.name = name;
        this.isFile = isFile;
    }

    /**
     * A getter method that returns the name
     * @return
     *      name
     */
    public String getName() {
        return name;
    }

    /**
     * A getter method that returns the left child of the node
     * @return
     *      left child of the node
     */
    public DirectoryNode getLeft() {
        return left;
    }

    /**
     * A getter method that returns the middle child of the node
     * @return
     *      middle child of the node
     */
    public DirectoryNode getMiddle() {
        return middle;
    }

    /**
     * A getter method that returns the right child of the node
     * @return
     *      right child of the node
     */
    public DirectoryNode getRight() {
        return right;
    }

    /**
     * A method that determines whether the hw5.DirectoryNode is a file
     * @return
     *      true if a file, false otherwise
     */
    public boolean isFile() {
        return isFile;
    }

    /**
     * A mutator method that changes the name of the hw5.DirectoryNode
     * @param name
     *      the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A method that adds a child to any open child positions of the node in left-to-right order
     * @param newChild
     *      child to be added
     * @throws FullDirectoryException
     *      when there are no open child positions
     * @throws NotADirectoryException
     *      when the current node is a file
     */
    public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException {
        if(isFile) {
            throw new NotADirectoryException("ERROR: Cannot change directory into a file");
        }
        if(left != null && middle != null && right != null) {
            throw new FullDirectoryException("ERROR: Present directory is full.");
        }

        if(left == null) {
            left = newChild;
        }
        else if(middle == null) {
            middle = newChild;
        }
        else if(right == null) {
            right = newChild;
        }

    }

}
