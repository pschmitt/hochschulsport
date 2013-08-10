package co.schmitt.si.model;

/**
 * @author Sophia
 */
public class TrainingDate {

    public enum DAY {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    }

    private DAY day;
    private Time startTime;
    private Time endTime;

    public TrainingDate(DAY day, int startTimeHours, int startTimeMinutes,
                        int endTimeHours, int endTimeMinutes) {

        this.day = day;
        this.startTime = new Time(startTimeHours, startTimeMinutes);
        this.endTime = new Time(endTimeHours, endTimeMinutes);
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
