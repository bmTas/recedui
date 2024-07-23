package com.github.bmTas.recedui.form;

import info.clearthought.layout.TableLayout;

public class VerticalPosition {
	public static final VerticalPosition TOP = new VerticalPosition(TableLayout.TOP);
	public static final VerticalPosition CENTER = new VerticalPosition(TableLayout.CENTER);
	public static final VerticalPosition FULL = new VerticalPosition(TableLayout.FULL);
	public static final VerticalPosition BOTTOM = new VerticalPosition(TableLayout.BOTTOM);
	 
	final int cellPosition;

	VerticalPosition(int position) {
		this.cellPosition = position;
	}

}
