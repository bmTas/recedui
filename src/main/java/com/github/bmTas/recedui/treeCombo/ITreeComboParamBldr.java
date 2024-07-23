package com.github.bmTas.recedui.treeCombo;

import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboModel;
import com.github.bmTas.recedui.styles.IStyle;

public interface ITreeComboParamBldr<Code> {

	ITreeComboParamBldr<Code> setComboModel(ITreeComboModel<Code> comboModel);

	ITreeComboParamBldr<Code> setComboModel(ITreeComboItem<Code>[] children);

	ITreeComboParamBldr<Code> setComboModel(List<ITreeComboItem<Code>> children);

	ITreeComboParamBldr<Code> setStyle(IStyle style);

	ITreeComboParamBldr<Code> setTxtComponent(JTextComponent txtComponent);

	ITreeComboParamBldr<Code> setFieldName(String fieldName);

	ITreeComboParamBldr<Code> setPopup(JPopupMenu popup);

	ITreeComboParamBldr<Code> setBlankItem(ITreeComboItem<Code> blankItem);
	
	ITreeComboParamBldr<Code> setAddBlankItem(boolean addBlankItem);



	ITreeComboParam<Code> asParam();
}