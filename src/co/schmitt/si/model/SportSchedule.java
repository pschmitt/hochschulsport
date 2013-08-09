package co.schmitt.si.model;

import java.util.Date;

/**
 * User: pschmitt
 * Date: 8/10/13
 * Time: 12:41 AM
 */
public class SportSchedule {
    private Date mStart;
    private Date mEnd;

    public SportSchedule(Date start, Date end) {
        mStart = start;
        mEnd = end;
    }

    public Date getStart() {
        return mStart;
    }

    public Date getEnd() {
        return mEnd;
    }
}
