package com.github.bmTas.recedui.treeCombo;

import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboModel;
import com.github.bmTas.recedui.standard.component.IBaseComboParam;

/**
 * TreeCombo parameter
 * @author Bruce Martin
 *
 * @param <Code> code for ComboItem
 */
public interface ITreeComboParam<Code> extends IBaseComboParam {

	ITreeComboModel<Code> getComboModel();
	ITreeComboItem<Code> getBlankItem();
	boolean isAddBlankItem();
}