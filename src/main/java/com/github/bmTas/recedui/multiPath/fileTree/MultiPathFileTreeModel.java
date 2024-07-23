package com.github.bmTas.recedui.multiPath.fileTree;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.github.bmTas.recedui.multiPath.fileTree.IGetFileType.FileType;
import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xEnvironment.IEnvironmentValues;


public class MultiPathFileTreeModel implements TreeModel, INotifyFileTreeChanged {

	private ArrayList<TreeModelListener> listners = new ArrayList<TreeModelListener>(5);
	private final MultiPathFileNode root;
	
	public static MultiPathFileTreeModel newModel() {
		return new MultiPathFileTreeModel(new MultiPathFileNode(null, Env.IS_WINDOWS, null));
	}


	public MultiPathFileTreeModel(MultiPathFileNode root) {
		this.root = root;
		
		if (root.isEmptyPaths()) {
			IEnvironmentValues environmentDefaults = Env.getEnvironmentDefaults();
			ArrayList<MultiPathFileNode> nodes = new ArrayList<MultiPathFileNode>();
			
			addFiles(root, nodes, environmentDefaults.getStandardMountPoints());
			addFiles(root, nodes, environmentDefaults.getOtherMountPoints());
			
			root.setChildren(nodes.toArray(new MultiPathFileNode[nodes.size()]));
		}
	}
	
	private void addFiles(MultiPathFileNode parent, ArrayList<MultiPathFileNode> nodes, File[] files) {
		
		if (files != null) {
			for (File f : files) {
				parent.addChild( new Path[] {f.toPath()});
			}
		}
	}
	
	public void expandTree() {
		root.loadChildTree();
	}
	

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((MultiPathFileNode) parent).getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((MultiPathFileNode) parent).getChildCount();
	}

	@Override
	public boolean isLeaf(Object node) {
		return  ((MultiPathFileNode) node).getFileType() == FileType.FILE; 
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return  ((MultiPathFileNode) parent).getIndex((MultiPathFileNode)child);
	}
	
	public MultiPathFileNode getTreePath(File f) {
		Path p = null;
		while (f != null) {
			try {
				p = f.toPath();
				break;
			} catch (Exception e) {
				try {
					f = f.getParentFile();
				} catch (Exception e1) {
					return null;
				}
			}
		}
		return p == null ? null : getTreePath(p);
	}
	
	public MultiPathFileNode getTreePath(Path f) {
		return root.getChildNode(f);
	}

	@Override
	public void fireStrucureChanged(MultiPathFileNode node) {
		TreeModelEvent e = new TreeModelEvent(this, node.treePath());
		for (TreeModelListener l : listners) {
			l.treeStructureChanged(e);
		}
	}


	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listners.remove(l);
	}

	
//	public static void main(String[] args) {
//		FileTreeModel mdl = newModel();
//		JTree tree = new JTree(mdl);
//		JFrame frame = new JFrame();
//		
//		//tree.path
//		//tree.setCellRenderer(x);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		frame.getContentPane().add(new JScrollPane(tree));
//		
//		frame.pack();
//		
//		//FileNode treePath = mdl.getTreePath(new File("/home/bruce/work/"));
//		FileNode treePath = mdl.getTreePath(new File("/home/bruce/work/cb2xml/source/cb2xml/src/net/sf/cb2xml"));
//		tree.scrollPathToVisible(treePath.treePath());
//		//tree.setSelectionPath(treePath.treePath());
//		
//		frame.setVisible(true);
//	}

}
