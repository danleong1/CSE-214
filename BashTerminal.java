package hw5; /**
 * The hw5.BashTerminal class implements a file system implemented by an
 * instance of hw5.DirectoryTree which allows users to input commands.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
import java.util.Scanner;
public class BashTerminal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Starting bash terminal.");
        DirectoryTree tree = new DirectoryTree();
        while(true) {
            System.out.print("[dtleong@host]: $ ");
            String option = input.nextLine();
            if(option.startsWith("mkdir ")) {
                try {
                    tree.returnMakeDirectory(option.substring(6));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (option.startsWith("touch ")) {
                try {
                    tree.returnMakeFile(option.substring(6));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (option.equals("pwd"))
                tree.presentWorkingDirectory();
            else if (option.equals("ls"))
                System.out.println(tree.listDirectory());
            else if (option.equals("ls -R"))
                tree.printDirectoryTree();
            else if (option.startsWith("cd")) {
                if (option.charAt(3) == '/') {
                    tree.resetCursor();
                }
                else {
                    if(option.substring(4).contains("/")) {
                        String dir = option.substring(option.lastIndexOf('/')+1);
                        try {
                            tree.changeDirectory(dir);
                        } catch (NotADirectoryException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else {
                        try {
                            tree.changeDirectory(option.substring(3));
                        } catch (NotADirectoryException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            else if(option.startsWith("find")) {
                tree.findPath(tree.getRoot(), tree.getRoot().getName(), option.substring(5));
            }
            else if(option.startsWith("mv")) {
                String[] optionArr = option.split(" ");
                tree.move(optionArr[1], optionArr[2]);
            }
            else if (option.equals("exit")) {
                System.out.println("Program terminating normally");
                break;
            }
        }
    }
}
