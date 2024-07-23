package com.github.bmTas.recedui.icons;

import javax.swing.Icon;

/**
 * 
 * 
 * 
 * @author Bruce Martin
 *
 */
public abstract class AbstractIcon implements Icon {
	protected final int width, height;


	public AbstractIcon(int width, int height) {
		this.height = height;
		this.width = width;
	}


	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}
}
