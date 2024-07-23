package com.github.bmTas.recedui.table.event;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public abstract class TableMouseAdapter extends MouseAdapter {

	public void mousePressed(MouseEvent e) {
		Object source = e.getSource();
		JTable table = source instanceof JTable  ? (JTable) source : null;
		int row=-1, column=-1;
		if (table != null) {
			if (! table.hasFocus()) {
				table.requestFocusInWindow();
			}
			Point point = e.getPoint();
			column = table.columnAtPoint(point);
			row = table.rowAtPoint(point);
			processMousePressed(table, row, column, e);
		}
	}

	public abstract void processMousePressed(JTable table, int row, int column, MouseEvent e);
}
