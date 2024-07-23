package com.github.bmTas.recedui.combo;

import javax.swing.JPopupMenu;

import com.github.bmTas.recedui.standard.component.BaseCombo;
import com.github.bmTas.recedui.value.IUiValueComponent;
import com.github.bmTas.recedui.value.IValueChangedListner;
import com.github.bmTas.recedui.value.ValueChangedManager;
import com.github.bmTas.recedui.value.ValueRendor;

@SuppressWarnings("serial")
public class UiContainerCombo<Value> extends BaseCombo implements IUiValueComponent<Value> {

	public static <Value> ValueRendor<Value> newTableRendor(IContainerComboDetails<Value> details) {
		return new ValueRendor<Value>(new UiContainerCombo<Value>(details));
	}

	private final IContainerComboDetails<Value> details;
	private final ValueChangedManager changeManager = new ValueChangedManager();

	public UiContainerCombo(IContainerComboDetails<Value> details) {
		this.details = details;
		JPopupMenu popup = new JPopupMenu();
		
		popup.add(details.getUiContainer().getGuiContainer());
	}

	/**
	 * @return
	 * @see com.github.bmTas.recedui.combo.IContainerComboDetails#getValue()
	 */
	public Value getValue() {
		return details.getValue();
	}

	/**
	 * @param value
	 * @see com.github.bmTas.recedui.combo.IContainerComboDetails#setValue(java.lang.Object)
	 */
	public void setValue(Value value) {
		details.setValue(value);
		setText(details.getText());
		changeManager.fireDataChanged();
	}

	protected int getPopupWidth(JPopupMenu currentPopup) {
		return Math.max(this.getWidth(), currentPopup.getPreferredSize().width);
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
