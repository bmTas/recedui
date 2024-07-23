package com.github.bmTas.recedui.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import com.github.bmTas.recedui.common.ShapePainters;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class TriangleIcon implements Icon {
	private int direction, width, height;
	private final Color color;
	private final boolean fill;

	public TriangleIcon(int direction, Color color) {
		this(direction, color, true);
	}

	public TriangleIcon(int direction, Color color, boolean fill) {
		this.direction = direction;
		this.height = SwingUtils.values().stdIconHeight();
		this.width = this.height;
		this.color = color;
		this.fill = fill;
	}

	public TriangleIcon(int direction, Color color, boolean fill, int width, int height) {
		this.direction = direction;
		this.height = height;
		this.width = width;
		this.color = color;
		this.fill = fill;
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		//System.out.println(" --> " + x + ", " + y);
		
		if (color != null) {
			g.setColor(color);
		}
		
		if (fill) {
			ShapePainters.paintTriangle(g, direction, x, y, width, height);
		} else {
			ShapePainters.drawTriangle(g, direction, x, y, width, height);
		}
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}
	
//	public static void main(String[] args) {
//		TriangleIcon icon = new TriangleIcon(ShapePainters.UP, Color.BLACK, true, 64, 64);
//		JFrame frame = new JFrame();
//		
//		//tree.path
//		//tree.setCellRenderer(x);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		frame.getContentPane().add(new JLabel(icon));
//		
//		
//		frame.pack();
//		
//		
//		frame.setVisible(true);
//	}

}
