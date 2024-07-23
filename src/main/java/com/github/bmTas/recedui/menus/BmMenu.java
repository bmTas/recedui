package com.github.bmTas.recedui.menus;


import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;

import com.github.bmTas.recedui.def.menu.IUiMenu;
import com.github.bmTas.recedui.styles.IStyle;

@SuppressWarnings("serial")
public class BmMenu extends JMenu implements IUiMenu {

	private final IStyle style;
	
	private JLabel fieldsLabel;

	public BmMenu(IStyle style, String label) {
		super(label);
		
		this.style = style;
	}

	@Override
	public IStyle getStyleId() {
		return style;
	}

	@Override
	public JComponent getGuiContainer() {
		return this;
	}

	@Override
	public JMenu getMainComponent() {
		return this;
	}

	@Override
	public JLabel getFieldsLabel() {
		return fieldsLabel;
	}

	@Override
	public void setFieldsLabel(JLabel label) {
		this.fieldsLabel = label;
	}

}
