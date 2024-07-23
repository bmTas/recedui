package com.github.bmTas.recedui.form;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.form.help.IHelpDisplay;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

/**
 * Create a Three Column form
 * 
 * @author Bruce Martin
 *
 */
public class ThreeColumnFormBuilder {

	public static final int FIELD_HEIGHT_INDEX = 1;
	public static final int GAP_HEIGHT_INDEX = 2;

	private static final double BORDER = 10;
	private static final double[] VERTICAL_GAPS_DEFAULT 
			= {BORDER, FormHelper.PREFERRED, 5, FormHelper.FILL, 5, FormHelper.PREFERRED, BORDER};
	private static final double[] HORIZONTAL_GAPS_DEFAULT = {
			BORDER, 
			FormHelper.NORMAL_HEIGHT, 
			FormHelper.GAP, 5};

	private final String formName;
	private final double[] rowGaps; 

	private final JPanel pnl;
	private final TableLayout tblLayout = new TableLayout();
	private final CellPosition labelPosition, defaultFieldPosition, lastPosition;
	private final IHelpDisplay helpDisplay;
	
	private int rowNum=-2;
	
	private double[] lSize = new double[40];
	
	public final int labelColumn, fieldColumnCount, lastFieldColumn, lastColumn;
	
	
	public ThreeColumnFormBuilder(String formName, JPanel panel,
			double[] rowGaps, double[] colGaps, 
			CellPosition labelPosition, CellPosition defaultFieldPosition, CellPosition lastPosition,
			int fieldColumnCount,
			IHelpDisplay help) {
		super();
		
		colGaps = colGaps == null ? VERTICAL_GAPS_DEFAULT : colGaps;
		rowGaps = rowGaps == null ? HORIZONTAL_GAPS_DEFAULT : rowGaps;
		
		this.fieldColumnCount =  (fieldColumnCount) * 2;
		this.lastFieldColumn = 2 + this.fieldColumnCount;
		int cols = 3 + this.fieldColumnCount;
		if (colGaps.length < cols) {
			double[] tmp = new double[cols];
			System.arraycopy(colGaps, 0, tmp, 0, colGaps.length - 1);
			tmp[tmp.length - 1] = colGaps[colGaps.length - 1];
			for (int i = -1; i < fieldColumnCount-1; i++) {
				tmp[colGaps.length + i * 2 - 1 ] = colGaps[2];
				tmp[colGaps.length + i * 2  ] = FormHelper.PREFERRED;
			}
			tmp[tmp.length - 1] = colGaps[colGaps.length - 1];
			colGaps = tmp;
		}
		lastColumn = colGaps.length - 1;
		
		this.formName = formName + ".";
		this.rowGaps = rowGaps;
		this.labelColumn = colGaps[0] == 0 ? 0 : 1;
		//this.fieldColumnCount = (fieldColumnCount) * 2 - 1;
		this.labelPosition = labelPosition;
		this.defaultFieldPosition = defaultFieldPosition;
		this.lastPosition = lastPosition;
		this.helpDisplay = help;
		
		tblLayout.setColumn(colGaps);
		
		if (rowGaps[0] > 0) {
			lSize[0] = rowGaps[0];
			rowNum+=1;
		}
			
		if (panel == null) {
			this.pnl = new JPanel(tblLayout);
		} else {
			this.pnl = panel;
			panel.setLayout(tblLayout);
		}
	}
	
	public ThreeColumnFormBuilder ls(
			JComponent field, CellPosition fieldPosition, 
			int rowCount, int firstCol, int lastCol ) {
		incRow();
		pnl.add(field,
				new TableLayoutConstraints(firstCol, rowNum, lastCol, rowNum + rowCount,
						fieldPosition.horizontalPosition.cellPosition,
						fieldPosition.verticalPosition.cellPosition));
		return this;
	}
	
	public ThreeColumnFormBuilder cell(
			JComponent field, CellPosition fieldPosition, 
			int rowCount, int firstCol, int lastCol ) {
		pnl.add(field,
				new TableLayoutConstraints(firstCol, rowNum, lastCol, rowNum + rowCount,
						fieldPosition.horizontalPosition.cellPosition,
						fieldPosition.verticalPosition.cellPosition));
		return this;
	}

	public ThreeColumnFormBuilder line(String label, IUiComponent field) {
		return line(label, field, null, defaultFieldPosition);
	}
	
	public ThreeColumnFormBuilder line(String label, IUiComponent field, IUiComponent field2) {
		return line(label, field, field2, defaultFieldPosition);
	}
	
	public ThreeColumnFormBuilder line(JComponent label, IUiComponent field, IUiComponent field2) {
		buildLine(label, null, jComponent(field), jComponent(field2), defaultFieldPosition);
		return this;
	}
	
	public ThreeColumnFormBuilder line(String label, IUiComponent field, IUiComponent field2, CellPosition fieldPosition) {
		JLabel jLabel = null;
		Component jComponent = jComponent(field);
		
		if (label == null || label.length() == 0) {
			label = jComponent == null ? "" : jComponent.getName();
		} else {
			jLabel = new JLabel(label);
			
			if (field != null) {
				field.setFieldsLabel(jLabel);
			}
		}
		buildLine(jLabel, label, jComponent, jComponent(field2), fieldPosition);
		
		return this;
	}

	private Component jComponent(IUiComponent field) {
		return field == null ? null : field.getGuiContainer();
	}
	
	
	public ThreeColumnFormBuilder lineSwing(String label, JComponent field) {
		return lineSwing(label, field, null);
	}

	/**
	 * Line using swing fields
	 * 
	 */
	public ThreeColumnFormBuilder lineSwing(String label, JComponent field, JComponent fld2) {
		buildLine(label == null ? null : new JLabel(label), label, field, fld2, defaultFieldPosition);
		return this;
	}
	
	
	/**
	 * Line using swing fields
	 * 
	 */
	public ThreeColumnFormBuilder lineSwing(JComponent label, JComponent field, JComponent fld2, CellPosition fieldPosition ) {
		buildLine(label, null, field, fld2, fieldPosition);
		return this;
	}
	
	private void buildLine(JComponent label, String name, Component itm, Component fld2, CellPosition fieldPosition) {
		
		incRow();
		if (label != null) {
			pnl.add(label,
					new TableLayoutConstraints(1, rowNum, 1, rowNum,
							labelPosition.horizontalPosition.cellPosition,
							labelPosition.verticalPosition.cellPosition));
			//lSize[rowNum] = Math.max(lSize[rowNum], label.getPreferredSize().height);
		}
		if (itm != null) {
			itm.setName(formName + name);
			pnl.add(itm,
					new TableLayoutConstraints(3, rowNum, 2 + fieldPosition.numberOfColumns(fieldColumnCount), rowNum,
							fieldPosition.horizontalPosition.cellPosition,
							fieldPosition.verticalPosition.cellPosition));
			helpDisplay.register(itm);
			//lSize[rowNum] = Math.max(lSize[rowNum], itm.getPreferredSize().height);
		}
		if (fld2 != null) {
			int col = lastFieldColumn + 1;
			itm.setName(formName + name + "_2");
			
			CellPosition fw = lastPosition; //CellPosition.FULL_WIDTH;
			pnl.add(fld2,
					new TableLayoutConstraints(col, rowNum, col, rowNum,
							fw.horizontalPosition.cellPosition,
							fw.verticalPosition.cellPosition));
			//lSize[rowNum] = Math.max(lSize[rowNum], itm.getPreferredSize().height);
			helpDisplay.register(itm);
		}

	}

	/**
	 * Increment the row counter and set standard Hieght and Gap
	 *
	 */
	public final void incRow() {

		incRow(1, rowGaps[FIELD_HEIGHT_INDEX], rowGaps[GAP_HEIGHT_INDEX]);
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
		
		if (rowNum+1 >= lSize.length) {
			double[] tmp = new double[rowNum + 41];
			System.arraycopy(lSize, 0, tmp, 0, lSize.length);
			lSize = tmp;
		}

		lSize[rowNum]     = height;
		lSize[rowNum + 1] = gap;
	}
	
	public final ThreeColumnFormBuilder setRowHeight(double height) {
		lSize[rowNum]     = height;		
		return this;
	}

	public LineBuilder<ThreeColumnFormBuilder> line() {
		incRow();
		return new LineBuilder<ThreeColumnFormBuilder>(this, formName, pnl, rowNum, labelPosition);
	}
	
	/**
	 * Set the Horizontal Gap between the current Field and the Next Field
	 *
	 * @param gap Line gap param gap Line gap Typically you would use<ul>
	 * <li><b>FormHelper.GAP2</b>
	 * <li><b>FormHelper.GAP32</b>
	 * <li> or some thing like the above </ul>
	 */
	public final ThreeColumnFormBuilder setGap(double gap) {
		lSize[rowNum + 1] = gap;
		return this;
	}

	public JPanel build() {
		
		double[] hGap = new double[rowNum + 2];
		
		System.arraycopy(lSize, 0, hGap, 0, rowNum + 2);
		tblLayout.setRow(hGap);
		return pnl;
	}

	/**
	 * @return the helpDisplay
	 */
	public final IHelpDisplay getHelpDisplay() {
		return helpDisplay;
	}
}
