package com.github.bmTas.recedui.tree;

import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import com.github.bmTas.recedui.styles.ITreeStyle;

public class TreeParamBuilder {
	
	public static ITreeParamBuilder1 newBuilder() { return new Builder(); }

	public interface ITreeParamBuilder {

		/**
		 * @param style the style to set
		 */
		ITreeParamBuilder setStyle(ITreeStyle style);

		/**
		 * Set the Cell-Renderer
		 */
		ITreeParamBuilder setTreeCellRenerer(TreeCellRenderer treeCellRenerer);
		
		/**
		 * Set The Tree Cell editor
		 * 
		 * @param treeCellEditor
		 * @return
		 */
		ITreeParamBuilder setTreeCellEditor(TreeCellEditor treeCellEditor);

		/**
		 * 
		 * @return cast this builder as a Ui-Tree parameter
		 */
		ITreeParam asParam();

		/**
		 * Create a new New Tree Parameter
		 * @return Tree-Parameter
		 */
		ITreeParam buildParam();

		/**
		 * Create a new Ui-Tree 
		 * @return
		 */
		UiTree buildTree();
		
		/**
		 * Build a scroll tree
		 */
		UiScrollTree buildScrollTree();

		/**
		 * @param tree the tree to set
		 */
		ITreeParamBuilder setTreeModel(TreeModel treeMode);

		/**
		 * @param tree the tree to set
		 */
		ITreeParamBuilder setTree(JTree tree);

	}
	

	public interface ITreeParamBuilder1 {

		/**
		 * @param tree the tree to set
		 */
		ITreeParamBuilder setTreeModel(TreeModel treeMode);

		/**
		 * @param tree the tree to set
		 */
		ITreeParamBuilder setTree(JTree tree);
	}

	static class Builder implements ITreeParamBuilder,  ITreeParamBuilder1, ITreeParam {
		ITreeStyle style;
		JTree tree;
		TreeCellRenderer treeCellRenerer;
		TreeCellEditor treeCellEditor;
		
		/**
		 * @return the style
		 */
		public ITreeStyle getStyle() {
			return style;
		}
		/**
		 * @param style the style to set
		 */
		@Override
		public ITreeParamBuilder setStyle(ITreeStyle style) {
			this.style = style;
			return this;
		}
		
		/**
		 * @return the tree
		 */
		public JTree getTree() {
			JTree jTree = tree == null ? new JTree() : tree;
			if (treeCellRenerer != null) {
				tree.setCellRenderer(treeCellRenerer);
			}
			if (treeCellEditor != null) {
				tree.setCellEditor(treeCellEditor);
			}
			//tree.setC
			return jTree;
		}
		
		/**
		 * @param tree the tree to set
		 */
		@Override
		public Builder setTree(JTree tree) {
			this.tree = tree;
			return this;
		}

		
		/**
		 * @param tree the tree to set
		 */
		@Override
		public Builder setTreeModel(TreeModel treeMode) {
			this.tree = new JTree(treeMode);
			return this;
		}

			
		/**
		 * @param treeCellRenerer the treeCellRenerer to set
		 */
		@Override
		public Builder setTreeCellRenerer(TreeCellRenderer treeCellRenerer) {
			this.treeCellRenerer = treeCellRenerer;
			return this;
		}
		/**
		 * @param treeCellEditor the treeCellEditor to set
		 */
		@Override
		public Builder setTreeCellEditor(TreeCellEditor treeCellEditor) {
			this.treeCellEditor = treeCellEditor;
			return this;
		}
		@Override
		public UiTree buildTree() {
			return new UiTree(this);
		}


		@Override
		public UiScrollTree buildScrollTree() {
			return new UiScrollTree(this);
		}

		@Override
		public ITreeParam buildParam() {
			Builder ret = new Builder();
			
			ret.style = this.style;
			ret.tree = tree;
			
			return ret;
		}


		@Override
		public ITreeParam asParam() {
			return this;
		}

	}
}
