/**
 * The IndexComparator class sorts numerically ascending based on the index of the WebPage
 */

import java.util.Comparator;

public class IndexComparator implements Comparator {
    /**
     * Sorts numerically ascending based on the index of the WebPage
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     *      sorts WebPages in WebGraph in ascending index order
     */
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        return Integer.compare(w1.getIndex(), w2.getIndex());
    }
}
