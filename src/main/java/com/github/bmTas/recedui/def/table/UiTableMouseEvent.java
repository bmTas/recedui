package com.github.bmTas.recedui.def.table;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class UiTableMouseEvent {
	private int row=Integer.MIN_VALUE, column;
	public final MouseEvent mouseEvent;
	
	public UiTableMouseEvent(MouseEvent mouseEvent, int row, int column) {
		super();
		this.mouseEvent = mouseEvent;
		this.row = row;
		this.column = column;
	}
	
	public UiTableMouseEvent(MouseEvent mouseEvent) {
		super();
		this.mouseEvent = mouseEvent;
	}

	public int getRow() {
		init();
		return row;
	}

	public int getColumn() {
		init();
		return column;
	}
	
	private void init() {
		if (row == Integer.MIN_VALUE) {
			Component component = mouseEvent.getComponent();
			if (component instanceof JTable) {
				JTable table = (JTable) component;
			
				column = table.columnAtPoint(mouseEvent.getPoint());
				row = table.rowAtPoint(mouseEvent.getPoint());
				
				if (column >= 0 && column < table.getColumnCount()) {
					column = table.getColumnModel().getColumn(column).getModelIndex();
				}
			}
		}
	}
}
