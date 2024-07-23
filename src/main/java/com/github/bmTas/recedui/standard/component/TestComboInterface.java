package com.github.bmTas.recedui.standard.component;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class TestComboInterface {
	public final BaseCombo combo;

	public TestComboInterface(BaseCombo combo) {
		super();
		this.combo = combo;
	}

	/**
	 * 
	 * @see com.github.bmTas.recedui.standard.component.BaseCombo#showPopup()
	 */
	public void showPopup() {
		combo.showPopup();
	}
	
	public JPopupMenu getPopup() {
		return combo.getPopup();
	}


	public List<JComponent> getAditionalButtons() {
		return combo.getAditionalButtons();
	}

}
