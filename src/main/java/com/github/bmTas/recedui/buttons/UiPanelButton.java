package com.github.bmTas.recedui.buttons;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.component.IUiButton;
import com.github.bmTas.recedui.styles.IStyle;

public class UiPanelButton extends UiStdComponent2<JPanel, JButton> implements IUiButton {

	public UiPanelButton(IStyle style) { this(style, new JButton()); }

	public UiPanelButton(IStyle style, Action a) { this(style, new JButton(a)); }

	public UiPanelButton(IStyle style, Icon a) { this(style, new JButton(a)); }

	public UiPanelButton(IStyle style, String a) { this(style, new JButton(a)); }	
	
	public UiPanelButton(IStyle style, JButton btn) {
		super(style, createPanel(btn), btn); 
	}	
	
	private static JPanel createPanel(JButton btn) {
		JPanel jPanel = new JPanel(new BorderLayout(0, 0));
		jPanel.add(btn, BorderLayout.CENTER);
		jPanel.setBorder(BorderFactory.createEmptyBorder());
		return jPanel;
	}
	

	/**
	 * 
	 * @see javax.swing.AbstractButton#doClick()
	 */
	public void doClick() {
		super.component.doClick();
	}

	/**
	 * @param listner
	 * @see javax.swing.AbstractButton#addActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addActionListener(ActionListener listner) {
		super.component.addActionListener(listner);
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
