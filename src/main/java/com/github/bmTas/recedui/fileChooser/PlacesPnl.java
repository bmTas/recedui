package com.github.bmTas.recedui.fileChooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.file.IDirectoryChangedListner;
import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.tree.ArrayListNode;
import com.github.bmTas.recedui.tree.LeafNode;
import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xEnvironment.IEnvironmentValues;
import com.github.bmTas.recedui.xlang.BasicUiTranslation;
import com.github.bmTas.recedui.xlang.IUiTranslation;

public class PlacesPnl extends UiStdComponent2<JPanel, JTree> {

	TreeNode last;

	FileSystemView fsv = FileSystemView.getFileSystemView();
	IEnvironmentValues e = Env.getEnvironmentDefaults();

	ArrayListNode<TreeNode>    top = translatedNode(null,"root");
	ArrayListNode<TreeNode>     pc = translatedNode(top, "This Computer");
//	ArrayListNode  mounts = ArrayListNode.translatedNode(top, "Other Mounts");
	
	
	public static ArrayListNode<TreeNode> translatedNode(ArrayListNode<TreeNode> parent, String name) {
		ArrayListNode<TreeNode> r = new ArrayListNode<TreeNode>(parent, BasicUiTranslation.getTrans().convert(IUiTranslation.ST_MESSAGE, name));
		if (parent != null) {
			parent.addChild(r);
		}
		return r;
	}
	
	private ArrayList<IDirectoryChangedListner> listners = new ArrayList<IDirectoryChangedListner>(4);

	
	public PlacesPnl(IBasicList recentDirs, IBasicList favDirs) {
		super(StyleManager.EMPTY_STYLE, new JPanel(new BorderLayout()), new JTree());
		
		@SuppressWarnings("serial")
		DefaultTreeCellRenderer render = new DefaultTreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
					boolean leaf, int row, boolean hasFocus) {
				
				Component r = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				
				if (value instanceof GetIcon) {
					Icon icon = ((GetIcon) value).getIcon();
					if (icon != null) {
						super.setIcon(icon);
					}
				} else if (leaf && value instanceof GetDirectory 
				&&  Paths.get(((GetDirectory) value).getDirectoryName()).getParent() == null) {
					super.setIcon(IconManager.ICONS.drive.icon());				
				}
				return r;
			}
			
		};
		Icon folderIcon = IconManager.ICONS.folderIcon();
		render.setLeafIcon(folderIcon);
		
		addDir(pc, "home", new File(Env.USER_HOME), IconManager.ICONS.folderHome.icon());
		addDir(pc, "Documents", e.getDocumentDirectory(), IconManager.ICONS.folderDocuments.icon());
		addDir(pc, "Desktop",  e.getDesktopDirectory(), folderIcon);
		
		loadPaths(pc, e.getStandardMountPoints());
		loadPaths(pc, e.getOtherMountPoints());
		
		if (favDirs != null) {
			DirListNode n = new DirListNode(top, "Favourites", favDirs, IconManager.ICONS.folderFavorites.icon());
			if (n.getChildCount() > 0) {
				last = n.getChildAt(0);
			}
		}
		if (recentDirs != null) {
			new DirListNode(top, "Recent", recentDirs, IconManager.ICONS.documentOpenRecent.icon());
		}
		
		

		//ListNode recent = ListNode.translatedNode(top, "Recent");
		DefaultTreeModel treeModel = new DefaultTreeModel(top);

		JTree tree = super.component;
		tree.setModel(treeModel);
		tree.setRootVisible(false);
		tree.setCellRenderer(render);
		if (last != null) {
			TreeNode parent = last.getParent();
			TreeNode[] nodes = {top, parent, last};
			tree.scrollPathToVisible(new TreePath(nodes));
			//tree.setSelectionPath(treePath);
			
			if (parent.getChildCount() + pc.getChildCount() < 23 && pc.getChildCount() > 0) {
				TreeNode[] nodes1 = {top, pc, pc.getChildAt(0)};
				tree.scrollPathToVisible(new TreePath(nodes1));
			}
		}
		tree.addMouseListener(new MouseAdapter() {
			long lt = 0;
			@Override
			public void mousePressed(MouseEvent e) {
				long t = e.getWhen();
				if (lt == 0 || t -lt > 50) {
					Point point = e.getPoint();
					JTree tree = PlacesPnl.super.component;
					TreePath treePath = tree.getClosestPathForLocation(point.x, point.y);
					Object node = treePath.getLastPathComponent();
					if (node instanceof GetDirectory) {
						File file = new File(((GetDirectory) node).getDirectoryName());
						
						for (IDirectoryChangedListner l : listners) {
							l.directoryChanged(file);
						}
					}
					e.consume();
				}
			}
		});
//		tree.addTreeSelectionListener(new TreeSelectionListener() {		
//			@Override public void valueChanged(TreeSelectionEvent e) {
//				TreePath treePath = e.getNewLeadSelectionPath();
//				Object node = treePath.getLastPathComponent();
//				if (node instanceof GetDirectory) {
//					File file = new File(((GetDirectory) node).getDirectoryName());
//					
//					for (IDirectoryChangedListner l : listners) {
//						l.directoryChanged(file);
//					}
//				}
//			}
//		});
		
		super.container.add(new JScrollPane(tree), BorderLayout.CENTER);
	}
	
	private void addDir(ArrayListNode<TreeNode> parent, String name, File folder, Icon icon) {
		if (folder != null && folder.exists()) {
			//System.out.println(folder.getAbsolutePath() + " " + folder.isDirectory() + " " + folder.getPath());
			if (folder.isDirectory()) {
				last = new MountLeafNode(parent, name, folder.getAbsolutePath(), icon);
			}
		}
	}
	
		//DefaultMutableTreeNode pc = new DefaultMutableTreeNode("pc");
	//DefaultMutableTreeNode recent = new DefaultMutableTreeNode("Recent");
	
	//DefaultMutableTreeNode last;
	
	private void loadPaths(ArrayListNode<TreeNode> parent, File[] paths) {
		String s;
		
		// for each pathname in pathname array
		if (paths != null) {
			for(File path:paths) {
				s = fsv.getSystemDisplayName(path);
				if (s != null && s.length() > 0) {
					last = new MountLeafNode(parent, s, path.getAbsolutePath(), IconManager.ICONS.drive.icon());
				}
			}
		}
	}

	
	public void addDirectoryChangedListner(IDirectoryChangedListner l) {
		listners.add(l);
	}

		
	private static interface GetIcon {
		Icon getIcon();
	}

	private static interface GetDirectory {
		String getDirectoryName();
	}
	
//	private static class MountNode extends ArrayListNode implements GetDirectory {
//		
//		String directoryName;
//		public MountNode(ArrayListNode parent, String name, String directoryName) {
//			super(parent, BasicUiTranslation.getTrans().convert(IUiTranslation.ST_MESSAGE, name));
//			this.directoryName = directoryName;
//			
//			if (parent != null) {
//				parent.addChild(this);
//			}
//
//		}
//		
//		public final String getDirectoryName() {
//			return directoryName;
//		}
//	}
//	
	
	private static class MountLeafNode extends LeafNode implements GetDirectory, GetIcon {
		
		final String directoryName;
		final Icon icon;
		public MountLeafNode(ArrayListNode<TreeNode> parent, String name, String directoryName, Icon icon) {
			super(parent, BasicUiTranslation.getTrans().convert(IUiTranslation.ST_MESSAGE, name));
			this.directoryName = directoryName;
			this.icon = icon;
			
			if (parent != null) {
				parent.addChild(this);
			}
		}
		
		public final String getDirectoryName() {
			return directoryName;
		}

		@Override
		public Icon getIcon() {
			return icon;
		}
		
		
	}

	private static class DirListNode implements TreeNode, IListChangeListner, GetIcon {
		final TreeNode parent;
		final String name;
		final IBasicList dirList;
		final Icon icon;
		
		ArrayList<DirLeafNode> children;
		
		
		public DirListNode(ArrayListNode<TreeNode> parent, String name, IBasicList dirList, Icon icon) {
			super();
			this.parent = parent;
			this.name = name;
			this.dirList = dirList;
			this.icon = icon;
			
			parent.addChild(this);
			dirList.addChangeListner(this);
		}

		@Override
		public Icon getIcon() {
			return icon;
		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		public void listChanged(IBasicList list) {
			children = null;
		}

		@Override
		public TreeNode getChildAt(int childIndex) {
			return getChildren().get(childIndex);
		}

		@Override
		public int getChildCount() {
			return dirList.getItemList().size();
		}

		@Override
		public TreeNode getParent() {
			return parent;
		}

		@Override
		public int getIndex(TreeNode node) {
			String dir;
			if (node instanceof GetDirectory && (dir = ((GetDirectory) node).getDirectoryName()) != null) {
				ArrayList<DirLeafNode> theChildren = getChildren();
				
				boolean isWindows = Env.IS_WINDOWS;
	
				for (int i = 0; i < theChildren.size(); i++) {
					String directoryName = theChildren.get(i).directoryName;
					if (isWindows && dir.equalsIgnoreCase(directoryName)) {
						return i;
					} else if (dir.equals(directoryName)) {
						return i;
					}
				}
			}
			return 0;
		}

		@Override
		public boolean getAllowsChildren() {
			return ! isLeaf();
		}

		@Override
		public boolean isLeaf() {
			return false;
		}
		
		

		@Override
		public Enumeration<? extends TreeNode> children() {
			return  Collections.enumeration(getChildren());
		}
		
		ArrayList<DirLeafNode> getChildren() {
			if (children == null) {
				synchronized (this) {
					List<IListItem> itemList = dirList.getItemList();
					children = new ArrayList<DirLeafNode>(itemList.size());
					
					for (IListItem item : itemList) {
						Path path = Paths.get(item.getItemText());
						if (path != null && path.getFileName() != null) {
							children.add(new DirLeafNode(this, path.getFileName().toString(), path.toString()));
						}
					}
				}
			}
			return children;
		}
	}
	
	private static class DirLeafNode extends LeafNode implements TreeNode, GetDirectory {

		final String directoryName;
		public DirLeafNode(TreeNode parent, String name, String directoryName) {
			super(parent, name);
			this.directoryName = directoryName;
		}
		
		public final String getDirectoryName() {
			return directoryName;
		}		
	}
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//     	BasicItemList rf = new BasicItemList("File",
//    			"/home/bruce/work/c1.xml", "/home/bruce/work/c2.xml",
//    			"/home/bruce/work/c1/cpy1.xml", "/home/bruce/work/c2/cpy1.xml");
//      	BasicItemList rd = new BasicItemList("Directory",
//    			"/home/bruce/work/", "/home/bruce/work/c1",
//    			"/home/bruce/work/c2", "/home/bruce/.RecordEditor/HSQLDB/SampleFiles",
//    			"/home/bruce/.RecordEditor/HSQLDB/SampleFiles/Xml");
//    	BasicItemList ud = new BasicItemList("Directory",
//    			"/home/bruce/work/c2", "/home/bruce/.RecordEditor/HSQLDB/SampleFiles",
//    			"/home/bruce/work/", "/home/bruce/work/c1",
//    			"/home/bruce/.RecordEditor/HSQLDB/SampleFiles/Xml");
//
//    	PlacesPnl places = new PlacesPnl(rd, ud);
//     	JFrame f = new JFrame();
//    	JPanel p = new JPanel(new BorderLayout());
//    	
//    	p.add(new JPanel(), BorderLayout.NORTH);
//    	p.add(places.getJContainer(), BorderLayout.CENTER);
//    	
//    	p.add(new JPanel(), BorderLayout.SOUTH);
//    	//p.add(new JTextField(25));
//
//    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	f.getContentPane().add(p);
//		f.pack();
//		f.setVisible(true);
//
//	}

}
