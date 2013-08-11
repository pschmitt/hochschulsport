package co.schmitt.si.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pschmitt on 7/25/13.
 */
public class Sport {
    private String mName;
    private int mFees;
    private int mParticipants;
    private int mMinParticipants;
    private int mMaxParticipants;
    private List<TrainingDate> mTrainingDates;

    public Sport(String name) {
        this.mName = name;
        this.mFees = -1;
        this.mParticipants = -1;
        this.mMaxParticipants = -1;
        this.mMinParticipants = -1;
        this.mTrainingDates = new ArrayList<>();
    }

    public String getStringRepresentation(TrainingDate date) {
        StringBuffer sb = new StringBuffer(mName);
        sb.append(": ").append(date);
        return sb.toString();
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

    public void setMinnParticipants(int minParticipants) {
        this.mMinParticipants = minParticipants;
    }

    public int getParticipants() {
        return mParticipants;
    }

    public int getMaxParticipants() {
        return mMaxParticipants;
    }

    public int getMinParticipants() {
        return mMinParticipants;
    }

    public List<TrainingDate> getTrainingDates() {
        return mTrainingDates;
    }

    public void setTrainingDates(List<TrainingDate> mTrainingDates) {
        this.mTrainingDates = mTrainingDates;
    }

    public void addTrainingDates(TrainingDate trainingDate) {
        mTrainingDates.add(trainingDate);
    }

    // TODO rename getStringRepresentation in toString?
    @Override
    public String toString() {
        return "<Sport> " + mName;
    }
}
