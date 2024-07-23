package com.github.bmTas.recedui.buttons;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.component.IUiButton;
import com.github.bmTas.recedui.styles.IStyle;

public class UiButton extends UiStdComponent<JButton> implements IUiButton {

	public UiButton(IStyle style, Action a) { super(style, new JButton(a)); }

	public UiButton(IStyle style) { super(style, new JButton()); }

	public UiButton(IStyle style, Icon icon) { super(style, new JButton(icon)); }

	public UiButton(IStyle style, String text) { super(style, new JButton(text)); }	

	/**
	 * 
	 * @see javax.swing.AbstractButton#doClick()
	 */
	public void doClick() {
		super.component.doClick();
	}

	/**
	 * @param l
	 * @see javax.swing.AbstractButton#addActionListener(java.awt.event.ActionListener)
	 */
	public void addActionListener(ActionListener l) {
		super.component.addActionListener(l);
	}


	/**
	 * @param enableComponent wether the component is enabled
	 * @see javax.swing.AbstractButton#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enableComponent) {
		super.component.setEnabled(enableComponent);
	}

}
