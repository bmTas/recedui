package com.github.bmTas.recedui.treeCombo;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;

public class TreeComboCodeItem<Code> extends BaseTreeComboItem<Code> {

	private final Code code;
	public TreeComboCodeItem(String value, Icon icon, Code code) {
		super(value, icon, null);
		
		this.code = code;
	}

	public TreeComboCodeItem(String value, Icon icon, ITreeComboItem<Code>[] children) {
		this(value, icon, Arrays.asList(children));
	}

	public TreeComboCodeItem(String value, Icon icon, List<? extends ITreeComboItem<Code>> children) {
		super(value, icon, children);
		
		this.code = null;
	}

	@Override
	public Code getCode() {
		return code;
	}

}
