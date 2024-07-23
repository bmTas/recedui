package com.github.bmTas.recedui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;

import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class LetterIconHolder implements IIconHolder {

	public static enum LetterPosition {
			TOP,
			MIDDLE,
			BOTTOM
	};
	
	private LetterPosition letterPosition = LetterPosition.TOP;
	double ratio = -1;
	private final char ch;
	private final Color color, backGroundColour;

	public LetterIconHolder(char ch, Color color) {
		this.ch = ch;
		this.color = color;
		this.backGroundColour = null;
	}

	public LetterIconHolder(LetterPosition letterPosition, double ratio, char ch, Color color, Color backGroundColour) {
		super();
		this.letterPosition = letterPosition;
		this.ratio = ratio;
		this.ch = ch;
		this.color = color;
		this.backGroundColour = backGroundColour;
	}

	@Override
	public Icon icon() {
		int stdIconHeight = SwingUtils.values().stdIconHeight();
		return new LetterIcon(stdIconHeight, calcLetterHeight( stdIconHeight), letterPosition, ch, color, backGroundColour);
	}

	@Override
	public Icon largeIcon() {
		int icon = SwingUtils.values().largeIconHeight();
		return new LetterIcon(icon, calcLetterHeight(icon), letterPosition, ch, color, backGroundColour);
	}

	private int calcLetterHeight(int height) {
		if (ratio <= 0) {
			return - 1;
		}
		return (int) (ratio * height + 0.5);
	}
	@Override
	public Icon toolbarIcon() {
		int icon = SwingUtils.values().toolbarIconHeight();
		return new LetterIcon(icon, calcLetterHeight(icon), letterPosition, ch, color, backGroundColour);
	}

	public static class LetterIcon implements Icon {

		private final int size, letterSize;
		private final LetterPosition letterPosition;
		private final char ch;
		private final Color color, backGroundColour;
		
		public LetterIcon(int size, int letterSize, LetterPosition letterPosition, char ch,
				Color color, Color backGroundColour) {
			this.size = size;
			this.letterSize = letterSize ;
			this.letterPosition = letterPosition == null ? LetterPosition.TOP : letterPosition;
			this.ch = ch;
			this.color = color;
			this.backGroundColour = backGroundColour;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			int adj = size / 16;
			char[] data = {ch};
			Font f = g.getFont();
			int fontSize = size;
			int posX = (size - fontSize/2 + 1)/2;
			int posY = 1;
			if (letterSize >  0) {
				fontSize = letterSize;
				switch (letterPosition) {
				case BOTTOM : posY = 2;							break;
				case MIDDLE : posY = (size - letterSize) / 2;	break;
				case TOP	: posY = (size - letterSize);		break;
				}
			}
			
			if (backGroundColour != null) {
				g.setColor(backGroundColour);
				g.fill3DRect(x+1, y+1, size-2, size-2, true);
			}
			
			if (f.getSize() != fontSize) {
				g.setFont(new Font(f.getName(), f.getStyle(), fontSize));
			}
			g.setColor(color);
			g.drawChars(data, 0, 1, x + adj + posX, y + size - posY);
		}

		@Override
		public int getIconWidth() {
			return size;
		}

		@Override
		public int getIconHeight() {
			return size;
		}

	}
}
