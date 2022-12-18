package hw6; /**
 * The hw6.AuctionTable class implements a Hashmap containing Auctions
 * and provides methods to perform various operations.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */

import big.data.DataSource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AuctionTable extends HashMap<String, Auction> implements Serializable {

    /**
     * This is a constructor used to create a new hw6.AuctionTable object
     */
    public AuctionTable() {
    }

    /**
     * This is a method that builds an hw6.AuctionTable from the specified URL
     * @param URL
     *      the specified URL to build the hw6.AuctionTable from
     * @return
     *      hw6.AuctionTable from URL
     * @throws IllegalArgumentException
     *      when URL can't connect or has invalid syntax
     */
    public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException {
        AuctionTable auctionTable = new AuctionTable();
        DataSource ds = DataSource.connect(URL).load();
        String[] auctionIDs = ds.fetchStringArray("listing/auction_info/id_num");
        String[] bids = ds.fetchStringArray("listing/auction_info/current_bid");
        String[] sellers = ds.fetchStringArray("listing/seller_info/seller_name");
        String[] buyers = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
        String[] times = ds.fetchStringArray("listing/auction_info/time_left");
        String[] cpu = ds.fetchStringArray("listing/item_info/cpu");
        String[] memory = ds.fetchStringArray("listing/item_info/memory");
        String[] hard_drive = ds.fetchStringArray("listing/item_info/hard_drive");

        for (int i = 0; i < auctionIDs.length; i++) {
            double bid = Double.parseDouble(bids[i].replaceAll("[$,]", ""));
            String[] timesArr = times[i].split(" ");
            int time;
            if((times[i].contains("days") | (times[i].contains("day"))) && (times[i].contains("hours") | times[i].contains("hrs"))) {
                time = Integer.parseInt(timesArr[0])*24 + Integer.parseInt(timesArr[2]);
            }
            else if((times[i].contains("days") | times[i].contains("day")) && (!times[i].contains("hours") | !times[i].contains("hrs"))) {
                time = Integer.parseInt(timesArr[0])*24;
            }
            else {
                time = Integer.parseInt(timesArr[0]);
            }

            if(sellers[i].contains(" ")) {
                sellers[i] = sellers[i].replaceAll("\n", "");
                sellers[i] = sellers[i].replaceAll("\r", "");
                int spaceCount = sellers[i].length() - sellers[i].replace(" ", "").length();
                for(int j=1; j<spaceCount; j++) {
                    sellers[i] = sellers[i].replaceFirst(" ", "");
                }
            }

            String item_info_column = cpu[i] + " - " + memory[i] + " - " + hard_drive[i];
            if(cpu[i].equals("") && memory[i].equals("") && hard_drive[i].equals(""))
                item_info_column = "";
            else if(memory[i].equals("") && hard_drive[i].equals(""))
                item_info_column = cpu[i];

            Auction auction = new Auction(time, bid, auctionIDs[i], sellers[i], buyers[i], item_info_column);
            auctionTable.putAuction(auctionIDs[i], auction);
        }
        return auctionTable;
    }

    /**
     * This is a method that posts an auction to the hw6.AuctionTable
     * @param auctionID
     *      The unique ID of the auction to be added to the hw6.AuctionTable
     * @param auction
     *      The auction to insert into the hw6.AuctionTable
     * @throws IllegalArgumentException
     *      when the auctionID is already stored in the hw6.AuctionTable
     */
    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException {
        if(getAuction(auctionID) != null)
            throw new IllegalArgumentException("hw6.Auction already in hw6.AuctionTable");
        put(auctionID, auction);
    }

    /**
     * This is a method that returns the hw6.Auction with the specified ID
     * @param auctionID
     *      the unique ID of the hw6.Auction searched for
     * @return
     *      the hw6.Auction with the specified ID
     */
    public Auction getAuction(String auctionID) {
        if(get(auctionID) == null)
            return null;
        return get(auctionID);
    }

    /**
     * This is a method that decreases the timeRemaining of all Auctions in the hw6.AuctionTable
     * @param numHours
     *      The amount of timeRemaining to decrement
     * @throws IllegalArgumentException
     *      When numHours is non-positive
     */
    public void letTimePass(int numHours) throws IllegalArgumentException {
        if(numHours < 0)
            throw new IllegalArgumentException("Hours cannot be negative.");
        for(Auction a: values()) {
            a.decrementTimeRemaining(numHours);
        }
    }

    /**
     * This is a method that removes all expired Auctions in the hw6.AuctionTable
     */
    public void removeExpiredAuctions() {
        ArrayList<String> toRemove = new ArrayList<>();
        for(Auction a: values()) {
            if(a.getTimeRemaining() == 0)
                toRemove.add(a.getAuctionID());
        }
        for(int i=0; i<toRemove.size(); i++)
            remove(toRemove.get(i));
    }

    /**
     * This method prints the hw6.AuctionTable in tabular form
     */
    public void printTable() {
        System.out.println("\n hw6.Auction ID |      Bid   |        Seller          |          Buyer          |    Time   |  Item Info ");
        for(int i=1; i<=132; i++)
            System.out.print("=");
        System.out.println();
        for(String auctionID: keySet())
            System.out.println(getAuction(auctionID));
        System.out.println();
    }
}
