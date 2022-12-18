package hw4; /**
 * The hw4.Router class implements a queue of hw4.Router objects by extending
 * LinkedList and provides methods to perform various operations on the queue.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */

import java.util.LinkedList;

public class Router extends LinkedList<Packet> {     // or implement Deque?
    private int size;
    private int maxBufferSize;

    /**
     * This is a constructor used to create a new hw4.Router object.
     */
    public Router() {
    }

    /**
     * This is a constructor used to create a new hw4.Router object.
     * @param maxBufferSize
     *      maxBufferSize of the new router created
     */
    public Router(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    /**
     * This is a method that performs the enqueue operation on the router
     * @param p
     *      enqueues a packet onto the router
     */
    public void enqueue(Packet p) {
        this.add(p);
        size++;
    }

    /**
     * This is a method that performs the dequeue operation on the router
     */
    public void dequeue() {
        this.remove();
        size--;
    }

    /**
     * This is a getter method that returns the size of the router queue
     * @return
     *      size of the router queue
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if router is empty and false otherwise
     * @return
     *      true if router is empty and false otherwise
     */
    public boolean isEmpty() {
        return (size==0);
    }

    /**
     * Returns a String representation of the router buffer
     * @return
     *      String representation of the router buffer
     */
    public String toString() {
        String str = super.toString();
        return "{" + str.substring(1, str.length()-1) + "}";
    }

    /**
     * Returns the index of the Intermediate router with the most free buffer space
     * @param routers
     *      array of Intermediate routers to search within
     * @return
     *      index of the router with the most free buffer space
     * @throws FullBufferException
     *      when all router buffers are full
     */
    public static int sendPacketTo(Router[] routers) throws FullBufferException {
        int bestIndex = 0;
        boolean isFull = true;
        for(int i = 0; i < routers.length; i++) {
            if(routers[i].size() < routers[bestIndex].size())
                bestIndex = i;
            if(routers[i].size() < routers[i].maxBufferSize)
                isFull = false;
        }
        if(isFull)
            throw new FullBufferException();
        return bestIndex;
    }
}