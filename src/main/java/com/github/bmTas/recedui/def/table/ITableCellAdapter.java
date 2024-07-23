package com.github.bmTas.recedui.def.table;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public interface ITableCellAdapter {

	public abstract TableCellRenderer getCellRenderer(JTable tbl, int row, int column);

	public abstract TableCellEditor getCellEditor(JTable tbl,int row, int column);

}