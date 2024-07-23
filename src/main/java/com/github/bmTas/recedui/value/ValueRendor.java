package com.github.bmTas.recedui.value;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.ITableCellEditAndRender;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class ValueRendor <Value> extends AbstractCellEditor implements ITableCellEditAndRender, IValueChangedListner {

	private final IUiValueComponent<Value> field;
	
	public ValueRendor(IUiValueComponent<Value> valueField) {
		super();
		this.field = valueField;
		valueField.addListner(this);
	}

	@Override
	public Object getCellEditorValue() {
		return field.getValue();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setValue(value);
		SwingUtils.values().setTableCellColors(field.getMainComponent(), table, row, isSelected);
		return field.getGuiContainer();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		setValue(value);
		return field.getGuiContainer();
	}

	@SuppressWarnings("unchecked")
	private void setValue(Object value) {
		try {
			field.setValue((Value) value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void valueChanged() {
		super.fireEditingStopped();
	}
	
	
}
