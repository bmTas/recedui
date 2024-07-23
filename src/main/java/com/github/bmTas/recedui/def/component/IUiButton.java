package com.github.bmTas.recedui.def.component;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

public interface IUiButton extends IUiComponent {
	public JComponent getGuiContainer();
	public JButton getMainComponent();

	public void addActionListener(ActionListener listner);

	/**
	 * @param enableComponent wether the component is enabled
	 * @see javax.swing.AbstractButton#setEnabled(boolean)
	 */
	void setEnabled(boolean enableComponent);
}
