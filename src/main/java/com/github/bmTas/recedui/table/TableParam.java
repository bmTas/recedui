package com.github.bmTas.recedui.table;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.github.bmTas.recedui.styles.ITableStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class TableParam implements ITableParam {

	private ITableStyle style = StyleManager.EMPTY_STYLE;
	private JTable table;
	

	@Override
	public ITableStyle getStyle() {
		return style;
	}

	public TableParam setStyle(ITableStyle style) {
		this.style = style;
		return this;
	}

	@Override
	public JTable getTable() {
		return table;
	}

	public TableParam setTable(JTable table) {
		this.table = table;
		return this;
	}
	
	public TableParam setTableModel(TableModel model) {
		this.table = new JTable(model);
		return this;
	}

}
