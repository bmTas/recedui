package com.github.bmTas.recedui.text;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.ITableCellEditAndRender;
import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class TextRender extends AbstractCellEditor implements ITableCellEditAndRender {

	private final IUiTextField textfield;
	
	public TextRender(IUiTextField textfield) {
		super();
		this.textfield = textfield;
	}

	@Override
	public Object getCellEditorValue() {
		return textfield.getText();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		textfield.setText(value == null ? "" : value.toString());
		SwingUtils.values().setTableCellColors(textfield.getMainComponent(), table, row, isSelected);
		return textfield.getGuiContainer();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		textfield.setText(value == null ? "" : value.toString());
		return textfield.getGuiContainer();
	}

}
