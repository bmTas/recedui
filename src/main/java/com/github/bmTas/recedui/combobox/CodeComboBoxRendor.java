package com.github.bmTas.recedui.combobox;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.ITableCellEditAndRender;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class CodeComboBoxRendor<Code, CodeItem extends ICodeItem<Code>> extends AbstractCellEditor implements ITableCellEditAndRender {

	private final UiCodeComboBox<Code, CodeItem> combo;
	public CodeComboBoxRendor(UiCodeComboBox<Code, CodeItem> combo) {
		this.combo = combo;
	}
	public CodeComboBoxRendor(ComboBoxModel<CodeItem> model) {
		this.combo = new UiCodeComboBox<Code, CodeItem>(new ComboBoxParam<CodeItem>().setModel(model));
	}

	public CodeComboBoxRendor(IComboBoxParam<CodeItem> param) {
		this.combo = new UiCodeComboBox<Code, CodeItem>(param);
	}


	@Override
	public Object getCellEditorValue() {
		return combo.getSelectedCode();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		combo.setCodeFromObj(value);
		
		SwingUtils.values().setTableCellColors(combo.getMainComponent(), table, row, isSelected);
		
		return combo.getMainComponent();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		combo.setCodeFromObj(value);
		return combo.getMainComponent();
	}
}
