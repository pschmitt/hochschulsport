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

	private String[] columnNames = { "Montag", "Dienstag", "Mittwoch",
			"Donnerstag", "Freitag", "Samstag", "Sonntag" };
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vector<String> courses = week[columnIndex];
		if (courses == null) {
			System.out.println("null");
		}
		return courses.get(rowIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// week[columnIndex].set(rowIndex, (String) aValue);
		week[columnIndex].add((String) aValue);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void add(List<Sport> sports) {
		for (Sport sport : sports) {
			for (TrainingDate td : sport.getTrainingDates()) {
				switch (td.getDay()) {
				case MONDAY:
					setValueAt(sport.getStringRepresentation(td), mon.size(), 0);
					break;
				case TUESDAY:
					setValueAt(sport.getStringRepresentation(td), tue.size(), 1);
					break;
				case WEDNESDAY:
					setValueAt(sport.getStringRepresentation(td), wed.size(), 2);
					break;
				case THURSDAY:
					setValueAt(sport.getStringRepresentation(td), thu.size(), 3);
					break;
				case FRIDAY:
					setValueAt(sport.getStringRepresentation(td), fri.size(), 4);
					break;
				case SATURDAY:
					setValueAt(sport.getStringRepresentation(td), sat.size(), 5);
					break;
				case SUNDAY:
					setValueAt(sport.getStringRepresentation(td), sun.size(), 6);
					break;
				default:
					break;
				}
			}
		}

		fillTable();
	}

	private void fillTable() {
		int max = mon.size();

		for (int i = 1; i < 7; i++) {
			if (week[i].size() > max) {
				max = week[i].size();
			}
		}

		for (int i = 0; i < 7; i++) {
			for (int j = week[i].size(); j < max; j++) {
				week[i].add("");
			}
		}
	}
}
