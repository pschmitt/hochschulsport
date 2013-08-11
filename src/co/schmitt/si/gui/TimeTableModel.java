package co.schmitt.si.gui;

import co.schmitt.si.model.Sport;
import co.schmitt.si.model.TrainingDate;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

/**
 * Created by sophia.
 */
@SuppressWarnings("serial")
public class TimeTableModel extends AbstractTableModel {

    private String[] columnNames = {"Montag", "Dienstag", "Mittwoch",
            "Donnerstag", "Freitag", "Samstag", "Sonntag"};
    private Vector<String>[] week;
    private Vector<String> mon;
    private Vector<String> tue;
    private Vector<String> wed;
    private Vector<String> thu;
    private Vector<String> fri;
    private Vector<String> sat;
    private Vector<String> sun;

    @SuppressWarnings("unchecked")
    public TimeTableModel() {
        super();
        week = new Vector[7];
        mon = new Vector<>();
        tue = new Vector<>();
        wed = new Vector<>();
        thu = new Vector<>();
        fri = new Vector<>();
        sat = new Vector<>();
        sun = new Vector<>();
        week[0] = mon;
        week[1] = tue;
        week[2] = wed;
        week[3] = thu;
        week[4] = fri;
        week[5] = sat;
        week[6] = sun;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        int max = -1;
        for (Vector<String> dayOfTheWeek : week) {
            if (dayOfTheWeek.size() > max) {
                max = dayOfTheWeek.size();
            }
        }
        return max;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int columnIndex, int rowIndex) {
        Vector<String> courses = week[columnIndex];
        System.out.println("DEBUG:" + rowIndex + "; " + columnIndex + "; " + week[columnIndex]);
        if (courses == null) {
            System.out.println("null");
        }
        return courses.get(rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int columnIndex, int rowIndex) {
        //		week[columnIndex].set(rowIndex, (String) aValue);
        week[columnIndex].add((String) aValue);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void add(List<Sport> sports) {
        for (Sport sport : sports) {
            System.out.println("DEBUG: " + sport.getName() + ": " + sport.getTrainingDates());
            for (TrainingDate td : sport.getTrainingDates()) {
                switch (td.getDay()) {
                    case MONDAY:
                        setValueAt(sport.getStringRepresentation(td), 0, mon.size());
                        break;
                    case TUESDAY:
                        setValueAt(sport.getStringRepresentation(td), 1, tue.size());
                        break;
                    case WEDNESDAY:
                        setValueAt(sport.getStringRepresentation(td), 2, wed.size());
                        break;
                    case THURSDAY:
                        setValueAt(sport.getStringRepresentation(td), 3, thu.size());
                        break;
                    case FRIDAY:
                        setValueAt(sport.getStringRepresentation(td), 4, fri.size());
                        break;
                    case SATURDAY:
                        setValueAt(sport.getStringRepresentation(td), 5, sat.size());
                        break;
                    case SUNDAY:
                        setValueAt(sport.getStringRepresentation(td), 6, sun.size());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
