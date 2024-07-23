package com.github.bmTas.recedui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import com.github.bmTas.recedui.common.ShapePainters;

/**
 * Create a standard Combo
 * 
 * 
 * @author Bruce Martin
 *
 */
public class StdComboIcon extends AbstractIcon {
	
	private final Color backgroundColor;
	
	public StdComboIcon(int width, int height) {
		this(width, height, null); // light blue
	}

	public StdComboIcon(int width, int height, Color backgroundColor) {
		super(width, height);
		this.backgroundColor = backgroundColor;
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {	

		if(backgroundColor != null) {
			Color foreground=g.getColor();

			g.setColor(backgroundColor);
			g.fillRect(x, y, width, height);
			g.setColor(foreground);
		}

    	ShapePainters.paintTriangle(g, ShapePainters.DOWN, x, y, width, height);
	}
}
