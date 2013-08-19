package co.schmitt.si.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User: pschmitt
 * Date: 8/19/13
 * Time: 10:11 PM
 */
public class ListDuplicateRemover {
    private ListDuplicateRemover() {}

    /**
     * Remove duplicate elements from a list
     *
     * @param list The list
     * @param <T>  Any type, whatever
     * @return A duplicate free list
     */
    public static <T> List<T> removeDuplicates(final List<T> list) {
        // add elements to al, including duplicates
        HashSet hs = new HashSet();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
        return list;
    }

    /**
     * Remove duplicate elements from a list (deprecated)
     * Source: http://stackoverflow.com/a/18089051/1872036
     *
     * @param list The list
     * @param <T>  Any type, whatever
     * @return A duplicate free list
     */
    @Deprecated
    public static <T> List<T> removeDuplicatesLegacy(List<T> list) {
        ArrayList<T> uniqueList = new ArrayList<>();
        for (T obj : list) {
            if (!uniqueList.contains(obj)) {
                uniqueList.add(obj);
            }
        }

        return uniqueList;
    }
}
