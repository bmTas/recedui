package com.github.bmTas.recedui.common;

import java.awt.event.FocusEvent;

import com.github.bmTas.recedui.def.component.IFocusEvent;
import com.github.bmTas.recedui.def.component.IGetContainer;

public class UiFocusEvent implements IFocusEvent {
	private final IGetContainer container;
	private final FocusEvent focusEvent;
	
	public UiFocusEvent(IGetContainer container, FocusEvent focusEvent) {
		super();
		this.container = container;
		this.focusEvent = focusEvent;
	}

	@Override
	public IGetContainer getContainer() {
		return container;
	}

	@Override
	public FocusEvent getFocusEvent() {
		return focusEvent;
	}

}
