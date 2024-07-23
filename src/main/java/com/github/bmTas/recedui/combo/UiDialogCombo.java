package com.github.bmTas.recedui.combo;

import javax.swing.JButton;

import com.github.bmTas.recedui.standard.component.BaseCombo;
import com.github.bmTas.recedui.value.IUiValueComponent;
import com.github.bmTas.recedui.value.IValueChangedListner;
import com.github.bmTas.recedui.value.ValueChangedManager;
import com.github.bmTas.recedui.value.ValueRendor;

@SuppressWarnings("serial")
public class UiDialogCombo<Value>  extends BaseCombo implements IUiValueComponent<Value> {


	public static <Value> ValueRendor<Value> newTableRendor(IContainerComboDetails<Value> details) {
		return new ValueRendor<Value>(new UiDialogCombo<Value>(details));
	}

	private final IContainerComboDetails<Value> details;
	private final ValueChangedManager changeManager = new ValueChangedManager();

	public UiDialogCombo(IContainerComboDetails<Value> details) {	this(details, ensureButtonExists(details.getButton())); 	}
	
	private UiDialogCombo(IContainerComboDetails<Value> details, JButton b) {
		super("DialogComboFld", false, b);
		this.details = details;

		super.setText(details.getText());
		b.setAction(new DialogAction<Value>(this, details, b.getText(), b.getIcon()));
	}


	/**
	 * @return
	 * @see com.github.bmTas.recedui.combo.IContainerComboDetails#getValue()
	 */
	public Value getValue() {
		setText(details.getText());
		return details.getValue();
	}

	/**
	 * @param value
	 * @see com.github.bmTas.recedui.combo.IContainerComboDetails#setValue(java.lang.Object)
	 */
	public void setValue(Value value) {
		details.setValue(value);
		setText(details.getText());
	}

	@Override
	public void addListner(IValueChangedListner listner) {
		changeManager.addListner(listner);
	}

	public void removeListner(Object o) {
		changeManager.removeListner(o);
	}

	@Override
	public void fireDataChanged() {
		changeManager.fireDataChanged();
	}

}