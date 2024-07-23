package com.github.bmTas.recedui.combobox;

import java.util.Collection;

import javax.swing.ComboBoxModel;

import com.github.bmTas.recedui.styles.IStyle;

public interface IComboBoxParamBuilder<CodeItem> {
	IComboBoxParamBuilder<CodeItem> setStyle(IStyle style);
	IComboBoxParamBuilder<CodeItem> setModel(CodeItem[] collection);
	IComboBoxParamBuilder<CodeItem> setModel(Collection<CodeItem> collection);
	IComboBoxParamBuilder<CodeItem> setModel(ComboBoxModel<CodeItem> model);
	IComboBoxParam<CodeItem> asParam();
}
