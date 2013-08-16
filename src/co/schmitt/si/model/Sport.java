package co.schmitt.si.model;

import co.schmitt.si.ontology.OntologyString;

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
        StringBuilder sb = new StringBuilder(getClearName());
        sb.append(": ").append(date);
        return sb.toString();
    }

    public String getName() {
        return this.mName;
    }

    public String getClearName() {
        return OntologyString.convert(this.mName);
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

    public void setMinParticipants(int minParticipants) {
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

    public boolean hasValidTrainingDates() {
        return !getValidTrainingDates().isEmpty();
    }

    public List<TrainingDate> getValidTrainingDates() {
        List<TrainingDate> validTrainingDates = new ArrayList<>();
        for (TrainingDate td : mTrainingDates) {
            if (td.isValidTraningDate()) {
                validTrainingDates.add(td);
            }
        }
        return validTrainingDates;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append(mName).append(" - Fees: ").append(mFees).append("EUR").append(" Parts: ").append(mParticipants).append("(").append(mMinParticipants).append("|").append(mMaxParticipants).append(")\nDates: ");
        for (TrainingDate td : mTrainingDates) {
            sb.append(td.toString()).append("; ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sport) {
            return this.mName.equals(((Sport) obj).getName());
        }
        return super.equals(obj);
    }

    // TODO rename getStringRepresentation in toString?
    @Override
    public String toString() {
        return "<Sport> " + mName;
    }
}
