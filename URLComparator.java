/**
 * The URLComparator class sorts alphabetically ascending based on the URL of the WebPage
 */

import java.util.Comparator;

public class URLComparator implements Comparator {
    /**
     * Sorts alphabetically ascending based on the URL of the WebPage
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     *      sorts WebPages in WebGraph in ascending URL order
     */
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        return(w1.getUrl().compareTo(w2.getUrl()));
    }
}
