package co.schmitt.si.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Sophia
 */
public class TrainingDate {

    private DAY day;
    private Time startTime;
    private Time endTime;

    public TrainingDate(int day, Date startTime, Date endTime) {
        switch (day) {
            case 1:
                this.day = DAY.MONDAY;
                break;
            case 2:
                this.day = DAY.TUESDAY;
                break;
            case 3:
                this.day = DAY.WEDNESDAY;
                break;
            case 4:
                this.day = DAY.THURSDAY;
                break;
            case 5:
                this.day = DAY.FRIDAY;
                break;
            case 6:
                this.day = DAY.SATURDAY;
                break;
            case 7:
                this.day = DAY.SUNDAY;
                break;
        }

        Calendar calendar = GregorianCalendar.getInstance();
        if (startTime != null) {
            calendar.setTime(startTime);
            this.startTime = new Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        }
        if (endTime != null) {
            calendar.setTime(endTime);
            this.endTime = new Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        }
    }

    public boolean isValidTraningDate() {
        return (this.day != null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof TrainingDate && this.endTime != null && this.startTime != null) {
            TrainingDate comparandum = (TrainingDate) obj;
            return (this.day == comparandum.getDay() && this.startTime.equals(comparandum.getStartTime()) && this.endTime.equals(comparandum.getEndTime()));
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.day != null) {
            sb.append(this.day.toString()).append(" - ");
        }
        if (startTime != null) {
            sb.append(startTime.toString());
        }
        if (endTime != null) {
            sb.append(" - ").append(endTime.toString()).append(" Uhr");
        }
        return sb.toString();
    }

    public DAY getDay() {
        return day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public enum DAY {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public class Time {
        private int hours;
        private int minutes;

        public Time(int hours, int minutes) {
            this.hours = hours;
            this.minutes = minutes;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Time) {
                Time compTime = (Time) obj;
                return (this.minutes == compTime.getMinutes() && this.hours == compTime.getHours());
            }
            return super.equals(obj);
        }

        @Override
        public String toString() {
            if (hours < 0 && minutes < 0) {
                return "?";
            }
            StringBuilder sb = new StringBuilder();
            if (hours < 10) {
                sb.append("0");
            }
            sb.append(hours).append(":");
            if (minutes < 10) {
                sb.append("0");
            }
            return sb.append(minutes).toString();
        }
    }
}
