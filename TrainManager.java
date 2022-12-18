package hw2;
import java.util.Scanner;

/**
 * The TrainManager class implements a menu-based application which performs
 * operations on an empty TrainLinkedList object
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class TrainManager {
    public static void main(String[] args) {
        TrainLinkedList train = new TrainLinkedList();
        while(true) {
            printMenu();
            Scanner input = new Scanner(System.in);
            String letter = input.nextLine().toUpperCase();
            switch (letter.charAt(0)) {
                case 'F':
                    try {
                        train.cursorForward();
                        System.out.println("Cursor moved forward");
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyListException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'B':
                    try {
                        train.cursorBackward();
                        System.out.println("Cursor moved backward");
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyListException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'I':
                    System.out.print("Enter car length in meters: ");
                    String length_str = input.nextLine();
                    double length = Double.parseDouble(length_str);
                    System.out.print("Enter car weight in tons: ");
                    String weight_str = input.nextLine();
                    double weight = Double.parseDouble(weight_str);
                    TrainCar newCar = new TrainCar(weight, length);
                    try {
                        train.insertAfterCursor(newCar);
                        System.out.println("New train car " + length + " meters " + weight + " tons inserted into train.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'R':
                    try {
                        System.out.println("Car successfully unlinked. The following load has been removed from the train:");
                        train.removeCursor();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyListException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'L':
                    System.out.print("Enter product name: ");
                    String productName = input.nextLine();
                    System.out.print("Enter product weight in tons: ");
                    String productWeight_str = input.nextLine();
                    double productWeight = Double.parseDouble(productWeight_str);
                    System.out.print("Enter product value in dollars: ");
                    String productValue_str = input.nextLine();
                    double productValue = Double.parseDouble(productValue_str);
                    System.out.print("Enter is product dangerous? (y/n): ");
                    String isDangerous_str = input.nextLine();
                    if(isDangerous_str.equals("y"))
                        isDangerous_str = "true";
                    else if(isDangerous_str.equals("n"))
                        isDangerous_str = "false";
                    boolean isProductDangerous = Boolean.parseBoolean(isDangerous_str);
                    ProductLoad newLoad = new ProductLoad();
                    try {
                        newLoad.setName(productName);
                        newLoad.setWeight(productWeight);
                        newLoad.setValue(productValue);
                        newLoad.setDangerous(isProductDangerous);
                        train.getCursorData().setProductLoad(newLoad);
                        train.updateProductLoadAttributes(newLoad, "add");
                        System.out.println(productWeight + " tons of " + productName + " added to the current car.");
                    } catch(IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch(EmptyListException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'S':
                    System.out.print("Enter product name: ");
                    String productName2 = input.nextLine();
                    try {
                        train.findProduct(productName2);
                    } catch (EmptyListException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'T':
                    System.out.println(train);
                    break;
                case 'M':
                    try {
                        train.printManifest();
                    } catch (EmptyListException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'D':
                    try {
                        train.removeDangerousCars();
                        System.out.println("Dangerous cars successfully removed from the train.");
                    } catch (EmptyListException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'Q':
                    System.out.println("Program terminating successfully...");
                    System.exit(0);
                    break;
            }
        }
    }
    public static void printMenu() {
        System.out.println("(F) Cursor Forward\n" +
                "(B) Cursor Backward\n" +
                "(I) Insert Car After Cursor\n" +
                "(R) Remove Car At Cursor\n" +
                "(L) Set Product Load\n" +
                "(S) Search For Product\n" +
                "(T) Display Train\n" +
                "(M) Display Manifest\n" +
                "(D) Remove Dangerous Cars\n" +
                "(Q) Quit");
        System.out.print("Enter a selection: ");
    }
}
