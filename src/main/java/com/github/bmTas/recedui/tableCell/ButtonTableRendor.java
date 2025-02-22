/*
 * @Author Bruce Martin
 * Created on 12/02/2005
 *
 * Purpose:
 */
package com.github.bmTas.recedui.tableCell;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * General Table Cell rendor for a JButton
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ButtonTableRendor extends JButton
							   implements TableCellRenderer {

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
	 * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(
		JTable tbl,
		Object value,
		boolean isSelected,
		boolean hasFocus,
		int row,
		int column) {

		return this;
	}

}
