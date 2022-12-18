/**
 * The Webpage class implements Webpage objects and makes their
 * corresponding attributes accessible and mutable.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */

import java.util.ArrayList;

public class WebPage {
    private String url, links;
    private int index, rank;
    private ArrayList<String> keywords;

    /**
     * This is a constructor used to create a new WebPage object
     * @param url
     *      URL of the webpage
     * @param keywords
     *      keywords of the webpage
     */
    public WebPage(String url, ArrayList<String> keywords) {
        this.url = url;
        this.keywords = keywords;
        links = "***";
    }

    /**
     * This is a constructor used to create a new WebPage object
     * @param url
     *      URL of the webpage
     * @param keywords
     *      keywords of the webpage
     * @param index
     *      index of the webpage in the WebGraph
     */
    public WebPage(String url, ArrayList<String> keywords, int index) {
        this.url = url;
        this.keywords = keywords;
        this.index = index;
        links = "***";
    }

    /**
     * A getter method that returns the URL of the webpage
     * @return
     *      URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * A getter method that returns the index of the webpage
     * @return
     *      index
     */
    public int getIndex() {
        return index;
    }

    /**
     * A getter method that returns the rank of the webpage
     * @return
     *      rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * A getter method that returns the keywords of the webpage
     * @return
     *      Arraylist of keywords
     */
    public ArrayList<String> getKeywords() {
        return keywords;
    }

    /**
     * A getter method that returns the links of the webpage
     * @return
     *      links
     */
    public String getLinks() {
        return links;
    }

    /**
     * A mutator method that changes the index
     * @param index
     *      index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * A mutator method that changes the rank
     * @param rank
     *      rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * A mutator method that changes the links of the webpage
     * @param links
     *      links
     */
    public void setLinks(String links) {
        this.links = links;
    }

    /**
     * This is a helper method to print the keywords ArrayList
     * @return
     *      String representation of keywords
     */
    public StringBuilder keywordsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";
        for(String keyword: keywords) {
            stringBuilder.append(separator);
            stringBuilder.append(keyword);
            separator = ", ";
        }
        return stringBuilder;
    }

    /**
     * Returns a tabular representation of the webpage
     * @return
     *      tabular representation of the webpage
     */
    public String toString() {
        return String.format("  %-3s | %-18s |    %-4s | %-17s | %-20s", index, url, rank, links, keywordsToString());
    }

}
