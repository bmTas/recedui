package com.github.bmTas.recedui.combobox;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.ITableCellEditAndRender;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class ComboBoxRendor<CodeItem> extends AbstractCellEditor implements ITableCellEditAndRender {

	private final UiComboBox<CodeItem> combo;

	public ComboBoxRendor(ComboBoxModel<CodeItem> model) {
		this.combo = new UiComboBox<CodeItem>(model);
	}


	public ComboBoxRendor(IComboBoxParam<CodeItem> param) {
		this.combo = new UiComboBox<CodeItem>(param);
	}

	@Override
	public Object getCellEditorValue() {
		return combo.getSelectedItem();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JComboBox<CodeItem> mainComponent = combo.getMainComponent();
		
		SwingUtils.values().setTableCellColors(mainComponent, table, row, isSelected);
		mainComponent.setSelectedItem(value);

		return mainComponent;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	
		combo.getMainComponent().setSelectedItem(value);
		return combo.getMainComponent();
	}
}
