package com.github.bmTas.recedui.form;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.bmTas.recedui.def.component.IUiComponent;

import info.clearthought.layout.TableLayoutConstraints;

public class LineBuilder<ReturnBuilder> {
	private final ReturnBuilder returnBldr;
	private final JPanel pnl;
//	private final TableLayout tblLayout;
	
	private int rowNum, colNum = -1;

	private final CellPosition labelPosition;
	private String formName, name = "";
	private JLabel lastLabel = null;

	public LineBuilder(ReturnBuilder returnBldr, String formName, JPanel pnl, int rowNum,
			CellPosition labelPosition) {
		this.returnBldr = returnBldr;
		this.formName = formName;
		this.pnl = pnl;
//		this.tblLayout = tblLayout;
		this.rowNum = rowNum;
		this.labelPosition = labelPosition;
	}

	public final void setRowNum(int rowNum) {
		this.rowNum = rowNum;
		this.name = "";
	}

	public LineBuilder<ReturnBuilder> add(String label) {
		return add(label, labelPosition);
	}

	public LineBuilder<ReturnBuilder> add(JLabel label) {
		name = label.getText();
		addCell(label, labelPosition, false);
		return this;
	}
	
	public LineBuilder<ReturnBuilder> add(String label, CellPosition pos) {
		name = label;
		
		lastLabel = label == null ? null : new JLabel(label);
		addCell(lastLabel, pos, false);
		return this;
	}

	public LineBuilder<ReturnBuilder> add(IUiComponent field, CellPosition pos) {
		
		field.setFieldsLabel(lastLabel);
		addCell(field.getMainComponent(), pos, true);
		lastLabel = null;
		
		return this;
	}

	public LineBuilder<ReturnBuilder> add(JComponent field, CellPosition pos) {

		addCell(field, pos, true);
		
		return this;
	}

	
	private void addCell(JComponent fld, CellPosition pos, boolean addName) {
		colNum += 2;
		if (fld != null) {
			if (addName && name.length() > 0) {
				fld.setName(formName + name);
			}
			pnl.add(fld,
					new TableLayoutConstraints(colNum, rowNum, colNum, rowNum,
							pos.horizontalPosition.cellPosition,
							pos.verticalPosition.cellPosition));
		}
	}
	
	public ReturnBuilder  lineEnd() {
		return returnBldr;
	}
}
