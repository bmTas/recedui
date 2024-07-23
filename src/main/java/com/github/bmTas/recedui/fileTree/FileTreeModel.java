package com.github.bmTas.recedui.fileTree;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xEnvironment.IEnvironmentValues;


public class FileTreeModel implements TreeModel, INotifyFileTreeChanged {

	private ArrayList<TreeModelListener> listners = new ArrayList<TreeModelListener>(5);
	private final FileNode root;
	
	public static FileTreeModel newModel() {
		return new FileTreeModel(new FileNode((Path) null));
	}


	public FileTreeModel(FileNode root) {
		this.root = root;
		
		if (root.getFilePath() == null) {
			IEnvironmentValues environmentDefaults = Env.getEnvironmentDefaults();
			ArrayList<FileNode> nodes = new ArrayList<FileNode>();
			
			addFiles(root, nodes, environmentDefaults.getStandardMountPoints());
			addFiles(root, nodes, environmentDefaults.getOtherMountPoints());
			
			root.setChildren(nodes.toArray(new FileNode[nodes.size()]));
		}
	}
	
	private void addFiles(FileNode parent, ArrayList<FileNode> nodes, File[] files) {
		
		if (files != null) {
			for (File f : files) {
				nodes.add(new FileNode(parent, f.toPath()));
			}
		}
	}
	

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((FileNode) parent).get(index, this);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((FileNode) parent).count(this);
	}

	@Override
	public boolean isLeaf(Object node) {
		//System.out.print("isLeaf ");
		return false; // getChildCount(node) == 0;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return  ((FileNode) parent).getIndex((FileNode)child);
	}
	
	public FileNode getTreePath(File f) {
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
	
	public FileNode getTreePath(Path f) {
		ArrayList<Path> pathList = new ArrayList<Path>();
		Path current = f;
		
		while (current != null) {
			pathList.add(current);
			current = current.getParent();
		}
		
		FileNode node = root;
		for (int i = pathList.size() - 1; i >= 0 && node != null; i--) {
			//System.out.println(" ~~> " + node.getFilePath());
			node = node.getChildForFile(pathList.get(i), this);			
		}
		return node;
	}

	@Override
	public void fireStrucureChanged(FileNode node) {
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
