package com.github.bmTas.recedui.tree;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.component.IGetJScrollPaneContainer;
import com.github.bmTas.recedui.def.component.IUiTree;
import com.github.bmTas.recedui.tree.TreeParamBuilder.Builder;
import com.github.bmTas.recedui.tree.TreeParamBuilder.ITreeParamBuilder1;


public class UiScrollTree extends UiStdComponent2<JScrollPane, JTree> implements IUiTree, IGetJScrollPaneContainer {
	public static ITreeParamBuilder1 newBuilder() { return new Builder(); }

	public UiScrollTree(ITreeParam param) {
		super(param.getStyle(), new JScrollPane(param.getTree()), param.getTree());
	}

	public void setCellRenderer(TreeCellRenderer render) {
		super.component.setCellRenderer(render);
	}

}
