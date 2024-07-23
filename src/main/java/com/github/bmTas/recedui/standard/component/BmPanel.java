package com.github.bmTas.recedui.standard.component;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.github.bmTas.recedui.def.component.IUiPanel;
import com.github.bmTas.recedui.styles.IStyle;

@SuppressWarnings("serial")
public class BmPanel extends BasicPanel implements IUiPanel {


	public BmPanel(IStyle style, LayoutManager layout) {
		super(style, layout);

		style.register(this);
	}


	@Override
	public final JPanel getMainComponent() {
		return this;
	}
}
