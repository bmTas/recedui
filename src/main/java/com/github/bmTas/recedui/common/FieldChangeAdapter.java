package com.github.bmTas.recedui.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.github.bmTas.recedui.def.common.IFieldChangedListner;
import com.github.bmTas.recedui.def.component.IUiComponent;

/**
 * 
 * @author Bruce Martin
 *
 */
public abstract class FieldChangeAdapter<UiElement extends IUiComponent, Value> implements FocusListener, ActionListener {
	
	private final UiElement uiElement;
	private ArrayList<IFieldChangedListner<UiElement,Value>> changeListners = new ArrayList<>();
	private Value oldValue;
	private boolean initOldValue = true;
	
	public FieldChangeAdapter(UiElement component) {
		super();
		this.uiElement = component;
	}


	public void addListner(IFieldChangedListner<UiElement,Value> listner) {
		if (initOldValue) {
			oldValue = getValue();
			initOldValue = false;
			UiElement uiItem = uiElement;
			addListnerToComponent(uiItem);
		}
		changeListners.add(listner);
	}
	
	public void removeListner(Object listner) {
		changeListners.remove(listner);
	}


	protected void addListnerToComponent(UiElement uiItem) {
		JComponent component = uiItem.getMainComponent();
		component.addFocusListener(this);
		if (component instanceof JComboBox) {
			((JComboBox<?>) component).addActionListener(this);
		}
	}


	@Override
	public void focusGained(FocusEvent e) {
	}


	@Override
	public void focusLost(FocusEvent e) {
		checkIfValueChanged();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		checkIfValueChanged();
	}


	private void checkIfValueChanged() {
		Value newValue = getValue();
		if (newValue == null) {
			if (oldValue == null) {
				return;
			}
		} else if (newValue.equals(oldValue)) {
			oldValue = newValue;
			return;
		}
		fireValueChanged(newValue);
	}
	
	
	protected void fireValueChanged(Value newValue) {

		Value old = oldValue;
		oldValue = newValue;
		if (changeListners != null) {
			for (IFieldChangedListner<UiElement,Value> listner : changeListners) {
				listner.valueChanged(uiElement, old, newValue);
			}
		}
	}

	
	
	protected abstract Value getValue();
}
