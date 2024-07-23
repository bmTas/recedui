package com.github.bmTas.recedui.treeCombo;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.ITableCellEditAndRender;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboModel;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class TreeComboRendor<Code> extends AbstractCellEditor implements
		ITableCellEditAndRender {

	private final UiTreeCombo<Code> combo;
	private int clickCountToStart = 1;
//	private final TreeComboModel<Code> comboItems;
//	private final String[] strItems;

	public TreeComboRendor(ITreeComboModel<Code> itms) {
//		comboItems = itms;
		combo = new UiTreeCombo<Code>(itms);
		combo.setBorder(null);
	}

	@Override
	public Object getCellEditorValue() {
		ITreeComboItem<Code> selectedItem = combo.getSelectedItem();

		if (selectedItem == null 
		|| selectedItem.toString().length() == 0
		) {
			return combo.getText();
		}
		return selectedItem;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		setVal(value);

		return combo;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		setVal(value);
		SwingUtils.values().setTableCellColors(combo, table, row, isSelected);

		return combo;
	}

    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
        }
        return true;
    }


	/**
	 * @param clickCountToStart the clickCountToStart to set
	 */
	public void setClickCountToStart(int clickCountToStart) {
		this.clickCountToStart = clickCountToStart;
	}

	private void setVal(Object value) {

		if (value == null) {
			combo.setBlankItem();
		} else {
			//System.out.print(" --> " + value + " << " + value.getClass().getName() + " ");
			if (value instanceof ITreeComboItem) {
				combo.setSelectedItem((ITreeComboItem<Code>) value);
			} else if (combo.updateIfCode(value)) {
			} else if (value instanceof String) {
				combo.setSelectedString((String) value); 
				//combo.setOnlySelectedItem(TreeComboItem.BLANK_ITEM);
				//combo.setText((String) value);
			} else {
				combo.setBlankItem();
			}
//			System.out.println(combo.getText());
		}
	}

	public void setTableFont(Font font) {
		combo.setFont(font);
	}
}
