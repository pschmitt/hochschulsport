package co.schmitt.si.gui;

import java.awt.Dimension;

import javax.swing.JTable;

/**
 * Created by sophia.
 */
@SuppressWarnings("serial")
public class TimeTable extends JTable {
	
	public TimeTable() {
		super();
	}

	public TimeTable(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {	
		return getPreferredSize();
	}
	
}
