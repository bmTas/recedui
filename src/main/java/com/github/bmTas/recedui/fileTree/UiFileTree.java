package com.github.bmTas.recedui.fileTree;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import com.github.bmTas.recedui.def.component.IUiTree;
import com.github.bmTas.recedui.def.file.IDirectoryChangedListner;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.styles.ITreeStyle;
import com.github.bmTas.recedui.styles.StyleManager;


/**
 * Displays File system in a Tree Structure
 * 
 * @author: Bruce Martin
 *
 */
public class UiFileTree implements IUiTree {

	private final ITreeStyle style;
	private final FileTreeModel treeMdl = FileTreeModel.newModel();
	private final JTree tree = new JTree(treeMdl);
	@SuppressWarnings("serial")
	private final DefaultTreeCellRenderer render = new DefaultTreeCellRenderer() {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			
			Component r = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
			if (value instanceof FileNode && ((FileNode) value).parent == treeMdl.getRoot()) {
				super.setIcon(IconManager.ICONS.drive.icon());				
			}

			return r;
		}
		
	};

	
	private JLabel fieldsLabel;
	
	private ArrayList<IDirectoryChangedListner> listners = new ArrayList<IDirectoryChangedListner>(4);

	public UiFileTree() {
		this(StyleManager.styles().folderTree());
	}
	
	public UiFileTree(ITreeStyle style) {
		super();
		this.style = style;
		
		Icon folderIcon = IconManager.ICONS.folderIcon();
		render.setLeafIcon(folderIcon);
		render.setClosedIcon(folderIcon);
		render.setOpenIcon(folderIcon);
		
		style.register(this);
		style.applyTree(this);
		
		tree.setCellRenderer(render);
		
//		TreeUI ui = tree.getUI();
//		if (ui instanceof BasicTreeUI) {
//			int height = SwingUtils.values().stdIconHeight();
//			int width = (height + 1) / 2;
//			BasicTreeUI treeui = (BasicTreeUI) ui;
//			treeui.setExpandedIcon(new TriangleIcon(ShapePainters.DOWN, Color.GRAY)); //, width, height));
//			treeui.setCollapsedIcon(new TriangleIcon(ShapePainters.RIGHT, Color.GRAY)); //, width, height));
//	   }
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {		
			@Override public void valueChanged(TreeSelectionEvent e) {
				TreePath treePath = e.getNewLeadSelectionPath();
				if (treePath != null) {
					Path filePath = ((FileNode) treePath.getLastPathComponent()).getFilePath();
					File file = filePath.toFile();
					
					for (IDirectoryChangedListner l : listners) {
						l.directoryChanged(file);
					}
				}
			}
		});
		tree.setRootVisible(false);
	}


	@Override
	public ITreeStyle getStyleId() {
		return style;
	}


	@Override
	public JComponent getGuiContainer() {
		return tree;
	}

	@Override
	public JTree getMainComponent() {
		return tree;
	}

	@Override
	public JLabel getFieldsLabel() {
		return fieldsLabel;
	}

	@Override
	public void setFieldsLabel(JLabel label) {
		this.fieldsLabel = label;
	}

	public void showPath(File f) {
		FileNode treePath = treeMdl.getTreePath(f);
		if (treePath != null) {
			tree.scrollPathToVisible(treePath.treePath());
		}
	}
	public void setSelectedPath(File f) {
		FileNode treeNode = treeMdl.getTreePath(f);
		if (treeNode != null) {
			TreePath treePath = treeNode.treePath();
			tree.scrollPathToVisible(treePath);
			tree.setSelectionPath(treePath);
		}
	} 
	
	public void addDirectoryChangedListner(IDirectoryChangedListner l) {
		listners.add(l);
	}

	@Override
	public void setVisible(boolean b) {
		tree.setVisible(b);
	}

	@Override
	public void setToolTipText(String text) {
		tree.setToolTipText(text);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		tree.setBounds(x, y, width, height);
	}
	@Override
	public void setBounds(Rectangle r) {
		tree.setBounds(r);
	}
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		tree.setPreferredSize(preferredSize);
	}
	@Override
	public Dimension getPreferredSize() {
		return tree.getPreferredSize();
	}
	@Override
	public int getX() {
		return tree.getX();
	}
	@Override
	public int getY() {
		return tree.getY();
	}

//	public static void main(String[] args) {
//		UiFileTree fileTree = new UiFileTree();
//		JFrame frame = new JFrame();
//		
//		//tree.path
//		//tree.setCellRenderer(x);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		JScrollPane comp = new JScrollPane(fileTree.getMainComponent());
//		frame.getContentPane().add(comp);
//		
//		comp.setPreferredSize(new Dimension(600, 800));
//		
//		frame.pack();
//		
//		//FileNode treePath = mdl.getTreePath(new File("/home/bruce/work/"));
//		
//		fileTree.showPath(new File("/home/bruce/work/cb2xml/source/cb2xml/src/net/sf/cb2xml"));
//		
//		frame.setVisible(true);
//	}

}
