package com.github.bmTas.recedui.treeCombo;

import java.util.Arrays;
import java.util.List;

import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboModel;

public class TreeComboModel<Code> implements ITreeComboModel<Code> {

	private final List<? extends ITreeComboItem<Code>> children;

	public TreeComboModel(ITreeComboItem<Code>[] children) {
		super();
		this.children = Arrays.asList(children);
	}


	public TreeComboModel(List<? extends ITreeComboItem<Code>> children) {
		super();
		this.children = children;
	}

	@Override
	public List<? extends ITreeComboItem<Code>> getChildren() {
		return children;
	}

}
