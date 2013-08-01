package co.schmitt.si.gui;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * Created by sophia.
 */
@SuppressWarnings("serial")
public class TimeTableModel extends AbstractTableModel {

	private String[] columnNames = { "Montag", "Dienstag", "Mittwoch",
			"Donnerstag", "Freitag" };
	private Vector<String>[] week;
	private Vector<String> mon;
	private Vector<String> tue;
	private Vector<String> wed;
	private Vector<String> thu;
	private Vector<String> fri;

	@SuppressWarnings("unchecked")
	public TimeTableModel() {
		super();
		week = new Vector[5];;
		mon = new Vector<String>();
		mon.add("Tanzen: 10:30 - 11:30 Uhr");
		mon.add("Handball: 09:30 - 12:30 Uhr");
		mon.add("");
		tue = new Vector<String>();
		tue.add("Schwimmen: 11:00 - 13:30 Uhr");
		tue.add("Volleyball: 14:00 - 16:00 Uhr");
		tue.add("Pilates: 18:00 - 21:00 Uhr");
		wed = new Vector<String>();
		wed.add("Handball: 09:30 - 12:30 Uhr");
		wed.add("Schwimmen: 11:00 - 13:30 Uhr");
		wed.add("Pilates: 18:00 - 21:00 Uhr");
		thu = new Vector<String>();
		thu.add("Volleyball: 12:30 - 13:30 Uhr");
		thu.add("");
		thu.add("");
		fri = new Vector<String>();
		fri.add("Tanzen: 10:30 - 11:30 Uhr");
		fri.add("");
		fri.add("");
		week[0] = mon;
		week[1] = tue;
		week[2] = wed;
		week[3] = thu;
		week[4] = fri;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		int max = mon.size();
		for (int i = 1; i < week.length-1; i++) {
			if (week[i].size() > max){
				max = week[i].size();
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
		return courses.get(rowIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		week[columnIndex].set(rowIndex, (String) aValue);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
