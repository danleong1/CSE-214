package hw2;

/**
 * The TrainCar class implements TrainCar objects and makes their
 * corresponding attributes accessible. It also provides a mutator
 * method for the TrainCar's ProductLoad and a method to determine
 * whether the car is empty or not.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class TrainCar {
    private final double carLength;
    private final double carWeight;
    private ProductLoad load;

    /**
     * This is a constructor used to create a new TrainCar object
     * @param carWeight
     *      The weight of the train car itself
     * @param carLength
     *      The length of the train car
     */
    public TrainCar(double carWeight, double carLength) {
        this.carWeight = carWeight;
        this.carLength = carLength;
    }

    /**
     * This is a getter method that returns the train's car weight (with the product)
     * @return
     *      The train's car weight (with the product)
     */
    public double getCarWeight() {
//        if(load != null)
//            return carWeight + load.getWeight();
//        else
        return carWeight;
    }

    /**
     * This is a getter method that returns the train car's length
     * @return
     *      The train car's length
     */
    public double getCarLength() {
        return carLength;
    }

    /**
     * This is a getter method that returns the TrainCar's ProductLoad
     * @return
     *      The ProductLoad associated with the TrainCar object
     */
    public ProductLoad getProductLoad() {
        return load;
    }

    /**
     * This is a mutator method that changes the TrainCar's ProductLoad
     * @param load
     *      The new ProductLoad that the mutator changes TrainCar's ProductLoad to
     */
    public void setProductLoad(ProductLoad load) {
        this.load = load;
    }

    /**
     * This is a method that determines whether the train car contains a product
     * @return
     *      Boolean value that tells if the TrainCar has a ProductLoad (true if no product found)
     */
    public boolean isEmpty() {
        if(load == null)
            return true;
        return false;
    }
}
