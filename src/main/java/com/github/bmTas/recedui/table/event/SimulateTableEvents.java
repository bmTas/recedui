package com.github.bmTas.recedui.table.event;

import com.github.bmTas.recedui.common.event.CreateEvent;
import com.github.bmTas.recedui.def.table.UiTableMouseEvent;

public class SimulateTableEvents {
	private final TableMouseListnerManager listnerManager;

	public SimulateTableEvents(IGetListnerManager getListnerManager) {
		this(getListnerManager.getMouserListnerManager());
	}
	
	public SimulateTableEvents(TableMouseListnerManager listnerManager) {
		super();
		this.listnerManager = listnerManager;
	}
	
	public void mouseClicked(int row, int column) {
		UiTableMouseEvent tableMouseEvent = new UiTableMouseEvent(
				CreateEvent.CreateMouseEvent(listnerManager.getTable(), 1), 
				row, 
				column);
		
		for (TableMouseEventTranslator listner : listnerManager.listners) {
			listner.getMouseListner().mouseClicked(tableMouseEvent);
		}
	}
	
	
	public void mousePressed(int row, int column) {
		UiTableMouseEvent tableMouseEvent = new UiTableMouseEvent(
				CreateEvent.CreateMouseEvent(listnerManager.getTable(), 1), 
				row, 
				column);
		
		for (TableMouseEventTranslator listner : listnerManager.listners) {
			listner.getMouseListner().mousePressed(tableMouseEvent);
		}
	}

}
