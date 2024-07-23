package com.github.bmTas.recedui.form;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.github.bmTas.recedui.def.component.IGetContainer;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

public class SingleLineBuilder {
	public static final CellOptions RIGHT
			= new CellOptions(FormHelper.PREFERRED, HorizontalPosition.RIGHT, VerticalPosition.CENTER);
	public static final CellOptions LEFT
			= new CellOptions(FormHelper.PREFERRED, HorizontalPosition.LEFT, VerticalPosition.CENTER);
	public static final CellOptions FILL_RIGHT
		= new CellOptions(FormHelper.FILL, HorizontalPosition.RIGHT, VerticalPosition.CENTER);
	public static final CellOptions FILL_LEFT
		= new CellOptions(FormHelper.FILL, HorizontalPosition.LEFT, VerticalPosition.CENTER);

	private final ArrayList<FieldDefinition> fieldPos = new ArrayList<FieldDefinition>();
	private final double gap, rightBorder;
	private final TableLayout tLayout = new TableLayout();
	private final JPanel panel;
	private int col = 0;
	private double fieldGap;
	
	
	public SingleLineBuilder() {
		this(new JPanel(), 0, 0, 0);
	}
	

	public SingleLineBuilder(JPanel panel, double leftBorder, double gap, double rightBorder) {
		super();
		
		this.panel = panel;
		//this.leftBorder = leftBorder;
		this.gap = gap;
		this.rightBorder = rightBorder;
		
		fieldGap = leftBorder;
		
		panel.setLayout(tLayout);
	}
	
	public SingleLineBuilder add(String label, CellOptions cellOptions) {
		//panel.add(component.getJContainer());
		return this;
	}
	
	public SingleLineBuilder add(IGetContainer component, CellOptions cellOptions) {
		return this.add(component.getGuiContainer(), cellOptions);
	}
	
	public SingleLineBuilder add(Component component, CellOptions cellOptions) {
		col += fieldGap > 0 ? 2 : 1;
		panel.add(component,
				new TableLayoutConstraints(col, 0, col, 0,
						cellOptions.horizontalPosition.cellPosition,
						cellOptions.verticalPosition.cellPosition));
		
		fieldPos.add(new FieldDefinition(fieldGap, cellOptions.rowOption));
		fieldGap = gap;
		return this;
	}

	public JPanel build() {
		double[] rows = {FormHelper.PREFERRED};
		double[] cols = new double[col +  rightBorder > 0 ? 2 : 1];
		
		int idx = 0;
		for (FieldDefinition fdef : fieldPos ) {
			if (fdef.gap > 0) {
				cols[idx++] = fdef.gap;
			}
			cols[idx++] = fdef.width;
		}
		if (rightBorder > 0) {
			cols[idx++] = rightBorder;
		}
		
		tLayout.setRow(rows);
		tLayout.setColumn(cols);
		
		return panel;
	}
	

	private static class FieldDefinition {
		final double gap, width;
		
		public FieldDefinition(double gap, double width) {
			super();
			this.gap = gap;
			this.width = width;
		}
		
	}
	
	public static class CellOptions extends CellPosition {

		final double rowOption;
		public CellOptions(double rowOption, HorizontalPosition horizontalPosition, VerticalPosition verticalPosition) {
			super(horizontalPosition, verticalPosition);
			this.rowOption = rowOption;
		}
	}
}
