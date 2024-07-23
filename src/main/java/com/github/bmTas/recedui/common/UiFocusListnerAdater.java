package com.github.bmTas.recedui.common;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import com.github.bmTas.recedui.def.component.IFocusListner;
import com.github.bmTas.recedui.def.component.IGetContainer;

public class UiFocusListnerAdater implements FocusListener {
	
	private final IGetContainer component;
	private ArrayList<IFocusListner> focusListners;
	
	
	public UiFocusListnerAdater(IGetContainer component) {
		super();
		this.component = component;
	}


	public void addListner(IFocusListner listner) {
		if (focusListners == null) {
			synchronized (this) {
				if (focusListners == null) {
					focusListners = new ArrayList<>();
					component.getGuiContainer().addFocusListener(this);
				}
			}
		}
		focusListners.add(listner);
	}


	@Override
	public void focusGained(FocusEvent e) {
		UiFocusEvent event = new UiFocusEvent(component, e);
		for (int i = focusListners.size() - 1; i >= 0; i--) {
			focusListners.get(i).focusGained(event);
		}
	}


	@Override
	public void focusLost(FocusEvent e) {
		UiFocusEvent event = new UiFocusEvent(component, e);
		for (int i = focusListners.size() - 1; i >= 0; i--) {
			focusListners.get(i).focusLost(event);
		}
	}
	
	
}
