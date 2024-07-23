package com.github.bmTas.recedui.form;

import info.clearthought.layout.TableLayout;

public class HorizontalPosition {
	public static final HorizontalPosition LEFT = new HorizontalPosition(TableLayout.LEFT);
	public static final HorizontalPosition CENTER = new HorizontalPosition(TableLayout.CENTER);
	public static final HorizontalPosition FULL = new HorizontalPosition(TableLayout.FULL);
	public static final HorizontalPosition RIGHT = new HorizontalPosition(TableLayout.RIGHT);

	/**
	 * Indicates that the component is leading justified in its cell. Leading
	 * justification means components are left justified if their container is
	 * left-oriented and right justified if their container is right-oriented.
	 * Trailing justification is opposite. see
	 * java.awt.Component#getComponentOrientation
	 */
	public static final HorizontalPosition LEADING = new HorizontalPosition(TableLayout.LEADING);

	/**
	 * Indicates that the component is trailing justified in its cell. Trailing
	 * justification means components are right justified if their container is
	 * left-oriented and left justified if their container is right-oriented.
	 * Leading justification is opposite. see
	 * java.awt.Component#getComponentOrientation
	 */
	public static final HorizontalPosition TRAILING = new HorizontalPosition(TableLayout.TRAILING);
	
	final int cellPosition;

	HorizontalPosition(int position) {
		this.cellPosition = position;
	}

}
