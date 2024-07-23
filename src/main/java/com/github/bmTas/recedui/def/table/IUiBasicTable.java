package com.github.bmTas.recedui.def.table;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public interface IUiBasicTable {

	JTable getMainComponent();

	TableModel getModel();

	TableColumnModel getColumnModel();

	void addTableMouseListner(IUiTableMouseListner listner);

	/**
	 * Set the Column Resize option
	 * see {@link JTable#setAutoResizeMode}
	 * 
	 * @param mode mode to select:<ul>
	 * <li>JTable.AUTO_RESIZE_OFF
	 * <li>JTable.AUTO_RESIZE_NEXT_COLUMN
	 * <li>JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
	 * <li>JTable.AUTO_RESIZE_LAST_COLUMN
	 * <li>JTable.AUTO_RESIZE_ALL_COLUMNS
	 * </ul>
	 * 
	 */
	void setAutoResizeMode(int mode);

	/**
	 * Whether to terminate editing when focus is lost
	 * @param terminateOption Whether to terminate editing when focus is lost
	 */
	void terminateEditOnFocusLost(boolean terminateOption);

	/**
	 * Stop cell editing
	 */
	void stopCellEditing();

	/**
	 * remove a mouse listner
	 * @param listner
	 */
	void removeTableMouseListner(Object listner);

}