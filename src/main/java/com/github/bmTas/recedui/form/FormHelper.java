package com.github.bmTas.recedui.form;

import com.github.bmTas.recedui.xEnvironment.SwingUtils;

import info.clearthought.layout.TableLayout;

public class FormHelper {
	public static final double NORMAL_HEIGHT = SwingUtils.values().NORMAL_FIELD_HEIGHT;
	public static final double BORDER = 10;
	  
    /** Indicates that the row/column should fill the available space */
    public static final double FILL = TableLayout.FILL;

    /**
     * Indicates that the row/column should be allocated just enough space to
     * accomidate the preferred size of all components contained completely
     * within this row/column.
     */
    public static final double PREFERRED = TableLayout.PREFERRED;

    /**
     * Indicates that the row/column should be allocated just enough space to
     * accomidate the minimum size of all components contained completely within
     * this row/column.
     */
    public static final double MINIMUM = TableLayout.MINIMUM;

	public static final double GAP  ;
	public static final double GAP0 ;
	public static final double GAP1 ;
	public static final double GAP2 ;
	public static final double GAP3 ;
	public static final double GAP4 ;
	public static final double GAP5 ;
	public static final double CHAR_FIELD_HEIGHT;              
	
	static {
		SwingUtils swingValues = SwingUtils.values();
		
		GAP0   = swingValues.STANDARD_FONT_HEIGHT;
		GAP    = GAP0 * 5 / 12;
		GAP1   = GAP0 * 5 / 3;
		GAP2   = GAP0 * 5 / 2;
		GAP3   = GAP1 * 2;
		GAP4   = GAP1 * 3;
		GAP5   = GAP1 * 4;
		CHAR_FIELD_HEIGHT = swingValues.CHAR_FIELD_HEIGHT;         

	}

//	public enum CellSizing {
//	    	/** Indicates that the row/column should fill the available space */
//		FILL(TableLayout.FILL),
//		    /**
//		     * Indicates that the row/column should be allocated just enough space to
//		     * accomidate the preferred size of all components contained completely
//		     * within this row/column.
//		     */
//		PREFERRED(TableLayout.PREFERRED),
//		    /**
//		     * Indicates that the row/column should be allocated just enough space to
//		     * accomidate the minimum size of all components contained completely within
//		     * this row/column.
//		     */
//		MINIMUM(TableLayout.MINIMUM),
//		
//		;
//		final double sizeId;
//		
//		CellSizing(double sizeId) {
//			this.sizeId = sizeId;
//		}
//
//	}
	
	public static double[] formFieldWidths(double... cols) {
		int size = cols.length * 2 + 1;
		double[] fields = new double[size];
		
		for (int i = 0; i < cols.length; i++) {
			fields[i * 2] = 5;
			fields[i * 2 + 1] = cols[i];
		}
		
		fields[0] = BORDER;
		fields[size-1] = BORDER;
		return fields;
	}
}
