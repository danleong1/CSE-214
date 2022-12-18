package hw6; /**
 * The hw6.Auction class implements an hw6.Auction object and provides methods
 * to perform various operations.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */

import java.io.Serializable;
import java.util.Scanner;

public class Auction implements Serializable {
    private int timeRemaining;
    private double currentBid;
    private String auctionID, sellerName, buyerName, itemInfo;

    /**
     * This is a constructor used to create a new hw6.Auction object
     * @param timeRemaining
     *      time remaining in hours
     * @param currentBid
     *      current bid for the auction
     * @param auctionID
     *      the unique ID (key) for the auction
     * @param sellerName
     *      seller name of item in auction
     * @param buyerName
     *      buyer name of item in auction
     * @param itemInfo
     *      information about item being sold in auction
     */
    public Auction(int timeRemaining, double currentBid, String auctionID, String sellerName, String buyerName, String itemInfo) {
        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.itemInfo = itemInfo;
    }

    /**
     * This is a constructor used to create a new hw6.Auction object
     * @param auctionID
     *       the unique ID (key) for the auction
     * @param timeRemaining
     *       time remaining in the auction in hours
     * @param itemInfo
     *       information about item being sold in auction
     * @param sellerName
     *       seller name of item in auction
     */
    public Auction(String auctionID, int timeRemaining, String itemInfo, String sellerName) {
        this.auctionID = auctionID;
        this.timeRemaining = timeRemaining;
        this.itemInfo = itemInfo;
        this.sellerName = sellerName;
    }

    /**
     * This is a getter method that returns the time remaining in the auction in hours
     * @return
     *      timeRemaining
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * This is a getter method that returns the current bid of the auction
     * @return
     *      currentBid
     */
    public double getCurrentBid() {
        return currentBid;
    }

    /**
     * This is a getter method that returns the auction ID
     * @return
     *      auctionID
     */
    public String getAuctionID() {
        return auctionID;
    }

    /**
     * This is a getter method that returns the seller name of the item being sold in the auction
     * @return
     *      sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * This is a getter method that returns the buyer name of the item being sold in the auction
     * @return
     *      buyerName
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * This is a getter method that returns the information about the item being sold in the auction
     * @return
     *      itemInfo
     */
    public String getItemInfo() {
        return itemInfo;
    }

    /**
     * This is a method that decrements the time remaining in the auction by the specified amount of time
     * @param time
     *      the specified amount of time to decrement the time remaining in the auction
     */
    public void decrementTimeRemaining(int time) {
        if(time > timeRemaining)
            timeRemaining = 0;
        timeRemaining -= time;
        if(timeRemaining < 0)
            timeRemaining = 0;
    }

    /**
     * This is a method that places a new bid on the auction
     * @param bidderName
     *      bidder name
     * @param bidAmt
     *      the new bid amount (0 is passed at placeholder)
     * @throws ClosedAuctionException
     *      when the time remaining of the auction is 0
     * @throws IllegalArgumentException
     *      when the bidAmt is less than or equal to the current bid
     */
    public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException, IllegalArgumentException {
        if(timeRemaining == 0) {
            if(currentBid == 0)
                throw new ClosedAuctionException("\nhw6.Auction " + auctionID + " is CLOSED\n    Current Bid: None\n\nYou can no longer bid on this item.");
            else
                throw new ClosedAuctionException("\nhw6.Auction " + auctionID + " is CLOSED\n    Current Bid: $ " + String.format("%,.2f", currentBid)  + "\n\nYou can no longer bid on this item.\n");
        }
        System.out.println("\nhw6.Auction " + auctionID + " is OPEN");
        if(currentBid == 0)
            System.out.println("    Current Bid: None");
        else
            System.out.println("    Current Bid: $" + String.format("%,.2f", currentBid));
        Scanner input = new Scanner(System.in);
        System.out.print("\nWhat would you like to bid?: ");
        String bidStr = input.nextLine();
        bidAmt = Double.parseDouble(bidStr);
        if(bidAmt > currentBid) {
            currentBid = bidAmt;
            buyerName = bidderName;
            System.out.println("Bid accepted.");
        }
        else
            throw new IllegalArgumentException("Bid was not accepted.");
    }

    /**
     * Returns String of data members in tabular form
     * @return
     *      String of data members in tabular form
     */
    public String toString() {
        String currentBidStr = String.format("%,.2f", currentBid);
        if(currentBid == 0)
            currentBidStr = "";
        String itemInfoTruncated = itemInfo.substring(0, Math.min(itemInfo.length(), 42));
        if(buyerName == null)
            buyerName = "";
        if(currentBid != 0)
            return String.format("%11s | $%9s | %-22s | %-23s | %9s | %-43s", auctionID , currentBidStr,
                    sellerName, buyerName, timeRemaining + " hours", itemInfoTruncated);
        else
            return String.format("%11s | %10s | %-22s | %-23s | %9s | %-43s", auctionID , currentBidStr,
                    sellerName, buyerName, timeRemaining + " hours", itemInfoTruncated);
    }

    /**
     * Returns String representation of hw6.Auction
     * @return
     *      String representation of hw6.Auction
     */
    public String toString2() {
        return "hw6.Auction " + auctionID + ":\n" +
                "    Seller: " + sellerName + "\n" +
                "    Buyer: " + buyerName + "\n" +
                "    Time: " + timeRemaining + " hours\n" +
                "    Info: " + itemInfo;
    }

}
