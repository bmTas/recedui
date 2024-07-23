package com.github.bmTas.recedui.tree;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

public class LeafNode implements TreeNode {


	private final TreeNode parent;
	private final String name;
	public LeafNode(TreeNode parent, String name) {
		super();
		this.parent = parent;
		this.name = name;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Enumeration<TreeNode> children() {
		return null;
	}

	@Override
	public String toString() {
		return name;
	}
}
