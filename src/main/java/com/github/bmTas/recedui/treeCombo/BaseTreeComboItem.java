package com.github.bmTas.recedui.treeCombo;

import java.util.List;

import javax.swing.Icon;

import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;

public abstract class BaseTreeComboItem<Code> implements ITreeComboItem<Code>  {

	private final String value;
	private final Icon icon;
	private final List<? extends ITreeComboItem<Code>> children;
	
//	public BaseTreeComboItem(String value, Icon icon) {
//		this.value = value;
//		this.icon = icon;
//		this.children = null;
//	}
//	
//	public BaseTreeComboItem(String value, Icon icon, ITreeComboItem[] children) {
//		this(value, icon, Arrays.asList(children));
//	}
	
	protected BaseTreeComboItem(String value, Icon icon, List<? extends ITreeComboItem<Code>> children) {
		this.value = value;
		this.icon = icon;
		if (children == null) {
			this.children = null;
		} else {
			this.children = children;
		}
	}

	@Override
	public boolean hasChildren() {
		return children != null;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public List<? extends ITreeComboItem<Code>> getChildren() {
		return children;
	}
}
