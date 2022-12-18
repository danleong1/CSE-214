/**
 * The WebGraph class implements a WebGraph containing WebPage objects using
 * ArrayLists and provides methods to perform various operations.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WebGraph {
    public static final int MAX_PAGES = 40;
    private ArrayList<WebPage> pages;
    private ArrayList<ArrayList<Integer>> links;

    /**
     * This is a constructor used to create a new WebGraph object
     */
    public WebGraph() {
        pages = new ArrayList<>(MAX_PAGES);
        links = new ArrayList<>(MAX_PAGES);
    }

    /**
     * This is a getter method that returns the pages ArrayList
     * @return
     *      pages ArrayList
     */
    public ArrayList<WebPage> getPages() {
        return pages;
    }

    /**
     * Constructs a WebGraph object using the indicated files
     * @param pagesFile
     *      String of the relative path to the file containing the page information
     * @param linksFile
     *      String of the relative path to the file containing the link information
     * @return
     *      The constructed WebGraph
     * @throws IllegalArgumentException
     *      when the text files are invalid or not formatted correctly
     * @throws FileNotFoundException
     *      when pagesFile or linksFile is not found
     */
    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException, FileNotFoundException {
        WebGraph webGraph = new WebGraph();
        Scanner scanPages = new Scanner(new File(pagesFile));
        while(scanPages.hasNextLine()) {
            if(webGraph.pages.size() == MAX_PAGES)
                throw new IllegalArgumentException("\nMaximum number of pages exceeded.\n");
            String[] pagesSplit = scanPages.nextLine().trim().split(" ");
            if(!pagesSplit[0].contains("."))
                throw new IllegalArgumentException("\nInvalid text file.\n");
            if(webGraph.findURL(pagesSplit[0]))
                throw new IllegalArgumentException("\nPage " + pagesSplit[0] + " already exists in the WebGraph!\n");
            ArrayList<String> keywords = new ArrayList<>(Arrays.asList(pagesSplit).subList(1, pagesSplit.length));
            WebPage webPage = new WebPage(pagesSplit[0], keywords);
            webGraph.pages.add(webPage);
            webPage.setIndex(webGraph.pages.indexOf(webPage));
        }
        Scanner scanLinks = new Scanner(new File(linksFile));
        int counter = 0;
        while(scanLinks.hasNextLine()) {
            String[] linksSplit = scanLinks.nextLine().trim().split(" ");
            if(linksSplit.length != 2)
                throw new IllegalArgumentException("\nInvalid text file\n");
            String source = linksSplit[0];
            String dest = linksSplit[1];
            if (!source.contains(".") | !dest.contains("."))
                throw new IllegalArgumentException("\nText file improperly formatted.\n");
            int sourceIndex = searchWebPageIndex(webGraph.pages, source);
            int destIndex = searchWebPageIndex(webGraph.pages, dest);
            if (sourceIndex == -1 || destIndex == -1)
                throw new IllegalArgumentException("\nSource/Destination could not be found in the WebGraph.\n");
            int row = 0;
            boolean iterate = true;
            do {
                ArrayList<Integer> matrixRow = new ArrayList<>();
                for (int i = 0; i < webGraph.pages.size(); i++)
                    matrixRow.add(0);
                if (counter < webGraph.pages.size())
                    webGraph.links.add(matrixRow);
                if (sourceIndex == row) {
                    int column = 0;
                    do {
                        if (destIndex == column) {
                            ArrayList<Integer> targetRow = webGraph.links.get(row);
                            targetRow.set(column, 1);
                            iterate = false;
                            counter++;
                            break;
                        }
                        column++;
                    } while (column < webGraph.links.get(row).size());
                }
                if (!iterate)
                    break;
                row++;
                if (row == webGraph.links.size()-2)
                    webGraph.links.remove(webGraph.links.size()-1);
            } while (row < webGraph.links.size());
        }
        scanLinks.close();
        scanPages.close();
        for(int i=0; i<webGraph.links.size(); i++) {
            ArrayList<Integer> outputLinks = new ArrayList<>();
            for(int j=0; j<webGraph.links.get(i).size(); j++)
                if(webGraph.links.get(i).get(j) == 1) {
                    outputLinks.add(j);
                }
            webGraph.pages.get(i).setLinks(outputLinks.toString().replace("[", "").replace("]", ""));
        }
        return webGraph;
    }

    /**
     * This is a helper method that returns the index of the WebPage with the given URL
     * @param pages
     *      pages ArrayList
     * @param url
     *      The indicated URL
     * @return
     *      index of the WebPage with the given URL
     */
    public static int searchWebPageIndex(ArrayList<WebPage> pages, String url) {
        for(WebPage page: pages) {
            if(page.getUrl().equals(url))
                return page.getIndex();
        }
        return -1;
    }

    /**
     * Adds a page to the WebGraph
     * @param url
     *      URL of the page to add
     * @param keywords
     *      Keywords of the page to add
     * @throws IllegalArgumentException
     *      when URL is already exists in graph or URl or keywords are null
     */
    public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException {
        if(url == null)
            throw new IllegalArgumentException("\nURL is null.\n");
        if(keywords == null)
            throw new IllegalArgumentException("\nKeywords is null\n");
        if(findURL(url))
            throw new IllegalArgumentException("\nError: " + url + " already exists in the WebGraph. Could not add new WebPage.\n");
        pages.add(new WebPage(url, keywords, pages.size()));
        ArrayList<Integer> newRow = new ArrayList<>();
        for(int i=0; i<pages.size(); i++)
            newRow.add(0);
        links.add(newRow);
        for(ArrayList<Integer> link: links)
            link.add(0);
        updatePageRanks();
    }

    /**
     * Adds a link from the indicated source to destination in the WebGraph
     * @param source
     *      URL of the source
     * @param destination
     *      URL of the destination
     * @throws IllegalArgumentException
     *      when either URL is null or cannot be found in pages ArrayList
     */
    public void addLink(String source, String destination) throws IllegalArgumentException {
        if(source == null)
            throw new IllegalArgumentException("\nSource URL is null.\n");
        if(destination == null)
            throw new IllegalArgumentException("\nDestination URL is null.\n");
        if(!findURL(source))
            throw new IllegalArgumentException("\nError: " + source + " could not be found in the WebGraph.\n");
        if(!findURL(destination))
            throw new IllegalArgumentException("\nError: " + destination + " could not be found in the WebGraph.\n");
        int srcIndex = searchWebPageIndex(pages, source);
        int destIndex = searchWebPageIndex(pages, destination);
        if(links.get(srcIndex).get(destIndex) == 1)
            throw new IllegalArgumentException("\nError: Link was already established\n");
        if(!links.get(srcIndex).contains(1)) {       // if source doesn't already have link
            links.get(srcIndex).set(destIndex, 1);
            pages.get(srcIndex).setLinks(String.valueOf(destIndex));
        }
        else {
            String[] linksArr = pages.get(srcIndex).getLinks().replace(",", "").split(" ");
            ArrayList<String> newLinksArr = new ArrayList<>();
            newLinksArr.addAll(List.of(linksArr));
            newLinksArr.add(String.valueOf(destIndex));
            Collections.sort(newLinksArr);
            links.get(srcIndex).set(destIndex, 1);
            pages.get(srcIndex).setLinks(newLinksArr.toString().replace("[", "").replace("]", ""));
        }
        updatePageRanks();
    }

    /**
     * This is a helper method that checks whether a WebPage with the given URL exists in the WebGraph
     * @param url
     *      indicated URL to search for
     * @return
     *      true if URL exists, false otherwise
     */
    public boolean findURL(String url) {
        for(WebPage webPage: pages) {
            if(webPage.getUrl().equals(url))
                return true;
        }
        return false;
    }

    /**
     * This is a helper method that checks if any WebPage in the WebGraph contains the indicated keyword
     * @param keyword
     *      the indicated keyword to search for
     * @return
     *      true if keyword found, false otherwise
     */
    public boolean findKeyword(String keyword) {
        for(WebPage webPage: pages) {
            if(webPage.getKeywords().contains(keyword))
                return true;
        }
        return false;
    }

    /**
     * Removes the WebPage with the given URL from the WebGraph
     * @param url
     *      the given URL
     */
    public void removePage(String url) {
        if(url == null || !findURL(url))
            return;
        int removeIndex = 0;
        for(WebPage webPage: pages) {
            if(webPage.getUrl().equals(url)) {
                removeIndex = webPage.getIndex();
                break;
            }
        }
        pages.remove(removeIndex);
        for(int i=removeIndex; i<pages.size(); i++) {
            pages.get(i).setIndex(pages.get(i).getIndex()-1);
        }
        links.remove(removeIndex);
        for(int row=0; row < links.size(); row++) {
            if(pages.get(row).getLinks().contains(String.valueOf(removeIndex))) {
                String[] linksArr = pages.get(row).getLinks().replace(",", "").split(" ");
                ArrayList<String> newLinksArr = new ArrayList<>();
                newLinksArr.addAll(List.of(linksArr));
                int toRemove = newLinksArr.indexOf(String.valueOf(removeIndex));
                newLinksArr.remove(toRemove);
                if(toRemove < linksArr.length)
                    for(int i=toRemove; i<newLinksArr.size(); i++) {
                        newLinksArr.set(i, String.valueOf(Integer.parseInt(newLinksArr.get(i))-1));
                    }
                Collections.sort(newLinksArr);
                pages.get(row).setLinks(newLinksArr.toString().replace("[", "").replace("]", ""));
            }
            if(row >= removeIndex) {
                String[] linksArr = pages.get(row).getLinks().replace(",", "").split(" ");
                ArrayList<String> newLinksArr = new ArrayList<>();
                newLinksArr.addAll(List.of(linksArr));
                if(!newLinksArr.contains("")) {
                    for (int i = 0; i < newLinksArr.size(); i++) {
                        if (Integer.parseInt(newLinksArr.get(i)) > removeIndex)
                            newLinksArr.set(i, String.valueOf(Integer.parseInt(newLinksArr.get(i)) - 1));
                    }
                }
                pages.get(row).setLinks(newLinksArr.toString().replace("[", "").replace("]", ""));
            }
            links.get(row).remove(removeIndex);
        }
        updatePageRanks();
    }

    /**
     * Removes the link from the given source to the destination from the WebGraph
     * @param source
     *      URL of the source
     * @param destination
     *      URL of the destination
     */
    public void removeLink(String source, String destination) {
        if(!findURL(source) || !findURL(destination))
            return;
        int sourceIndex = searchWebPageIndex(pages, source);
        int destIndex = searchWebPageIndex(pages, destination);
        links.get(sourceIndex).set(destIndex, 0);
        String[] linksArr = pages.get(sourceIndex).getLinks().replace(",", "").split(" ");
        ArrayList<String> newLinksArr = new ArrayList<>();
        newLinksArr.addAll(List.of(linksArr));
        newLinksArr.remove(String.valueOf(destIndex));
        Collections.sort(newLinksArr);
        pages.get(sourceIndex).setLinks(newLinksArr.toString().replace("[", "").replace("]", ""));
        updatePageRanks();
    }

    /**
     * Calculates the PageRank for every page in the WebGraph
     */
    public void updatePageRanks() {
        for(WebPage page: pages)
            page.setRank(0);
        for(WebPage page: pages) {
            for(int row=0; row<links.size(); row++) {
                if(links.get(row).get(page.getIndex()) == 1)
                    page.setRank(page.getRank()+1);
            }
        }
    }

    /**
     * Prints the WebGraph in tabular form
     */
    public void printTable() {
        System.out.println();
        System.out.printf("%-10s%-18s%-10s%-20s%-8s", "Index", "URL", "PageRank", "Links", "Keywords");
        System.out.println();
        for(int i=0; i<86; i++)
            System.out.print("-");
        System.out.println();
        for(int i=0; i<links.size(); i++) {
            if(!pages.get(i).getLinks().equals("***"))
                System.out.println(pages.get(i));
            else {
                pages.get(i).setLinks("");
                System.out.println(pages.get(i));
            }
        }
        System.out.println();
    }

    /**
     * Searches for the WebPages in the WebGraph with the indicated keyword
     * and prints them in tabular form
     * @param keyword
     *      indicated keyword to search for
     * @throws IllegalArgumentException
     *      when no Webpages contain the keyword
     */
    public void searchKeyword(String keyword) throws IllegalArgumentException {
        System.out.println();
        if(!findKeyword(keyword))
            throw new IllegalArgumentException("No search results found for the keyword " + keyword + ".\n");
        System.out.printf("%-7s%-12s%-3s", "Rank", "PageRank", "URL");
        System.out.println();
        for(int i=0; i<86; i++)
            System.out.print("-");
        System.out.println();
        ArrayList<WebPage> output = new ArrayList<>();
        for(WebPage webPage: pages) {
            if(webPage.getKeywords().contains(keyword))
                output.add(webPage);
        }
        Collections.sort(output, new RankComparator());
        for(WebPage webPage: output) {
            System.out.printf("  %-3s|    %-6s| %-3s", output.indexOf(webPage)+1, webPage.getRank(), webPage.getUrl());
            System.out.println();
        }
    }

}
