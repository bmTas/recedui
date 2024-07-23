package com.github.bmTas.recedui.table.event;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.IProcessTableMousePressed;
import com.github.bmTas.recedui.def.table.IUiTable;

public class TableMousePressed extends TableMouseAdapter {

	private final IProcessTableMousePressed processMousePress;
	public final IUiTable uiTable;

	public TableMousePressed(IUiTable uiTable, IProcessTableMousePressed processMousePress) {
		super();
		this.uiTable = uiTable;
		this.processMousePress = processMousePress;
	}

	@Override
	public void processMousePressed(JTable jTable, int row, int column, MouseEvent e) {
		processMousePress.processMousePressed(uiTable, row, column, e);
	}

}
