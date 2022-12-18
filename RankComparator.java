/**
 * The RankComparator class sorts numerically descending based on the PageRank of the WebPage
 */

import java.util.Comparator;

public class RankComparator implements Comparator {

    /**
     * Sorts numerically descending based on the PageRank of the WebPage
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     *      0 if same rank, 1 if o2 has higher rank, -1 if o1 has higher rank
     */
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        if(w1.getRank() == w2.getRank())
            return 0;
        else if(w1.getRank() < w2.getRank())
            return 1;
        else
            return -1;
    }
}
