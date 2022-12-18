package hw6; /**
 * The hw6.AuctionSystem class implements a menu-based application that performs
 * various operations on an hw6.AuctionTable created from the user-specified URL.
 * It provides functionality to load a serialized hw6.AuctionTable or create a new
 * hw6.AuctionTable if a saved table doesn't exist.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */

import java.io.*;
import java.io.Serializable;
import java.util.Scanner;

public class AuctionSystem implements Serializable {
    private AuctionTable auctionTable;
    private String username;

    public static void main(String[] args) {
        AuctionSystem auctionSystem = new AuctionSystem();
        Scanner input = new Scanner(System.in);
        deserialize(auctionSystem);
        System.out.print("\nPlease select a username: ");
        auctionSystem.username = input.nextLine();
        while(true) {
            printMenu();
            String option = input.nextLine();
            switch (option) {
                case "D":
                    System.out.print("Please enter a URL: ");
                    String url = input.nextLine().trim();
                    try {
                        if(auctionSystem.auctionTable != null) {
                            for(Auction a: AuctionTable.buildFromURL(url).values())
                                auctionSystem.auctionTable.putAuction(a.getAuctionID(), a);
                        }
                        else
                            auctionSystem.auctionTable = AuctionTable.buildFromURL(url);
                        System.out.println("Loading...");
                        System.out.println("hw6.Auction data loaded successfully!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "A":
                    System.out.println("Creating new hw6.Auction as " + auctionSystem.username + ".");
                    System.out.print("Please enter an hw6.Auction ID: ");
                    String auctionID = input.nextLine();
                    System.out.print("Please enter an hw6.Auction time (hours): ");
                    String timeStr = input.nextLine();
                    int time = Integer.parseInt(timeStr);
                    System.out.print("Please enter some Item Info: ");
                    String itemInfo = input.nextLine();
                    Auction auction = new Auction(auctionID, time, itemInfo, auctionSystem.username);
                    try {
                        auctionSystem.auctionTable.putAuction(auctionID, auction);
                        System.out.println("\nhw6.Auction " + auctionID + " inserted into table.");
                    } catch(IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "B":
                    System.out.print("Please enter an hw6.Auction ID: ");
                    String auctionID1 = input.nextLine();
                    Auction a = auctionSystem.auctionTable.getAuction(auctionID1);
                    try {
                        a.newBid(auctionSystem.username, 0);
                    } catch (ClosedAuctionException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "I":
                    System.out.print("Please enter an hw6.Auction ID: ");
                    String auctionID2 = input.nextLine();
                    System.out.println();
                    System.out.println(auctionSystem.auctionTable.get(auctionID2).toString2() + "\n");
                    break;
                case "P":
                    auctionSystem.auctionTable.printTable();
                    break;
                case "R":
                    System.out.println("\nRemoving expired auctions...");
                    auctionSystem.auctionTable.removeExpiredAuctions();
                    System.out.println("All expired auctions removed.\n");
                    break;
                case "T":
                    try {
                        System.out.print("How many hours should pass: ");
                        String timePassStr = input.nextLine();
                        int timePass = Integer.parseInt(timePassStr);
                        auctionSystem.auctionTable.letTimePass(timePass);
                        System.out.println("\nTime passing...");
                        System.out.println("hw6.Auction times updated.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "Q":
                    System.out.println("\nWriting hw6.Auction Table to file...");
                    serialize(auctionSystem);
                    System.out.println("Goodbye.\n");
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * This is a helper method which prints the menu.
     */
    public static void printMenu() {
        System.out.println("Menu:\n" +
                "    (D) - Import Data from URL\n" +
                "    (A) - Create a New hw6.Auction\n" +
                "    (B) - Bid on an Item\n" +
                "    (I) - Get Info on hw6.Auction\n" +
                "    (P) - Print All Auctions\n" +
                "    (R) - Remove Expired Auctions\n" +
                "    (T) - Let Time Pass\n" +
                "    (Q) - Quit\n");
        System.out.print("Please select an option: ");
    }

    /**
     * This is a method that serializes the hw6.AuctionTable
     * @param auctionSystem
     *      An instance of hw6.AuctionSystem
     */
    public static void serialize(AuctionSystem auctionSystem) {
        try {
            FileOutputStream file = new FileOutputStream("auction.obj");
            ObjectOutputStream outStream = new ObjectOutputStream(file);
            outStream.writeObject(auctionSystem.auctionTable);
            outStream.close();
            System.out.println("Done!\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This is a method that deserializes the hw6.AuctionTable
     * @param auctionSystem
     *      An instance of hw6.AuctionSystem
     */
    public static void deserialize(AuctionSystem auctionSystem) {
        System.out.println("Starting...");
        try {
            FileInputStream file = new FileInputStream("auction.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            auctionSystem.auctionTable = (AuctionTable) inStream.readObject();
            System.out.println("Loading previous hw6.Auction Table...");
        } catch (FileNotFoundException e) {
            System.out.println("No previous auction table detected.");
            System.out.println("Creating new table...");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

}