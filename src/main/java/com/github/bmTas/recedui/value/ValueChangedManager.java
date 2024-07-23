package com.github.bmTas.recedui.value;

import java.util.ArrayList;

public class ValueChangedManager {

	private ArrayList<IValueChangedListner> listners = new ArrayList<>(1);
	
	public void addListner(IValueChangedListner listner) {
		listners.add(listner);
	}

	public void removeListner(Object o) {
		listners.remove(o);
	}
	
	public void fireDataChanged() {
		for (IValueChangedListner l : listners) {
			l.valueChanged();
		}
	}
}
