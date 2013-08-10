package co.schmitt.si.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Sophia
 */
public class TrainingDate {

    public enum DAY {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

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
        calendar.setTime(startTime);
        this.startTime = new Time(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
        calendar.setTime(endTime);
        this.endTime = new Time(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(startTime.toString());
        sb.append(" - ").append(endTime.toString()).append(" Uhr");
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

    public class Time {
        private int hours;
        private int minutes;

        public Time(int hours, int minutes) {
            this.hours = hours;
            this.minutes = minutes;
        }

        @Override
        public String toString() {
            return hours + ":" + minutes;
        }
    }
}
