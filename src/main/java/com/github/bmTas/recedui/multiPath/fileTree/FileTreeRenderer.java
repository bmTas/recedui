package com.github.bmTas.recedui.multiPath.fileTree;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.github.bmTas.recedui.icons.IconManager;

@SuppressWarnings("serial")
public class FileTreeRenderer extends DefaultTreeCellRenderer {
	
	{
		Icon folderIcon = IconManager.ICONS.folderIcon();
		this.setLeafIcon(folderIcon);
		this.setClosedIcon(folderIcon);
		this.setOpenIcon(folderIcon);

	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		
		super.setBackground(backgroundNonSelectionColor);
		super.setOpaque(false);

		Component r = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		
		if (value instanceof IGetFileType) {
			Icon icon;
			switch (((IGetFileType) value).getFileType()) {
			case DRIVE: icon = IconManager.ICONS.drive.icon();		break;
			case DIRECTORY: icon = IconManager.ICONS.folderIcon();	break;
			default:
				icon = IconManager.ICONS.smallBlankIcon;
				r.setBackground(Color.CYAN);
				super.setOpaque(true);
			}
			super.setIcon(icon);
		}

		return r;// super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
	}

}
