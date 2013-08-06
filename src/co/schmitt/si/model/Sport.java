package co.schmitt.si.model;

import java.util.Date;

/**
 * Created by pschmitt on 7/25/13.
 */
public class Sport {
    private String mName;
    private int mFees;
    private int mParticipants;
    private int mMaxParticipants;
    private Date mStartTime;
    private Date mEndTime;

    public Sport(String name) {
        this.mName = name;
        this.mFees = -1;
        this.mParticipants = -1;
        this.mMaxParticipants = -1;
    }

    public String getName() {
        return this.mName;
    }

    public int getFees() {
        return mFees;
    }

    public void setFees(int fees) {
        this.mFees = fees;
    }

    public void setParticipants(int participants) {
        this.mParticipants = participants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.mMaxParticipants = maxParticipants;
    }

    public int getParticipants() {
        return mParticipants;
    }

    public int getMaxParticipants() {
        return mMaxParticipants;
    }

    public Date getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Date endTime) {
        this.mEndTime = endTime;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date startTime) {
        this.mStartTime = startTime;
    }

    @Override
    public String toString() {
        return "<Sport> " + mName;
    }
}
