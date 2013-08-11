package co.schmitt.si.model;

import java.util.List;

/**
 * User: pschmitt
 * Date: 8/11/13
 * Time: 7:11 PM
 */
public class DLQuery {
    private String mQuery;
    private List<String> mNotQuery;

    public DLQuery(String query, List<String> notQuery) {
        this.mQuery = query;
        this.mNotQuery = notQuery;
    }

    public String getQuery() {
        return mQuery;
    }

    public List<String> getNotQuery() {
        return mNotQuery;
    }
}
