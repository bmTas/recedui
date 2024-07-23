package com.github.bmTas.recedui.def.treeCombo;

import java.util.List;

import javax.swing.Icon;

import com.github.bmTas.recedui.combobox.ICodeItem;

public interface ITreeComboItem<Code> extends ICodeItem<Code> {

	boolean hasChildren();
//	String toString();
	Icon getIcon();
//	Code getCode();
	
	List<? extends ITreeComboItem<Code>> getChildren();
}
