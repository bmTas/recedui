package com.github.bmTas.recedui.styles.attributes;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class LineBorderBuilder implements IBorderBuilder {

	private final Color color;
	private final int thickness;
	
	public LineBorderBuilder(Color color, int thickness) {
		super();
		this.color = color;
		this.thickness = thickness;
	}


	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.styles.attributes.IBorderBuilder#buildBorder()
	 */
	@Override
	public Border buildBorder() {
		return BorderFactory.createLineBorder(color, thickness);
	}

}
