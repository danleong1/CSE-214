package hw5;

/**
 * The hw5.DirectoryTree class implements a ternary tree of DirectoryNodes and provides methods
 * to perform various operations.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class DirectoryTree {
    private DirectoryNode root, cursor;
    private String path;
    private String indent = "    ";

    /**
     * This is a constructor used to create a new hw5.DirectoryTree object
     */
    public DirectoryTree() {
        root = new DirectoryNode("root");
        cursor = root;
        path = root.getName();
    }

    /**
     * This is a getter method that returns the root node of the hw5.DirectoryTree
     * @return
     *      root
     */
    public DirectoryNode getRoot() {
        return root;
    }

    /**
     * This is a method that moves the cursor to the root node of the tree
     */
    public void resetCursor() {
        cursor = root;
    }

    /**
     * This is a method that determines whether a hw5.DirectoryNode exists in the hw5.DirectoryTree
     * @param node
     *      The node in the tree to begin search from (nodePtr)
     * @param name
     *      The name of the hw5.DirectoryNode to search for
     * @return
     *      true if found, false otherwise
     */
    public boolean ifNodeExists(DirectoryNode node, String name) {
        if(node == null)
            return false;
        if(node.getName().equals(name))
            return true;

       boolean onLeft = ifNodeExists(node.getLeft(), name);
       if(onLeft) return true;
       boolean onMiddle = ifNodeExists(node.getMiddle(), name);
       if(onMiddle) return true;
       boolean onRight = ifNodeExists(node.getRight(), name);
       return onRight;
    }

    /**
     * This is a method that searches for a hw5.DirectoryNode in a hw5.DirectoryTree
     * @param node
     *      The node in the tree to begin search from (nodePtr)
     * @param name
     *      The name of the hw5.DirectoryNode to search for
     * @return
     *      The hw5.DirectoryNode searched for
     */
    public DirectoryNode findNode(DirectoryNode node, String name) {
        if(node == null)
            return null;
        if(node.getName().equals(name))
            return node;

        if(findNode(node.getLeft(), name) != null)
            return findNode(node.getLeft(), name);
        if(findNode(node.getMiddle(), name) != null)
            return findNode(node.getMiddle(), name);
        return findNode(node.getRight(), name);
    }

    /**
     * This is a method that moves the cursor to the directory with the indicated name
     * @param name
     *      the name of the directory to move the cursor to
     * @throws NotADirectoryException
     *      when the node with the indicated name is a file
     */
    public void changeDirectory(String name) throws NotADirectoryException {
        if(!ifNodeExists(root, name))
            throw new NotADirectoryException("ERROR: No such directory named '" + name + "'.");
        if(findNode(root, name).isFile())
            throw new NotADirectoryException("ERROR: Cannot change directory into a file.");
        cursor = findNode(root, name);
    }

    /**
     * A helper method that prints the path of the directory from the root to the node with indicated name
     * @param nodePtr
     *      The current hw5.DirectoryNode in the hw5.DirectoryTree
     * @param path
     *      The path of the directory from the root to the node with indicated name
     * @param name
     *      The indicated name
     */
    public void findPath(DirectoryNode nodePtr, String path, String name) {
        if(nodePtr.getName().equals(name))
            System.out.println(path);

        if(nodePtr.getLeft() != null)
            findPath(nodePtr.getLeft(), path + "/" + nodePtr.getLeft().getName(), name);
        if(nodePtr.getMiddle() != null)
            findPath(nodePtr.getMiddle(), path + "/" + nodePtr.getMiddle().getName(), name);
        if(nodePtr.getRight() != null)
            findPath(nodePtr.getRight(), path + "/" + nodePtr.getRight().getName(), name);
    }

    /**
     * A method that returns a string containing the path of the directory names from
     * the root to the cursor, separated by "/"
     * @return
     *      a string representation
     */
    public String presentWorkingDirectory() {
        findPath(root, root.getName(), cursor.getName());
        return path;
    }

    /**
     * Returns a string containing a space-separated list of names of all the
     * child directories or files of the cursor
     * @return
     *      a string representation
     */
    public String listDirectory() {
        String str = "";
        if(cursor.getLeft() != null)
            str += cursor.getLeft().getName() + " ";
        if(cursor.getMiddle() != null)
            str += cursor.getMiddle().getName() + " ";
        if(cursor.getRight() != null)
            str += cursor.getRight().getName() + " ";
        return str;
    }

    /**
     * Method that prints a formatted nested list of names of all the nodes in the directory
     * tree, starting from the nodePtr, and keeps track of indents
     * @param nodePtr
     *      the current node
     * @param numIndents
     *      the number of indents needed at the current level of the tree
     */
    public void printDirectoryTree(DirectoryNode nodePtr, int numIndents) {
        if(nodePtr == null)
            return;
        for (int i = 0; i < numIndents; i++)
            System.out.print(indent);
        if(!nodePtr.isFile())
            System.out.println("|- " + nodePtr.getName() + " ");
        else
            System.out.println("- " + nodePtr.getName() + " ");
        if(nodePtr.getLeft() != null)
            printDirectoryTree(nodePtr.getLeft(), numIndents+1);
        if(nodePtr.getMiddle() != null)
            printDirectoryTree(nodePtr.getMiddle(), numIndents+1);
        if(nodePtr.getRight() != null)
            printDirectoryTree(nodePtr.getRight(), numIndents+1);
    }

    /**
     * Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor
     */
    public void printDirectoryTree() {
        printDirectoryTree(cursor, 0);
    }


    /**
     * Creates a directory with the indicated name and adds it to child of cursor node
     * @param name
     *      name of new directory
     * @throws IllegalArgumentException
     *      when name is invalid
     */
    public void makeDirectory(String name) throws IllegalArgumentException {
        if(name.contains(" ") | name.contains("/"))
            throw new IllegalArgumentException("ERROR: Invalid name");
        DirectoryNode directory = new DirectoryNode(name);
        try {
            cursor.addChild(directory);
        } catch(FullDirectoryException | NotADirectoryException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a directory with the indicated name and adds it to child of cursor node
     * @param name
     *      name of new directory
     * @return
     *      new directory with the indicated name
     * @throws IllegalArgumentException
     *      when name is invalid
     */
    public DirectoryNode returnMakeDirectory(String name) throws IllegalArgumentException {
        if(name.contains(" ") | name.contains("/"))
            throw new IllegalArgumentException("ERROR: Invalid name");
        DirectoryNode directory = new DirectoryNode(name);
        try {
            cursor.addChild(directory);
        } catch(FullDirectoryException | NotADirectoryException e) {
            System.out.println(e.getMessage());
        }
        return directory;
    }

    /**
     * Creates a file with the indicated name and adds it to child of cursor node
     * @param name
     *      name of new file
     * @throws IllegalArgumentException
     *      when name is invalid
     */
    public void makeFile(String name) throws IllegalArgumentException {
        if(name.contains(" ") | name.contains("/"))
            throw new IllegalArgumentException("ERROR: Invalid name");
        DirectoryNode file = new DirectoryNode(name, true);
        try {
            cursor.addChild(file);
        } catch(FullDirectoryException | NotADirectoryException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a file with the indicated name and adds it to child of cursor node
     * @param name
     *      name of new file
     * @return
     *      new file with indicated name
     * @throws IllegalArgumentException
     *      when name is invalid
     */
    public DirectoryNode returnMakeFile(String name) throws IllegalArgumentException {
        if(name.contains(" ") | name.contains("/"))
            throw new IllegalArgumentException("ERROR: Invalid name");
        DirectoryNode file = new DirectoryNode(name, true);
        try {
            cursor.addChild(file);
        } catch(FullDirectoryException | NotADirectoryException e) {
            System.out.println(e.getMessage());
        }
        return file;
    }

    /**
     * Method that attempts to move a file or directory from the src to dest
     * @param srcPath
     *      path from root to src
     * @param destPath
     *      path from root to dest
     */
    public void move(String srcPath, String destPath) {
        String srcName = srcPath.substring(srcPath.lastIndexOf('/')+1);
        String destName = destPath.substring(destPath.lastIndexOf('/')+1);
        if(ifNodeExists(root, srcName) && ifNodeExists(root, destName)) {
            DirectoryNode src = findNode(root, srcName);
            DirectoryNode dest = findNode(root, destName);
            try {
                dest.addChild(src);
                src = null;
            } catch (FullDirectoryException | NotADirectoryException e) {
                System.out.println(e.getMessage());
            }
        }
        else
            System.out.println("ERROR: No such file exists.");
    }
}
