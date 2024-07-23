package com.github.bmTas.recedui.form;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.github.bmTas.recedui.def.component.IUiComponent;

import info.clearthought.layout.TableLayoutConstraints;

/**
 * 
 * @author Bruce Martin
 *
 */
public class TwoColumnFormBuilder {

	public static final int FIELD_HEIGHT_INDEX = 1;
	public static final int GAP_HEIGHT_INDEX = 2;

//	private static final double BORDER = 10;
//	private static final double[] VERTICAL_GAPS_DEFAULT 
//			= {BORDER, FormHelper.PREFERRED, 5, FormHelper.FILL, BORDER};
//	private static final double[] HORIZONTAL_GAPS_DEFAULT = {
//			BORDER, 
//			FormHelper.PREFERRED, 
//			FormHelper.GAP, 5};

//	private final String formName;
//	private final double[] rowGaps; 

	private final JPanel pnl;
//	private final TableLayout tblLayout = new TableLayout();
	private final CellPosition labelPosition, defaultFieldPosition;
	
//	private int rowNum=-2;
	
//	private double[] lSize = new double[40];
	
//	final int labelColumn, fieldColumnCount, lastFieldColumn;
	
	private final FormBuilderDetails builderDetails;
	
	public TwoColumnFormBuilder(String formName_, JPanel panel,
			double[] rowGaps_, double[] columnGaps, 
			CellPosition labelPosition, CellPosition defaultFieldPosition,
			int fieldColumnCount) {
		super();
		
		builderDetails = new FormBuilderDetails(
				formName_,  rowGaps_, columnGaps, true, fieldColumnCount);
		
//		colGaps = colGaps == null ? VERTICAL_GAPS_DEFAULT : colGaps;
//		rowGaps = rowGaps == null ? HORIZONTAL_GAPS_DEFAULT : rowGaps;
//		
//		this.fieldColumnCount =  (fieldColCount) * 2 - 1;
//		this.lastFieldColumn = 2 + this.fieldColumnCount;
//		int cols = 4 + this.fieldColumnCount;
//		if (colGaps.length < cols) {
//			double[] tmp = new double[cols];
//			System.arraycopy(colGaps, 0, tmp, 0, colGaps.length - 1);
//			for (int i = 0; i < fieldColCount-1; i++) {
//				tmp[colGaps.length + i * 2 - 1 ] = colGaps[2];
//				tmp[colGaps.length + i * 2  ] = FormHelper.PREFERRED;
//			}
//			tmp[tmp.length - 1] = colGaps[colGaps.length - 1];
//			colGaps = tmp;
//		}
//		
//		this.formName = formName + ".";
//		this.rowGaps = rowGaps;
//		this.labelColumn = colGaps[0] == 0 ? 0 : 1;
		this.labelPosition = labelPosition;
		this.defaultFieldPosition = defaultFieldPosition;
//		
//		tblLayout.setColumn(builderDetails.getColumnGaps());
		
//		if (builderDetails.rowGaps[0] > 0) {
//			lSize[0] = builderDetails.rowGaps[0];
//			rowNum+=1;
//		}
			
		if (panel == null) {
			this.pnl = new JPanel(builderDetails.getLayout());
		} else {
			this.pnl = panel;
			panel.setLayout(builderDetails.getLayout());
		}
	}
	
	/**
	 * @return the labelColumn
	 */
	public int getLabelColumn() {
		return builderDetails.labelColumn;
	}

	/**
	 * @return the lastFieldColumn
	 */
	public int getLastFieldColumn() {
		return builderDetails.lastFieldColumn;
	}
	
	public TwoColumnFormBuilder setSwingToolBar(JToolBar toolbar) {
		builderDetails.incForToolbar(FormHelper.PREFERRED);
		pnl.add(toolbar,
				new TableLayoutConstraints(0, 0, 3, 0,
						HorizontalPosition.FULL.cellPosition,
						VerticalPosition.CENTER.cellPosition));
		return this;
		/*
		 				new TableLayoutConstraints(
						firstCol, builderDetails.getRowNumber(), lastCol, builderDetails.getRowNumber() + rowCount-1,
						fieldPosition.horizontalPosition.cellPosition,
						fieldPosition.verticalPosition.cellPosition));

		 */

	}
	/**
	 * Create a line with one field in it
	 * @param field any UiField
	 * @param fieldPosition How to position the field e.g.<ul>
	 *  <li>CellPosition.LEFT
	 *  <li>CellPosition.RIGHT etc;
	 * </ul>
	 */
	public TwoColumnFormBuilder line(IUiComponent field, CellPosition fieldPosition ) {
		incRow();

		return cellSwing(field.getGuiContainer(), fieldPosition, 1, getLabelColumn(), getLastFieldColumn());
	}

	/**
	 * Create a line with one field in it
	 * @param field any UiField
	 * @param fieldPosition How to position the field e.g.<ul>
	 *  <li>CellPosition.LEFT
	 *  <li>CellPosition.RIGHT etc;
	 * </ul>
	 * @param rowCount number of rows the field occupies
	 * @param firstCol first column the field occupies
	 * @param lastCol last column the field occupies
	 */
	public TwoColumnFormBuilder line(
			IUiComponent field, CellPosition fieldPosition, 
			int rowCount, int firstCol, int lastCol ) {
		incRow();

		return cellSwing(field.getGuiContainer(), fieldPosition, rowCount, firstCol, lastCol);
	}
	
	
	/**
	 * Create a line with one field in it
	 */
	public TwoColumnFormBuilder lineSwing(
			JComponent field, CellPosition fieldPosition, 
			int rowCount, int firstCol, int lastCol ) {
		incRow();

		return cellSwing(field, fieldPosition, rowCount, firstCol, lastCol);
	}

	
	/**
	 * Position a cell anywhere in the line
	 */
	public TwoColumnFormBuilder cell(
			IUiComponent field, CellPosition fieldPosition, 
			int rowCount, int firstCol, int lastCol ) {
		return cellSwing(field.getGuiContainer(), fieldPosition, rowCount, firstCol, lastCol);
	}

	
	/**
	 * Position a cell anywhere in the line
	 */
	public TwoColumnFormBuilder cellSwing(
			Component field, CellPosition fieldPosition, 
			int rowCount, int firstCol, int lastCol ) {
		
		pnl.add(field,
				new TableLayoutConstraints(
						firstCol, builderDetails.getRowNumber(), lastCol, builderDetails.getRowNumber() + rowCount-1,
						fieldPosition.horizontalPosition.cellPosition,
						fieldPosition.verticalPosition.cellPosition));
		return this;
	}

	public TwoColumnFormBuilder line(IUiComponent field, double height) {
		
		incRow();
		if (field != null) {
			String fieldName = field.getClass().getSimpleName();
			field.getMainComponent().setName(builderDetails.formName + fieldName);
			int rowNumber = builderDetails.getRowNumber();
			CellPosition fieldPosition = defaultFieldPosition;
			pnl.add(field.getGuiContainer(),
					new TableLayoutConstraints(1, rowNumber,
							2 + fieldPosition.numberOfColumns(builderDetails.fieldColumnCount),
							rowNumber,
							fieldPosition.horizontalPosition.cellPosition,
							fieldPosition.verticalPosition.cellPosition));
			//lSize[rowNum] = Math.max(lSize[rowNum], itm.getPreferredSize().height);
//			new TableLayoutConstraints(1, rowNumber, 1, rowNumber,
//					labelPosition.horizontalPosition.cellPosition,
//					labelPosition.verticalPosition.cellPosition);
			setRowHeight(height);
		}

		return this;
	}

	public TwoColumnFormBuilder line(String label, IUiComponent field) {
		return line(label, field, defaultFieldPosition);
	}
	
	public TwoColumnFormBuilder line(String label, IUiComponent field, CellPosition fieldPosition) {
		JLabel jLabel = null;
		Component jComponent = field == null ? null : field.getGuiContainer();
		String fieldName = label;
		if (label == null || label.length() == 0) {
			fieldName = field == null ? "" : field.getClass().getSimpleName();
		} else {
			jLabel = new JLabel(label);
			
			if (field != null) {
				field.setFieldsLabel(jLabel);
			}
		}
		buildLine(jLabel, fieldName, jComponent, fieldPosition);
		
		return this;
	}
	
	
	/**
	 * Line using swing fields
	 * 
	 */
	public TwoColumnFormBuilder lineSwing(String label, JComponent field) {
		buildLine(label == null ? null : new JLabel(label), label, field, defaultFieldPosition);
		return this;
	}
	
	public TwoColumnFormBuilder lineSwing(String label, JComponent field, CellPosition fieldPosition) {
		buildLine(label == null ? null : new JLabel(label), label, field, fieldPosition);
		return this;
	}
	
	
	/**
	 * Line using swing fields
	 * 
	 */
	public TwoColumnFormBuilder lineSwing(JComponent label, JComponent field, CellPosition fieldPosition ) {
		buildLine(label, null, field, fieldPosition);
		return this;
	}
	
	private void buildLine(JComponent label, String fieldName, Component field, CellPosition fieldPosition) {
		
		incRow();
		if (label != null) {
			int rowNumber = builderDetails.getRowNumber();
			pnl.add(label,
					new TableLayoutConstraints(1, rowNumber, 1, rowNumber,
							labelPosition.horizontalPosition.cellPosition,
							labelPosition.verticalPosition.cellPosition));
		}
		if (field != null) {
			fieldName = fieldName == null ? "" : fieldName;
			field.setName(builderDetails.formName + fieldName);
			int rowNumber = builderDetails.getRowNumber();
			pnl.add(field,
					new TableLayoutConstraints(3, rowNumber,
							2 + fieldPosition.numberOfColumns(builderDetails.fieldColumnCount),
							rowNumber,
							fieldPosition.horizontalPosition.cellPosition,
							fieldPosition.verticalPosition.cellPosition));
			//lSize[rowNum] = Math.max(lSize[rowNum], itm.getPreferredSize().height);
		}

	}

	/**
	 * Increment the row counter and set standard Height and Gap
	 *
	 */
	public final TwoColumnFormBuilder incRow() {

		incRow(1, builderDetails.rowGaps[FIELD_HEIGHT_INDEX], builderDetails.rowGaps[GAP_HEIGHT_INDEX]);
		return this;
	}


	/**
	 * Increment the row counter and set the Height and Gap amounts to the
	 * supplied values
	 *
	 * @param height Height of the Field
	 * @param gap    Gap that will follow the field
	 */
	public final void incRow(int rows, double height, double gap) {
		builderDetails.incRow(rows, height, gap);
	}
	
	public TwoColumnFormBuilder setRowHeight(double height) {
		builderDetails.setRowHeight(height);
		return this;
	}
	
	/**
	 * Set the Horizontal Gap between the current Field and the Next Field
	 *
	 * @param gap Line gap Typically you would use<ul>
	 * <li><b>FormHelper.GAP2</b>
	 * <li><b>FormHelper.GAP32</b>
	 * <li> or some thing like the above </ul>
	 * 
	 */
	public final TwoColumnFormBuilder setGap(double gap) {
		builderDetails.setGap(gap);
		return this;
	}

	public JPanel build() {
		builderDetails.applyRowDetailsToPanelLayout();
//		double[] hGap = new double[rowNum + 2];
//		
//		System.arraycopy(lSize, 0, hGap, 0, rowNum + 2);
//		tblLayout.setRow(hGap);
		return pnl;
	}
}
