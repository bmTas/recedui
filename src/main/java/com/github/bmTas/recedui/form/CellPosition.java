package com.github.bmTas.recedui.form;

/**
 * Cell positioning values
 * @author Bruce Martin
 *
 */
public class CellPosition {
	
	public static final CellPosition LEFT		= new CellPosition(HorizontalPosition.LEFT,  VerticalPosition.CENTER);
	public static final CellPosition LEFT_1_COL	= new CellPosition(HorizontalPosition.LEFT,  VerticalPosition.CENTER, 1);
	public static final CellPosition LEFT_2_COL	= new CellPosition(HorizontalPosition.LEFT,  VerticalPosition.CENTER, 3);
	public static final CellPosition RIGHT		= new CellPosition(HorizontalPosition.RIGHT, VerticalPosition.CENTER);
	public static final CellPosition CENTER		= new CellPosition(HorizontalPosition.CENTER, VerticalPosition.CENTER);

	public static final CellPosition FULL_WIDTH
			= new CellPosition(HorizontalPosition.FULL, VerticalPosition.CENTER);
	public static final CellPosition FULL_WIDTH_1_COL
		= new CellPosition(HorizontalPosition.FULL, VerticalPosition.CENTER, 1);
	public static final CellPosition FULL_WIDTH_2_COL
	= new CellPosition(HorizontalPosition.FULL, VerticalPosition.CENTER, 3);

	public static final CellPosition DEFAULT_LABEL_POSITION  = RIGHT;
	public static final CellPosition DEFAULT_FIELD_POSITION  = FULL_WIDTH;
	public static final CellPosition DEFAULT_FIELD2_POSITION = LEFT;
	public static final CellPosition FULL_HEIGHT
			= new CellPosition(HorizontalPosition.FULL, VerticalPosition.FULL);


	
	private final int columnCount;
	public final HorizontalPosition horizontalPosition;
	public final VerticalPosition verticalPosition;
	
	
	public CellPosition(HorizontalPosition horizontalPosition, VerticalPosition verticalPosition) {
		this(horizontalPosition, verticalPosition, Integer.MAX_VALUE);
	}
	
	public CellPosition(HorizontalPosition horizontalPosition, VerticalPosition verticalPosition, int colCount) {
		super();
		
		this.columnCount = colCount;
		this.horizontalPosition = horizontalPosition;
		this.verticalPosition = verticalPosition;
	}
	
	public int numberOfColumns(int colsAvaialable) {
		
		if (columnCount == Integer.MAX_VALUE) {
			return colsAvaialable;
		}
		
		return columnCount;
	}
	
}
