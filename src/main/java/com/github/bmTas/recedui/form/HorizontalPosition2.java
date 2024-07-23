package com.github.bmTas.recedui.form;

import info.clearthought.layout.TableLayout;

public enum HorizontalPosition2 {
	LEFT(TableLayout.LEFT),
	CENTER(TableLayout.CENTER), FULL(TableLayout.FULL),
	RIGHT(TableLayout.RIGHT),

	/**
	 * Indicates that the component is leading justified in its cell. Leading
	 * justification means components are left justified if their container is
	 * left-oriented and right justified if their container is right-oriented.
	 * Trailing justification is opposite. see
	 * java.awt.Component#getComponentOrientation
	 */
	LEADING(TableLayout.LEADING),

	/**
	 * Indicates that the component is trailing justified in its cell. Trailing
	 * justification means components are right justified if their container is
	 * left-oriented and left justified if their container is right-oriented.
	 * Leading justification is opposite. see
	 * java.awt.Component#getComponentOrientation
	 */
	TRAILING(TableLayout.TRAILING);
	
	final int cellPosition;

	HorizontalPosition2(int position) {
		this.cellPosition = position;
	}

}
