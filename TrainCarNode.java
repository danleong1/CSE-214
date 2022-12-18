package hw2;

/**
 * The TrainCarNode class implements TrainCarNode objects which act as node
 * wrappers around TrainCar objects in a TrainLinkedList.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class TrainCarNode {
    private TrainCarNode prev;
    private TrainCarNode next;
    private TrainCar car;

    /**
     * This is a constructor used to create a new TrainCarNode object
     */
    public TrainCarNode() {
    }

    /**
     * This is a constructor used to create a new TrainCarNode object
     * @param car
     *      The TrainCar object which the constructor creates a
     *      TrainCarNode object for
     */
    public TrainCarNode(TrainCar car) {
        this.car = car;
    }

    /**
     * This is a getter method that returns the previous train car
     * @return
     *      The previous train car
     */
    public TrainCarNode getPrev() {
        return prev;
    }

    /**
     * This is a getter method that returns the next train car
     * @return
     *      The next train car
     */
    public TrainCarNode getNext() {
        return next;
    }

    /**
     * This is a getter method that returns the train car at the called position
     * @return
     *      The train car at the called position
     */
    public TrainCar getCar() {
        return car;
    }

    /**
     * This is a mutator method that changes the previous train car
     * @param newPrev
     *      The new previous train car
     */
    public void setPrev(TrainCarNode newPrev) {
        prev = newPrev;
    }

    /**
     * This is a mutator method that changes the next train car
     * @param newNext
     *      The new next train car
     */
    public void setNext(TrainCarNode newNext) {
        next = newNext;
    }

    /**
     * This is a mutator method that changes the train car at the called position
     * @param newCar
     *      The new train car
     */
    public void setCar(TrainCar newCar) {
        car = newCar;
    }

    /**
     * A table representation of the train car and its attributes
     * @return
     *      The table representation of the train car and its attributes
     */
    public String toString() {
        String isDangerous_str = "NO";
        if (car.getProductLoad().getDangerous())
            isDangerous_str = "YES";
        String output = String.format("%8s%16s%14s%12s", "Name", "Weight (t)", "Value ($)", "Dangerous")
                + "\n===================================================\n"
                + String.format("%10s%14s%14s%12s", car.getProductLoad().getName(), String.valueOf(car.getProductLoad().getWeight()),
                String.valueOf(car.getProductLoad().getValue()), isDangerous_str);
        return output;
    }
}
