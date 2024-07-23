package com.github.bmTas.recedui.icons;

import java.awt.Component;
import java.awt.Graphics;

/**
 * 
 * 
 * 
 * @author Bruce Martin
 *
 */
public class BlankIcon extends AbstractIcon {
	public BlankIcon(int width, int height) {
		super(width, height);
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {	}
}
