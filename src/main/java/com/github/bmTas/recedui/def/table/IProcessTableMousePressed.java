package com.github.bmTas.recedui.def.table;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

/**
 * Process a Generic Mouse pressed on a JTable
 * @author Bruce Martin
 *
 */
public interface IProcessTableMousePressed {
	/**
	 * Process a mouse pressed on a JTable
	 * @param table The Table where the event occured;
	 * @param row the row that was pressed
	 * @param column Column that was pressed
	 * @param event Mouse Event
	 */
	void processMousePressed(IUiTable table, int row, int column, MouseEvent event);
}
