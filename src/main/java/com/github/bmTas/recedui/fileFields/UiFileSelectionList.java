package com.github.bmTas.recedui.fileFields;

import java.awt.Component;
import java.nio.file.Path;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;

import com.github.bmTas.recedui.common.UiSelectionList;
import com.github.bmTas.recedui.def.common.IListPopupActions;
import com.github.bmTas.recedui.def.file.IFileListItem;
import com.github.bmTas.recedui.def.file.IGetPath;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.styles.IStyle;

public class UiFileSelectionList extends UiSelectionList<IFileListItem> {



	public UiFileSelectionList(
			IStyle style, JComponent parentComponent, boolean focusable, 
			IListPopupActions<IFileListItem> completeActions) {
		this(style, parentComponent, focusable, null, completeActions);
	}


	public UiFileSelectionList(
			IStyle style, JComponent parentComponent, boolean focusable, 
			JPanel favouritePnls,
			IListPopupActions<IFileListItem> completeActions) {
		super(style, parentComponent, focusable, favouritePnls, completeActions);

	}


	protected void setupList(JWindow window) {
		
		super.setupList(window);
		list.setCellRenderer(new ListCellRenderer<IFileListItem>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends IFileListItem> list, IFileListItem value,
					int index, boolean isSelected, boolean cellHasFocus) {
				String lblStr = "";
				String tip = "";
				Path path = null;
				
				if (value != null) {
					lblStr =  value.getDisplayText();
					tip = value.getItemText();
					path = value.getPath();
				}
				
				JLabel lbl = new JLabel(
							lblStr,
							IconManager.ICONS.iconForFile(path), 
							JLabel.LEADING); 
				lbl.setToolTipText(tip);
				return lbl;
			}
		});

	}


//	public static interface IFileListPopupActions extends IListPopupActions<Path> {
//		
//	}

}
