package com.github.bmTas.recedui.radioBtn;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;

import com.github.bmTas.recedui.def.radioButton.IRadioBtnDetails;

public class BasicRadioBtnDtls  implements IRadioBtnDetails {
	private final JRadioButton btn;
	
	/**
	 * Action to be performed 
	 * @param a
	 */
	public BasicRadioBtnDtls(Action a){
		btn = new JRadioButton(a);
	}
	
	public BasicRadioBtnDtls(JRadioButton b){
		btn = b;
	}

	
	public BasicRadioBtnDtls(String text, Icon icon){
		JRadioButton b = null;
		
		if (text != null && icon != null) {
			b = new JRadioButton(text, icon);
		} else if (text != null) {
			b = new JRadioButton(text);
		} else if (icon != null) {
			b = new JRadioButton(icon);
		} else {
			throw new RuntimeException("You must supply either a Text string or and icon or both");
		}
		
		btn = b;
	}

	@Override
	public JRadioButton getButton() {
		return btn;
	}

	/**
	 * @return is button is selected
	 * @see javax.swing.AbstractButton#isSelected()
	 */
	public boolean isSelected() {
		return btn.isSelected();
	}

	public void setSelected(boolean b) {
		btn.setSelected(b);
	}
	
	
}
