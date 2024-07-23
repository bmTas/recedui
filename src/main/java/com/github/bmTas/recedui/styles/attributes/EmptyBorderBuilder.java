package com.github.bmTas.recedui.styles.attributes;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class EmptyBorderBuilder implements IBorderBuilder {
	
	public final static EmptyBorderBuilder EMPTY_BORDER_BUILDER = new EmptyBorderBuilder();
	
	private final int top,  left, bottom,  right;
	private final boolean noBorder;
    
	public EmptyBorderBuilder() {
		this(0, 0, 0, 0);
	}
    public EmptyBorderBuilder(int top, int left, int bottom, int right) {
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		
		noBorder = top == 0 && bottom <= 0 && left <= 0 && right <= 0;
	}
    
	@Override
	public Border buildBorder() {
		// TODO Auto-generated method stub
		if (noBorder) {
			return BorderFactory.createEmptyBorder();
		}
		return BorderFactory.createEmptyBorder(top,  left, bottom,  right);
	}

}
