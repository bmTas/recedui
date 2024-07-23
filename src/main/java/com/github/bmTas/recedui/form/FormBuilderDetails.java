package com.github.bmTas.recedui.form;

import info.clearthought.layout.TableLayout;

public class FormBuilderDetails {
	
	private static final double BORDER = 10;
	private static final double[] VERTICAL_GAPS_ONE_COLUMN 
					= {BORDER, FormHelper.FILL, BORDER};
	private static final double[] VERTICAL_GAPS_TWO_COLS 
		= {BORDER, FormHelper.PREFERRED, 5, FormHelper.FILL, BORDER};
	private static final double[] HORIZONTAL_GAPS_DEFAULT = {
					BORDER, 
					FormHelper.PREFERRED, 
					FormHelper.GAP, 5};

	final int labelColumn, fieldColumnCount, lastFieldColumn;
	
	final String formName;
	final double[] rowGaps; 
	
	
	private int rowNum=-2;
	
	private double[] lSize = new double[40];
	
	private final TableLayout tblLayout = new TableLayout();



	public FormBuilderDetails(String formName, 
			double[] rowGaps, double[] gapsForColumns, 
//			CellPosition labelPosition, CellPosition defaultFieldPosition,
			boolean labelColumn,
			int fieldColCount) {
		
		double[] columnGaps;

		int labelColumnCount = 0;
		if (labelColumn) {
			columnGaps = gapsForColumns == null ? VERTICAL_GAPS_TWO_COLS : gapsForColumns;
			labelColumnCount = 1;
		} else {
			columnGaps = gapsForColumns == null ? VERTICAL_GAPS_ONE_COLUMN : gapsForColumns;
		}
		rowGaps = rowGaps == null ? HORIZONTAL_GAPS_DEFAULT : rowGaps;
		
		this.fieldColumnCount =  (fieldColCount) * 2 - 1;
		this.lastFieldColumn = 2 + this.fieldColumnCount;
		int cols = 2 + 2*labelColumnCount + this.fieldColumnCount;
		if (columnGaps.length < cols) {
			double[] tmp = new double[cols];
			System.arraycopy(columnGaps, 0, tmp, 0, columnGaps.length - 1);
			for (int i = 0; i < fieldColCount-1; i++) {
				tmp[columnGaps.length + i * 2 - 1 ] = columnGaps[2];
				tmp[columnGaps.length + i * 2  ] = FormHelper.PREFERRED;
			}
			tmp[tmp.length - 1] = columnGaps[columnGaps.length - 1];
			columnGaps = tmp;
		}
		
		this.formName = formName + ".";
		this.rowGaps = rowGaps;
		this.labelColumn = columnGaps[0] == 0 ? 0 : 1;
		
		if (rowGaps[0] > 0) {
			lSize[0] = rowGaps[0];
			rowNum+=1;
		}
		
		
		tblLayout.setColumn(columnGaps);

	}
	
//	double[] getColumnGaps() {
//		return this.columnGaps;
//	}
	
	/**
	 * @return the rowNum
	 */
	public int getRowNumber() {
		return rowNum;
	}

	/**
	 * @return the tblLayout
	 */
	public java.awt.LayoutManager2 getLayout() {
		return tblLayout;
	}

	/**
	 * Adjust array for a Toolbar
	 * @param height height of the Toolbar
	 */
	public final void incForToolbar(double height) {
		if (rowNum >= 0) {
			throw new RuntimeException("The Toolbar must be added before any lines/cells are defined");
		}
		
		rowNum = -1;
		lSize[0]     = height;
		if (rowGaps[0] > 0) {
			lSize[1] = rowGaps[0];
			rowNum = 0;
		}

	}
	
	/**
	 * Increment the row counter and set the Height and Gap amounts to the
	 * supplied values
	 *
	 * @param height Height of the Field
	 * @param gap    Gap that will follow the field
	 */
	public final void incRow(int rows, double height, double gap) {
		rowNum += 2 * rows;
		
		checkHeightArraySize();

		lSize[rowNum]     = height;
		lSize[rowNum + 1] = gap;
	}

	private void checkHeightArraySize() {
		if (rowNum+1 >= lSize.length) {
			double[] tmp = new double[rowNum + 41];
			System.arraycopy(lSize, 0, tmp, 0, lSize.length);
			lSize = tmp;
		}
	}
	
	public void setRowHeight(double height) {
		lSize[rowNum]     = height;
	}

	public final void setGap(double gap) {
		lSize[rowNum + 1] = gap;
	}

	public final void applyRowDetailsToPanelLayout() {
		double[] hGap = new double[rowNum + 2];
		
		System.arraycopy(lSize, 0, hGap, 0, rowNum + 2);
		tblLayout.setRow(hGap);
	}
}
