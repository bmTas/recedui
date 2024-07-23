package com.github.bmTas.recedui.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

/**
 * Basic TreeNode based on an ArrayList
 * @author Bruce Martin
 *
 */
public class ArrayListNode<TN extends TreeNode> implements TreeNode {


	protected final List<TN> EMPTY_LIST = Collections.<TN>emptyList();


	protected final TN parent;
	protected final String name;
	protected List<TN> children = EMPTY_LIST;


	public ArrayListNode(TN parent, String name) {
		super();
		this.parent = parent;
		this.name = name;
	}

	
	public void addChild(TN child) {
		if (children == EMPTY_LIST) {
			children = new ArrayList<TN>();
		}
		children.add(child);
	}

	@Override
	public TN getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return ! isLeaf();
	}

	@Override
	public boolean isLeaf() {
		return children == EMPTY_LIST;
	}


	@Override
	public String toString() {
		return name;
	}
	

	@Override
	public Enumeration<TN> children() {
		return Collections.enumeration(children);
	}
}
