package com.github.bmTas.recedui.tree;

import javax.swing.JTree;

import com.github.bmTas.recedui.styles.ITreeStyle;

public interface ITreeParam {
	
	/**
	 * 
	 * @return
	 */
	ITreeStyle getStyle();

	/**
	 * Get the Tree
	 * @return return the tree
	 */
	JTree getTree();
}
