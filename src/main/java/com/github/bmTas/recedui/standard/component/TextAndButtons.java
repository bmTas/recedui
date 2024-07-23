package com.github.bmTas.recedui.standard.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.github.bmTas.recedui.common.MultiComponentBuilder;
import com.github.bmTas.recedui.def.component.IUiComponent;

public class TextAndButtons {
	
	private static final int FIELD_WIDTH = 20;


	protected final JTextComponent valueTxt;
	
	private JPanel panel;
	ArrayList<JComponent> buttons = new ArrayList<>();

	public TextAndButtons(JTextComponent txtComponent) {
		valueTxt = txtComponent == null ? new JTextField() : txtComponent;
	}

	/**
	 * @param panel the panel to set
	 */
	public TextAndButtons setPanel(JPanel panel) {
		this.panel = panel;
		return this;
	}

	public TextAndButtons setButtons(JComponent...components) {
		if (components != null) {	
			for (JComponent c : components) {
				buttons.add(c);
			}
		}
		return this;
	}

	public TextAndButtons addUiButton(IUiComponent component) {
		buttons.add(component.getMainComponent());
		return this;
	}
	

	public TextAndButtons addButton(JComponent component) {
		buttons.add(component);
		return this;
	}

	
	public void layout() {
		panel.add(BorderLayout.CENTER, valueTxt);
		
		valueTxt.setOpaque(true);
		valueTxt.setMinimumSize(new Dimension(FIELD_WIDTH, valueTxt.getHeight()));
		
		MultiComponentBuilder componentBldr = new MultiComponentBuilder(panel, valueTxt);
				
		for (JComponent btn : buttons) {
			componentBldr.add(btn);
		}
		
		componentBldr.build();

//		if (! Common.NIMBUS_LAF) {
//			this.setBorder(valueTxt.getBorder());
//		}
		valueTxt.setBorder(BorderFactory.createEmptyBorder());
	}
	
	
	public void setEditable(boolean editable) {
		valueTxt.setEditable(editable);
		
		if (buttons != null) {
			for (JComponent b : buttons) {
				b.setEnabled(editable);
			}
		}
	}
	

	/**
	 * @param name
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	public void setName(String name) {
		valueTxt.setName(name + "_Txt");

		if (buttons != null) {
			int i = 0;
			String btnName = name + "_Btn";
			for (JComponent b : buttons) {
				b.setName(btnName + i++);
			}
		}
	}


}
