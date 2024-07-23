package com.github.bmTas.recedui.form;

import info.clearthought.layout.TableLayout;

public enum VerticalPosition2 {
	 TOP(TableLayout.TOP),
	 CENTER(TableLayout.CENTER),
	 FULL(TableLayout.FULL),
	 BOTTOM(TableLayout.BOTTOM),
	 ;
	final int cellPosition;

	VerticalPosition2(int position) {
		this.cellPosition = position;
	}

}
