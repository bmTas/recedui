package com.github.bmTas.recedui.combobox;

import javax.swing.ComboBoxModel;

import com.github.bmTas.recedui.styles.IStyle;

public interface IComboBoxParam<ComboItem> {
	public IStyle getStyle();
	public ComboBoxModel<ComboItem> getModel();
}
