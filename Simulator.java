package hw4; /**
 * The hw4.Simulator class takes user input to simulate networking
 * using hw4.Packet and hw4.Router objects.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Simulator {
    private Router dispatcher;
    private Router[] routers;
    private Queue<Object> fairness;
    private ArrayList<Packet> destination;
    private int totalServiceTime;
    private int totalPacketsArrived;
    private int packetsDropped;
    private int currentTime;
    public static final int MAX_PACKETS = 3;

    /**
     * This is a constructor to create a new hw4.Simulator Object
     */
    public Simulator() {
    }

    /**
     * This is a mutator method that changes the total service time
     * @param totalServiceTime
     *      total service time
     */
    public void setTotalServiceTime(int totalServiceTime) {
        this.totalServiceTime = totalServiceTime;
    }

    /**
     * This is a mutator method that changes totalPacketsArrived
     * @param totalPacketsArrived
     *      number of packets successfully forwarded to destination
     */
    public void setTotalPacketsArrived(int totalPacketsArrived) {
        this.totalPacketsArrived = totalPacketsArrived;
    }

    /**
     * This is a method that runs a simulation using user input
     * @param numIntRouters
     *      number of Intermediate routers in the network
     * @param arrivalProb
     *      probability of new packet arriving at Dispatcher
     * @param maxBufferSize
     *      maximum number of Packets a hw4.Router can accommodate for
     * @param minPacketSize
     *      minimum size of a packet
     * @param maxPacketSize
     *      maximum size of a packet
     * @param bandwidth
     *      maximum number of Packets the destination router can accept at a given time
     * @param duration
     *      the total time/simulation units
     * @return
     *      the average time each packet spends within the network
     */
    public double simulate(int numIntRouters, double arrivalProb, int maxBufferSize, int minPacketSize, int maxPacketSize, int bandwidth, int duration) {
        dispatcher = new Router(MAX_PACKETS);
        routers = new Router[numIntRouters];        // array of intermediate RouterQueues
        fairness = new LinkedList<>();
        destination = new ArrayList<Packet>();
        for(int i=0; i<numIntRouters; i++)
            routers[i] = new Router(maxBufferSize);
        for(currentTime=1; currentTime<=duration; currentTime++) {
            System.out.println("Time: " + currentTime);
            int noPacketCtr = 0;
            for (int i = 1; i <= MAX_PACKETS; i++) {
                if(Math.random() < arrivalProb) {       // if packet(s) arrive at dispatcher
                    Packet p = new Packet();
                    int packetCount = p.getPacketCount();
                    packetCount++;
                    p.setPacketCount(packetCount);
                    p.setId(packetCount);
                    p.setPacketSize(randInt(minPacketSize, maxPacketSize));
                    p.setTimeArrive(currentTime);
                    p.setTimeToDest(p.getPacketSize() / 100);

                    System.out.println("hw4.Packet " + p.getId() + " arrives at dispatcher with size " + p.getPacketSize() + ".");
                    dispatcher.enqueue(p);

                    sendToIntermediate(p);

                }
                else {
                    noPacketCtr++;
                    if(noPacketCtr == MAX_PACKETS)
                        System.out.println("No packets arrived.");
                }
            }
            updateTimeToDest(routers, bandwidth);       // calls sendToDestination if conditions met
            for(int j=1; j<=numIntRouters; j++)
                System.out.println("R" + j + ": " + routers[j-1]);
            System.out.println();
            destination.removeAll(destination);

        }

        System.out.println("Simulation ending...");
        System.out.println("Total service time: " + totalServiceTime);
        System.out.println("Total packets served: " + totalPacketsArrived);
        double avgTime = (double) totalServiceTime / totalPacketsArrived;
        System.out.println("Average service time per packet: " + String.format("%.2f", avgTime));
        System.out.println("Total packets dropped: " + packetsDropped);
        return avgTime;
    }

    /**
     * This is a method which sends a packet to the appropriate Intermediate router
     * @param p
     *      packet to be sent
     */
    public void sendToIntermediate(Packet p) {  // send packet from dispatcher router to intermediate router
        try {
            int interDest = Router.sendPacketTo(routers);
            dispatcher.dequeue();
            routers[interDest].enqueue(p);
            fairness.add(interDest); // add index of router to queue to track fairness (FIFO)
            System.out.println("hw4.Packet " + p.getId() + " sent to hw4.Router " + (interDest+1));
        } catch (FullBufferException e) {
            packetsDropped++;
            System.out.println("Network is congested. hw4.Packet " + p.getId() + " is dropped.");
        }
    }

    /**
     * Updates packets' time to destination and determines whether it is ready to be sent to determination
     * @param routers
     *      array of Intermediate routers
     * @param bandwidth
     *      maximum number of Packets the destination router can accept at a given time
     */
    public void updateTimeToDest(Router[] routers, int bandwidth) {
        for(Router r: routers) {        // may need to use regular for loop instead
            if(!r.isEmpty()) {
                for(int i=0; i<r.size(); i++) {     // loop through packets in a router
                    if (r.get(i).getTimeToDest() > 0) {
                        r.get(i).setTimeToDest(r.get(i).getTimeToDest() - 1);
                    } else if (r.get(i).getTimeToDest() <= 0) {
                        fairness.add(i);
                        sendToDestination(r.get(i), bandwidth);
                    }
                }
            }
        }
    }

    /**
     * This is a method which sends a packet from the Intermediate router to the destination router
     * @param p
     *      the packet to be sent
     * @param bandwidth
     *      maximum number of Packets the destination router can accept at a given time
     */
    public void sendToDestination(Packet p, int bandwidth) {
        int sendTo = (int) fairness.peek();
        if(destination.size() >= bandwidth) {       // if greater than bandwidth don't send, reset to next cycle
            p.setTimeToDest(1);
        }
//        if(destination.size() < bandwidth && p.getTimeToDest() == 0) {      // remove from intermediate routers, send to destination
//            for(int i=0; i<routers[sendTo].size(); i++) {
//                if(routers[sendTo].get(i) == p) {
//                    routers[sendTo].remove(i);
//                }
//            }
//            destination.add(p);
//        }
        routers[sendTo].dequeue();
        fairness.remove();
        totalPacketsArrived++;
        setTotalPacketsArrived(totalPacketsArrived);
        p.setServiceTime(currentTime - p.getTimeArrive());
        totalServiceTime += p.serviceTime;
        setTotalServiceTime(totalServiceTime);
        System.out.println("hw4.Packet " + p.getId() + " has successfully reached its destination: +" + p.serviceTime);
    }

    /**
     * A helper method to generate random number between range inclusively
     * @param minVal
     *      minimum value for random number
     * @param maxVal
     *      maximum value for random number
     * @return
     *      random number between range inclusively
     */
    private int randInt(int minVal, int maxVal) {
        return (int) (Math.random() * (maxVal+1-minVal) + minVal);
    }

    public static void main(String[] args) {
        Simulator s = new Simulator();
        boolean simulate = true;
        while (simulate) {
            System.out.println("Starting simulator...");
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the number of Intermediate routers: ");
            int numIntRouters = input.nextInt();
            System.out.print("Enter the arrival probability of a packet: ");
            double arrivalProb = input.nextDouble();
            System.out.print("Enter the maximum buffer size of a router: ");
            int maxBufferSize = input.nextInt();
            System.out.print("Enter the minimum size of a packet: ");
            int minPacketSize = input.nextInt();
            System.out.print("Enter the maximum size of a packet: ");
            int maxPacketSize = input.nextInt();
            System.out.print("Enter the bandwidth size: ");
            int bandwidth = input.nextInt();
            System.out.print("Enter the simulation duration: ");
            int duration = input.nextInt();

            s.simulate(numIntRouters, arrivalProb, maxBufferSize, minPacketSize, maxPacketSize, bandwidth, duration);

            input.nextLine();
            System.out.print("\nDo you want to try another simulation? (y/n): ");
            String answer = input.nextLine();
            if(answer.equals("n")) {
                simulate = false;
                System.out.println("Program terminating successfully...");
            }
        }
    }
}
