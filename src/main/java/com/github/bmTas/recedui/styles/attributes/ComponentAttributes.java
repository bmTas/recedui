package com.github.bmTas.recedui.styles.attributes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class ComponentAttributes implements IComponentAttribute {
	private final Color foregroundColor, backgroundColor;
	private final Border border;
	private Font font;
	private Boolean contentAreaFilled;
	private Boolean opaque;
	private Integer width, height, minWidth;
	private Icon icon;

	
	public ComponentAttributes(Color foregroundColor, Color backgroundColor, Border border) {
		super();
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.border = border;
//		this.font = font;
//		this.contentAreaFilled = contentAreaFilled;
	}
	
	public void apply(Component component) {
		if (foregroundColor != null) {
			component.setForeground(foregroundColor);
		}
		if (backgroundColor != null) {
			component.setBackground(backgroundColor);
		}
		if (font != null) {
			component.setFont(font);
		}
		
		if (component instanceof AbstractButton) {
			AbstractButton abstractButton = (AbstractButton) component;
			if (contentAreaFilled != null) {
				abstractButton.setContentAreaFilled(contentAreaFilled);
			}
			if (icon != null) {
				abstractButton.setIcon(icon);
			}
		}
		
		if (component instanceof JComponent) {
			JComponent jComponent = (JComponent) component;
			if (border != null) {
				jComponent.setBorder(border);
			}
			if (opaque != null) {
				jComponent.setOpaque(opaque);
			}
		}
		
		if (width != null || height != null) {
			component.setPreferredSize(calcDimension(component.getPreferredSize(), width, height));
		}

		if (minWidth != null ) {
			component.setMinimumSize(calcDimension(component.getMinimumSize(), minWidth, null));
		}
	
		component.revalidate();
	}
	
	private Dimension calcDimension(Dimension size, Integer width, Integer height) {
		Dimension d = size;
		if (width != null && height != null) {
			d = new Dimension(width, height);
		} else if (width != null) {
			d = new Dimension(width, size.height);
		} else if (height != null) {
			d = new Dimension(size.width, height);
		}
		return d;
	}
	
	public ComponentAttributes setFont(Font font) {
		this.font = font;
		return this;
	}
	
	public ComponentAttributes setContentAreaFilled(Boolean val) {
		this.contentAreaFilled = val;
		return this;
	}
	
	public ComponentAttributes setOpaque(boolean opaque) {
		this.opaque = opaque;
		return this;
	}

	/**
	 * @param icon the icon to set
	 */
	public ComponentAttributes setIcon(Icon icon) {
		this.icon = icon;
		return this;
	}

	public final ComponentAttributes setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public final ComponentAttributes setHeight(Integer height) {
		this.height = height;
		return this;
	}
	

	public final ComponentAttributes setMinWidth(Integer width) {
		this.minWidth = width;
		return this;
	}

}
