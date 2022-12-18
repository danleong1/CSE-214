/**
 * The SearchEngine class implements a menu-based application that performs
 * various operations on a WebGraph object created from the user-specified files.
 * @author Daniel Leong
 *         114430807
 *         Recitation 02
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class SearchEngine {
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";
    private WebGraph web;

    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.web = new WebGraph();
        Scanner input = new Scanner(System.in);
        System.out.println("Loading WebGraph data...");
        try {
            searchEngine.web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
            searchEngine.web.updatePageRanks();
        } catch(IllegalArgumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Success!\n");
        while(true) {
            printMenu();
            String option = input.nextLine();
            switch (option.toUpperCase()) {
                case "AP":
                    System.out.print("Enter a URL: ");
                    String url = input.nextLine();
                    System.out.print("Enter keywords (space-separated): ");
                    String keywordsStr = input.nextLine();
                    String[] keywordsArr = keywordsStr.split(" ");
                    ArrayList<String> keywords = new ArrayList<>(Arrays.asList(keywordsArr));
                    try {
                        searchEngine.web.addPage(url, keywords);
                        System.out.println("\n" + url + " successfully added to the WebGraph!\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RP":
                    System.out.print("Enter a URL: ");
                    String url2 = input.nextLine();
                    searchEngine.web.removePage(url2);
                    System.out.println("\n" + url2 + " has been removed from the graph!\n");
                    break;
                case "AL":
                    System.out.print("Enter a source URL: ");
                    String source = input.nextLine();
                    System.out.print("Enter a destination URL: ");
                    String destination = input.nextLine();
                    try {
                        searchEngine.web.addLink(source, destination);
                        System.out.println("\nLink successfully added from " + source + " to " + destination + "!\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RL":
                    System.out.print("Enter a source URL: ");
                    String source2 = input.nextLine();
                    System.out.print("Enter a destination URL: ");
                    String destination2 = input.nextLine();
                    searchEngine.web.removeLink(source2, destination2);
                    System.out.println("\nLink removed from " + source2 + " to " + destination2 + "!\n");
                    break;
                case "S":
                    System.out.print("Search keyword: ");
                    String keyword = input.nextLine();
                    try {
                        searchEngine.web.searchKeyword(keyword);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "P":
                    printSubMenu();
                    String option2 = input.nextLine();
                    switch (option2.toUpperCase()) {
                        case "I":
                            Collections.sort(searchEngine.web.getPages(), new IndexComparator());
                            searchEngine.web.printTable();
                            break;
                        case "U":
                            Collections.sort(searchEngine.web.getPages(), new URLComparator());
                            searchEngine.web.printTable();
                            break;
                        case "R":
                            Collections.sort(searchEngine.web.getPages(), new RankComparator());
                            searchEngine.web.printTable();
                            break;
                    }
                    break;
                case "Q":
                    System.out.println("\nGoodbye.");
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
                "    (AP) - Add a new page to the graph.\n" +
                "    (RP) - Remove a page from the graph.\n" +
                "    (AL) - Add a link between pages in the graph.\n" +
                "    (RL) - Remove a link between pages in the graph.\n" +
                "    (P)  - Print the graph.\n" +
                "    (S)  - Search for pages with a keyword.\n" +
                "    (Q)  - Quit\n");
        System.out.print("Please select an option: ");
    }

    /**
     * This is a helper method that prints the Print submenu.
     */
    public static void printSubMenu() {
        System.out.print("    (I) Sort based on index (ASC)\n" +
                "    (U) Sort based on URL (ASC)\n" +
                "    (R) Sort based on rank (DSC)\n" +
                "\n" +
                "Please select an option: ");
    }
}
