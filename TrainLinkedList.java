package hw2;
import java.text.DecimalFormat;

/**
 * The TrainLinkedList class implements a linked list of the
 * TrainCar objects and provides methods to perform various
 * operations.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class TrainLinkedList {
    private TrainCarNode head;
    private TrainCarNode tail;
    private TrainCarNode cursor;
    private TrainCarNode nodePtr;
    private int numTrainCars;
    private double length;
    private double value;
    private double weight;
    private boolean isDangerous;       // train not dangerous by default

    /**
     * This is a constructor used to create a new TrainLinkedList object.
     */
    public TrainLinkedList() {
        head = null;
        tail = null;
        cursor = null;
    }

    /**
     * This is a method which checks whether the train contains any train cars
     * @return
     *      true if the train contains no train cars and false otherwise
     */
    public boolean isEmptyList() {
        //return (cursor == null);
        return (size() == 0);
    }

    /**
     * This method returns the car object which the cursor refers to
     * @return
     *      Car object which the cursor refers to
     * @throws EmptyListException
     *      When the train contains no train cars
     */
    public TrainCar getCursorData() throws EmptyListException {
        if(isEmptyList())
           throw new EmptyListException("List is empty");
        return cursor.getCar();
    }

    /**
     * This method changes the car object referenced by the cursor
     * @param newCar
     *      The car object which the method changes the cursor's reference to
     * @throws EmptyListException
     *      When the train contains no train cars
     */
    public void setCursorData(TrainCar newCar) throws EmptyListException {
        if(isEmptyList())
           throw new EmptyListException("List is empty");
        cursor.setCar(newCar);
    }

    /**
     * This method advances the cursor's position in the linked list
     * @throws NullPointerException
     *      When the cursor is null
     * @throws EmptyListException
     *      When the train contains no train cars
     * @throws IllegalArgumentException
     *      When the cursor is pointed at the tail
     */
    public void cursorForward() throws NullPointerException, EmptyListException, IllegalArgumentException {
        if(isEmptyList())
           throw new EmptyListException("List is empty");
        if(cursor == null)
            throw new NullPointerException("The list is empty.");
        if(cursor == tail)
            throw new IllegalArgumentException("No next car, cannot move cursor forward");
        cursor = cursor.getNext();
    }

    /**
     * This method moves the cursor backwards in the linked list
     * @throws EmptyListException
     *      When the train contains no train cars
     * @throws IllegalArgumentException
     *      When the cursor is pointed at the head
     */
    public void cursorBackward() throws EmptyListException, IllegalArgumentException {
        if(isEmptyList())
           throw new EmptyListException("List is empty");
        if(cursor == head)
            throw new IllegalArgumentException("Cursor currently at head, cannot move cursor backward");
        cursor = cursor.getPrev();
    }

    /**
     * This method inserts a TrainCar object after the cursor in the linked list
     * @param newCar
     *      The TrainCar object to be added to the train
     * @throws IllegalArgumentException
     *      When the TrainCar object to be added is null
     */
    public void insertAfterCursor(TrainCar newCar) throws IllegalArgumentException {   // fix
        TrainCarNode newNode = new TrainCarNode(newCar);
        if(newCar == null)
            throw new IllegalArgumentException("newCar is null.");
        if(cursor == null) {  // empty list
            head = newNode;
            cursor = newNode;
            tail = newNode;
        }
        else {
            newNode.setNext(cursor.getNext());
            cursor.setNext(newNode);
            newNode.setPrev(cursor);
            cursor = newNode;       // cursor now points at inserted car
            if(cursor.getNext() == null)
                tail = cursor;
        }
        /*
        else {
            cursor.setPrev(cursor);
            newNode.getNext().setPrev(newNode);  // or cursor.getNext().setPrev(newNode);
         */
        numTrainCars++;
        length += newCar.getCarLength();
        weight += newCar.getCarWeight();
    }

    /**
     * This method updates the attributes of the train car according to its product load
     * @param load
     *      The ProductLoad object to update the train
     * @param operation
     *      Determine which operation to perform depending on whether the train car of
     *      the product load is added or removed
     */
    public void updateProductLoadAttributes(ProductLoad load, String operation) {
         if(operation.equalsIgnoreCase("add")) {
             weight += load.getWeight();
             value += load.getValue();
         }
         else if(operation.equalsIgnoreCase("remove")) {
             weight -= load.getWeight();
             value -= load.getValue();
         }
    }

    /**
     * This method removes the train car at the cursor
     * @return
     *      The train car object to be removed
     * @throws EmptyListException
     *      When the train contains no train cars
     * @throws IllegalArgumentException
     *      When the cursor is null
     */
    public TrainCar removeCursor() throws EmptyListException, IllegalArgumentException {  // fix
        if(cursor == null)
            throw new IllegalArgumentException("Cursor is null.");
        TrainCarNode output = new TrainCarNode();
        output = cursor;       // to get reference to cursor to be removed
        if(cursor == tail) {
            if(cursor.getPrev() == head) { // if prev is at head
                cursor = head;
                cursor.setNext(null);
            }
            else {
                cursorBackward();
                cursor.setPrev(cursor.getPrev());
                cursor.setNext(null);
            }
            tail = cursor;
        }
        else if(cursor.getPrev() == null && cursor.getNext() != null) {  // if cursor is at the head, only care about next
            if(cursor.getNext() == tail) // if next is at tail
                cursor = tail;  // cursor = tail
            else {
                cursor.setNext(cursor.getNext().getNext());
                cursorForward();
            }
            head = cursor;
        }
        else if(cursor.getPrev() == null && cursor.getNext() == null) {  // to remove only car in train
            cursor = null;
            printManifestHeader();
        }
        else if(cursor.getPrev() != null && cursor.getNext() != null) {
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursorForward();
        }
        else {
            cursorForward();
            cursor.setPrev(cursor.getPrev().getPrev());
            cursor.setNext(cursor.getNext().getNext());
        }
        numTrainCars--;
        length -= output.getCar().getCarLength();
        if(!output.getCar().isEmpty()) {
            updateProductLoadAttributes(output.getCar().getProductLoad(), "remove");
        }
        weight -= output.getCar().getCarWeight();
        System.out.println(output);
        return output.getCar();
    }

    /**
     * This method returns the number of train cars in the train
     * @return
     *      The number of train cars in the train
     */
    public int size() {
        return numTrainCars;
    }

    /**
     * This method returns the total length of the train
     * @return
     *      The total length of the train
     */
    public double getLength() {
        return length;
    }

    /**
     * This method returns the total value of the train
     * @return
     *       The total value of the train
     */
    public double getValue() {
        return value; //+ getCursorData().getProductLoad().getValue();   lead to toString asking for try/catch
    }

    /**
     * This method returns the total weight of the train (including product load)
     * @return
     *      The total weight of the train
     */
    public double getWeight() {
        return weight; //+ getCursorData().getProductLoad().getWeight();
    }

    /**
     * This method checks whether there is a dangerous product on the train
     * @return
     *      True if there is a dangerous product on the train and false otherwise
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * This method searches for a particular product throughout the train and sums together
     * its weight and value (if multiple are found)
     * @param name
     *      The name of the product to be searched for
     * @throws EmptyListException
     *      When there are no train cars in the train
     */
    public void findProduct(String name) throws EmptyListException {
        double totalWeight = 0, totalValue = 0;
        int counter = 0;
        nodePtr = head;
        TrainCarNode target = new TrainCarNode();
        while(nodePtr != null) {
            if(!nodePtr.getCar().isEmpty())
                if(nodePtr.getCar().getProductLoad().getName().equalsIgnoreCase(name)) {
                    target = nodePtr;
                    totalWeight += nodePtr.getCar().getProductLoad().getWeight();
                    totalValue += nodePtr.getCar().getProductLoad().getValue();
                    nodePtr.getCar().getProductLoad().setWeight(totalWeight);
                    nodePtr.getCar().getProductLoad().setValue(totalValue);
                    counter++;
                }
            if(nodePtr.getNext() == null) {      // when at tail
                if(counter == 0)
                    System.out.println("No record of " + name + " on board train.");
                else {
                    System.out.println("The following products were found on " + counter + " cars:");
                    System.out.println(nodePtr);
                }
            }
            nodePtr = nodePtr.getNext();
        }
    }

    /**
     * This method prints a formatted table representation of the train, displaying the
     * attributes of each train car and its product load
     * @throws EmptyListException
     *      When the train contains no train cars
     */
    public void printManifest() throws EmptyListException {
        printManifestHeader();
        nodePtr = head;
        int counter = 1;
        while(nodePtr != null) {
            if(nodePtr.getCar().isEmpty())
                printEmptyCar(nodePtr, counter);
            else
                printCar(nodePtr, counter);
            nodePtr = nodePtr.getNext();
            counter++;
        }
    }

    /**
     * This helper method prints the header of the table from printManifest()
     */
    public void printManifestHeader() {
        System.out.println(String.format("%7s%36s", "CAR:", "LOAD:") + "\n"
                + String.format("%8s%13s%14s%3s%8s%16s%14s%12s", "Num", "Length (m)", "Weight (t)", "|", "Name", "Weight (t)", "Value ($)",
                "Dangerous") + "\n" + "   ==================================+===================================================");
    }

    /**
     * This helper method prints the train car if it has no product load
     * @param nodePtr
     *      The node pointer of the train car
     * @param counter
     *      The position of the train car in the train
     */
    public void printEmptyCar(TrainCarNode nodePtr, int counter) {
        if(nodePtr.getCar() == cursor.getCar())
            System.out.println(String.format("%2s%5d%14s%14s%3s%10s%14s%14s%12s", "->", counter, String.valueOf(nodePtr.getCar().getCarLength()),
                    String.valueOf(nodePtr.getCar().getCarWeight()), "|", "Empty", "0.0", "0.00", "NO"));
        else
            System.out.println(String.format("%2s%5d%14s%14s%3s%10s%14s%14s%12s", "  ", counter, String.valueOf(nodePtr.getCar().getCarLength()),
                    String.valueOf(nodePtr.getCar().getCarWeight()), "|", "Empty", "0.0", "0.00", "NO"));
    }

    /**
     * This helper method prints the train car if it has a product load
     * @param nodePtr
     *      The node pointer of the train car
     * @param counter
     *      The position of the train car in the train
     */
    public void printCar(TrainCarNode nodePtr, int counter) {
        if(nodePtr.getCar().getProductLoad().getDangerous()) {
            String isDangerous_str = "YES";
            if(nodePtr.getCar() == cursor.getCar()) {
                System.out.println(String.format("%2s%5d%14s%14s%3s%10s%14s%14s%12s", "->", counter, String.valueOf(nodePtr.getCar().getCarLength()),
                        String.valueOf(nodePtr.getCar().getCarWeight()), "|", nodePtr.getCar().getProductLoad().getName(),
                        String.valueOf(nodePtr.getCar().getProductLoad().getWeight()), String.valueOf(nodePtr.getCar().getProductLoad().getValue()),
                        isDangerous_str));
            }
            else {
                System.out.println(String.format("%2s%5d%14s%14s%3s%10s%14s%14s%12s", "  ", counter, String.valueOf(nodePtr.getCar().getCarLength()),
                        String.valueOf(nodePtr.getCar().getCarWeight()), "|", nodePtr.getCar().getProductLoad().getName(),
                        String.valueOf(nodePtr.getCar().getProductLoad().getWeight()), String.valueOf(nodePtr.getCar().getProductLoad().getValue()),
                        isDangerous_str));
            }
        }
        else {
            String isDangerous_str = "NO";
            if (nodePtr.getCar() == cursor.getCar()) {
                System.out.println(String.format("%2s%5d%14s%14s%3s%10s%14s%14s%12s", "->", counter, String.valueOf(nodePtr.getCar().getCarLength()),
                        String.valueOf(nodePtr.getCar().getCarWeight()), "|", nodePtr.getCar().getProductLoad().getName(),
                        String.valueOf(nodePtr.getCar().getProductLoad().getWeight()), String.valueOf(nodePtr.getCar().getProductLoad().getValue()),
                        isDangerous_str));
            }
            else {
                System.out.println(String.format("%2s%5d%14s%14s%3s%10s%14s%14s%12s", "  ", counter, String.valueOf(nodePtr.getCar().getCarLength()),
                        String.valueOf(nodePtr.getCar().getCarWeight()), "|", nodePtr.getCar().getProductLoad().getName(),
                        String.valueOf(nodePtr.getCar().getProductLoad().getWeight()), String.valueOf(nodePtr.getCar().getProductLoad().getValue()),
                        isDangerous_str));
            }
        }
    }

    /**
     * This method removes all train cars which have dangerous products
     * @throws EmptyListException
     *      When the train contains no train cars
     */
    public void removeDangerousCars() throws EmptyListException {
        cursor = head;
        while(cursor != null) {
            if(getCursorData().getProductLoad()!=null && getCursorData().getProductLoad().getDangerous()) {
                removeCursor();
            }
            else {
                cursor = cursor.getNext();
            }
        }
        cursor = tail;
    }

    /**
     * This method returns a String representation of the train
     * @return
     *      String representation of the train
     */
    public String toString() {
        String isDangerous = "";
        if(isDangerous())
            isDangerous = "DANGEROUS";
        else
            isDangerous = "not dangerous";
        DecimalFormat df = new DecimalFormat("#.##");
        String weight_str = df.format(getWeight());
        String value_str = df.format(getValue());
        String output = "Train: " + size() + " car(s), " + getLength() + " meters, " + weight_str + " tons, $" + value_str + " value, " + isDangerous + ".";
        return output;
    }
}