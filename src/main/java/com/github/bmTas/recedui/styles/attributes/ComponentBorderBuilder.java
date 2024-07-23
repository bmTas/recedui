package com.github.bmTas.recedui.styles.attributes;

import javax.swing.JComponent;
import javax.swing.border.Border;

public class ComponentBorderBuilder implements IBorderBuilder {

	final Border border;
	
	public ComponentBorderBuilder(Border border) {
		this.border = border;
	}
	public ComponentBorderBuilder(JComponent borderSource) {
		this.border = borderSource.getBorder();
	}


	@Override
	public Border buildBorder() {
		return border;
	}

}
