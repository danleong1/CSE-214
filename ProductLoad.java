package hw2;

/**
 * The ProductLoad class implements ProductLoad objects and makes their
 * corresponding attributes accessible and mutable.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class ProductLoad {
    private String name = "Empty";  // to account for when load is empty
    private double weight;
    private double value;
    private boolean isDangerous;  // product load is not dangerous by default

    /**
     * This is a constructor used to create a new ProductLoad object
     */
    public ProductLoad(){
    }

    /**
     * This is a constructor used to create a new ProductLoad object
     * @param name
     *      The name of the product
     * @param weight
     *      The weight of the product
     * @param value
     *      The value of the product
     * @param isDangerous
     *      Determines whether the product is dangerous
     */
    public ProductLoad(String name, double weight, double value, boolean isDangerous) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.isDangerous = isDangerous;
    }

    /**
     * A getter method that returns the name of the product
     * @return
     *        The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * A getter method that returns the weight of the product
     * @return
     *        The weight of the product
     */
    public double getWeight() {
        return weight;
    }

    /**
     * A getter method that returns the value of the product
     * @return
     *       The value of the product
     */
    public double getValue() {
        return value;
    }

    /**
     * A getter method that returns if the product is dangerous
     * @return
     *        The boolean value of whether the product is dangerous (true if dangerous)
     */
    public boolean getDangerous() {
        return isDangerous;
    }

    /**
     * A mutator method that changes the name of the product
     * @param name
     *      The new name that the mutator changes the product's current name to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A mutator method that changes the weight of the product
     * @param weight
     *      The new weight that the mutator changes the product's current weight to
     */
    public void setWeight(double weight) {
        if(weight<0)
            throw new IllegalArgumentException("Weight cannot be negative");
        else
            this.weight = weight;
    }

    /**
     * A mutator method that changes the product's value
     * @param value
     *      The new value that the mutator changes the product's current value to
     */
    public void setValue(double value) {
        if(value<0)
            throw new IllegalArgumentException("Value cannot be negative");
        else
            this.value = value;
    }

    /**
     * A mutator method that changes whether the product is dangerous
     * @param isDangerous
     *      Boolean value that tells the new status of whether the product is dangerous
     */
    public void setDangerous(boolean isDangerous) {
        this.isDangerous = isDangerous;
    }
}
