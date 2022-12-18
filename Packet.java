package hw4;

/**
 * The hw4.Packet class implements hw4.Packet objects and makes their
 * corresponding attributes accessible and mutable.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */
public class Packet {
    private static int packetCount = 0;
    int id, packetSize, timeArrive, timeToDest, serviceTime;

    /**
     * This is a constructor used to create a new hw4.Packet object
     */
    public Packet() {
    }

    /**
     * This is a constructor used to create a new hw4.Packet object
     * @param packetCount
     *      number of packets created
     * @param id
     *      unique identifier for the packet
     * @param packetSize
     *      size of packet
     * @param timeArrive
     *      time the packet is created
     * @param timeToDest
     *      time it takes packet to arrive at destination router
     */
    public Packet(int packetCount, int id, int packetSize, int timeArrive, int timeToDest) {
        this.id = id;
        this.packetSize = packetSize;
        this.timeArrive = timeArrive;
        this.timeToDest = timeToDest;
    }

    /**
     * A getter method that returns the packetCount
     * @return
     *      packetCount
     */
    public int getPacketCount() {
        return packetCount;
    }

    /**
     * A getter method that returns the packet's ID
     * @return
     *      id
     */
    public int getId() {
        return id;
    }

    /**
     * A getter method that returns the packet's size
     * @return
     *      packetSize
     */
    public int getPacketSize() {
        return packetSize;
    }

    /**
     * A getter method that returns the time the hw4.Packet is created
     * @return
     *      timeArrive
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    /**
     * A getter method that returns the time it takes the hw4.Packet to
     * arrive at the destination router
     * @return
     *       timeToDest
     */
    public int getTimeToDest() {
        return timeToDest;
    }

    /**
     * A mutator method that changes the packetCount
     * @param packetCount
     *      the new packetCount of the packet
     */
    public void setPacketCount(int packetCount) {
        this.packetCount = packetCount;
    }

    /**
     * A mutator method that changes the packet's ID
     * @param id
     *      the new ID of the packet
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A mutator method that changes the packet's size
     * @param packetSize
     *      the new size of the packet
     */
    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    /**
     * A mutator method that changes the time which the packet is created
     * @param timeArrive
     *      the new time which the packet is created
     */
    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    /**
     * A mutator method that changes the time it takes the
     * packet to arrive at the destination router
     * @param timeToDest
     *      the time it takes the packet to arrive at the destination router
     */
    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /**
     * A mutator method that changes the time a packet is in the network
     * @param serviceTime
     *      the time a packet is in the network
     */
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * Returns a String representation of a hw4.Packet object
     * @return
     *      String representation of hw4.Packet object
     */
    public String toString() {
        String output = "[" + id + ", " + timeArrive +  ", " + (timeToDest+1) + "]";
        return output;
    }
}
