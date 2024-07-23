package com.github.bmTas.recedui.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Very basic TableModel based on an a List
 * 
 * @author Bruce Martin
 *
 * @param <RowEntry> One row in the Table. The table data is stored in a list
 */
@SuppressWarnings("serial")
public abstract class ListTableModel<RowEntry> extends AbstractTableModel {

	private List<RowEntry> tableList;
	private final String[] names;
	private boolean editable;

	
	public ListTableModel(List<RowEntry> tableList, String[] names, boolean editable) {
		super();
		this.tableList = tableList;
		this.names = names;
		this.editable = editable;
	}

	/**
	 * @return the tableList
	 */
	public List<RowEntry> getTableList() {
		return tableList;
	}

	/**
	 * @param tableList the tableList to set
	 */
	public void setTableList(List<RowEntry> tableList) {
		this.tableList = tableList;
		super.fireTableDataChanged();
	}

	/**
	 * @return the list
	 */
	public List<RowEntry> getList() {
		return tableList;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return editable;
	}

	@Override
	public int getRowCount() {
		return tableList.size();
	}

	@Override
	public int getColumnCount() {
		return names.length;
	}

	@Override
	public String getColumnName(int column) {
		return column >= names.length ? "" : names[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= tableList.size()) {
			return null;
		}
		return getValue(rowIndex, columnIndex, tableList.get(rowIndex));
	}
	
	protected abstract Object getValue(int row, int column, RowEntry rowEntry);


	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= tableList.size()) {
			return;
		}
		setValue(rowIndex, columnIndex, tableList.get(rowIndex), aValue);
	}

	protected void setValue(int row, int column, RowEntry rowEntry, Object value) {
		
	}

}
