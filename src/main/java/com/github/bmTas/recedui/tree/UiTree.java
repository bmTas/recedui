package com.github.bmTas.recedui.tree;

import javax.swing.JTree;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.component.IUiTree;
import com.github.bmTas.recedui.tree.TreeParamBuilder.Builder;
import com.github.bmTas.recedui.tree.TreeParamBuilder.ITreeParamBuilder1;

public class UiTree extends UiStdComponent<JTree> implements IUiTree {
	public static ITreeParamBuilder1 newBuilder() { return new Builder(); }

	public UiTree(ITreeParam param) {
		super(param.getStyle(), param.getTree());
	}


}
