package com.github.bmTas.recedui.table.event;

import java.util.ArrayList;

import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.IUiTableMouseListner;

public class TableMouseListnerManager {
	private final JTable table;
	final ArrayList<TableMouseEventTranslator> listners = new ArrayList<>(2);

	public TableMouseListnerManager(JTable table) {
		super();
		this.table = table;
	}
	
	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	public void add(IUiTableMouseListner event) {
		TableMouseEventTranslator listner = TableMouseEventTranslator.getMouseEventTranslator(event);
		table.addMouseListener(listner);
		listners.add(listner);
	}
	
	public void remove(Object listner) {
		for (int idx = listners.size() - 1; idx >= 0; idx--) {
			TableMouseEventTranslator eventListner = listners.get(idx);
			if (eventListner == listner || eventListner.getMouseListner() == listner) {
				table.removeMouseListener(eventListner);
				listners.remove(idx);
			}
		}
	}
	
	
}
