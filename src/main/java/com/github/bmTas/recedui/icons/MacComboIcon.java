package com.github.bmTas.recedui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import com.github.bmTas.recedui.common.ShapePainters;

/**
 * 
 * 
 * 
 * @author Bruce Martin
 *
 */
public class MacComboIcon extends AbstractIcon {
	
	private final Color backgroundColor;
	
	public MacComboIcon(int width, int height) {
		this(width, height, new Color(116, 148, 255)); // light blue
	}

	public MacComboIcon(int width, int height, Color backgroundColor) {
		super(width, height);
		this.backgroundColor = backgroundColor;
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {	
		Color foreground=g.getColor();

		g.setColor(backgroundColor);
		g.fillRoundRect(x, y, width, height, 5, 5);
		g.setColor(foreground);

		ShapePainters.paintMacCombo(g, x, y, width, height);
	}
}
