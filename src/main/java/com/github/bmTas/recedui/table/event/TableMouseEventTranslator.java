package com.github.bmTas.recedui.table.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.github.bmTas.recedui.def.table.IUiTableMouseListner;
import com.github.bmTas.recedui.def.table.UiTableMouseEvent;

public class TableMouseEventTranslator implements MouseListener {
	
	public static TableMouseEventTranslator getMouseEventTranslator(IUiTableMouseListner listner) {
		return listner instanceof TableMouseEventTranslator
				? (TableMouseEventTranslator) listner
				: new TableMouseEventTranslator(listner);
	}

	//private final JTable table;
	private final IUiTableMouseListner mouseListner;
	
	public TableMouseEventTranslator() {
		if (this instanceof IUiTableMouseListner) {
			mouseListner = (IUiTableMouseListner) this;
			return;
		}
		throw new RuntimeException("This Constructor can only be used by a class that implements ITableMouseEvent");
	}
	
	public TableMouseEventTranslator(IUiTableMouseListner mouseListner) {
		super();
		this.mouseListner = mouseListner;
	}

	/**
	 * @return the mouseListner
	 */
	public IUiTableMouseListner getMouseListner() {
		return mouseListner;
	}

	@Override
	public final void mouseClicked(MouseEvent e) {
		mouseListner.mouseClicked(createUiMouseEvent(e));
	}

	@Override
	public final void mousePressed(MouseEvent e) {
		mouseListner.mousePressed(createUiMouseEvent(e));
	}

	@Override
	public final void mouseReleased(MouseEvent e) {
		mouseListner.mouseReleased(createUiMouseEvent(e));
	}

	@Override
	public final void mouseEntered(MouseEvent e) {
		mouseListner.mouseEntered(createUiMouseEvent(e));
	}

	@Override
	public final void mouseExited(MouseEvent e) {
		mouseListner.mouseExited(createUiMouseEvent(e));
	}

	private UiTableMouseEvent createUiMouseEvent(MouseEvent e) {
		return new UiTableMouseEvent(e);
	}
}
