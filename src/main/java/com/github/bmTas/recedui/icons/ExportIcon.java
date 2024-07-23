package com.github.bmTas.recedui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ExportIcon implements Icon {

	private final Icon baseIcon;
	private final Color color;
	private final int pos;

	public ExportIcon(Icon baseIcon, boolean arrowAtTop, Color color) {
		this.baseIcon = baseIcon;
		this.color = color;
		int iconHeight = baseIcon.getIconHeight();
		this.pos = arrowAtTop ? iconHeight / 5 : iconHeight* 3 / 4;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		baseIcon.paintIcon(c, g, x, y);
		
		int height = baseIcon.getIconHeight();
		int width = baseIcon.getIconWidth();
		int w = height / 16;
		
		g.setColor(color);
		int lineY = y + pos + (w - 1) / 2;
		int endX = x + width;
		int startX = x + width * 3 / 8;
		
//		if (w == 1) {
//			g.drawLine(startX, lineY, endX - w + 1, lineY);			
//		} else {
		for (int i = 0; i < w; i++) {
			g.drawLine(startX, lineY+i, endX - w + 1, lineY+i);	
		}
//		}
		if ( w % 2 == 1) {
			for (int i = w*2; i >= 0; i--) {
				g.drawLine(endX - i, lineY + w / 2 - i, endX - i, lineY + w / 2 + i);
			}
		} else {
			for (int i = w*2; i >= 0; i--) {
				g.drawLine(endX - i, lineY - i, endX - i, lineY + i + 1);
			}
		}
	}

	@Override
	public int getIconWidth() {
		return baseIcon.getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return baseIcon.getIconHeight();
	}

}
