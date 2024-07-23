package com.github.bmTas.recedui.fileFields.zcopy;

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
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.styles.IStyle;

public class UiFileSelectionList extends UiSelectionList<Path> {



	public UiFileSelectionList(
			IStyle style, JComponent parentComponent, boolean focusable, 
			IListPopupActions<Path> completeActions) {
		this(style, parentComponent, focusable, null, completeActions);
	}


	public UiFileSelectionList(
			IStyle style, JComponent parentComponent, boolean focusable, 
			JPanel favouritePnls,
			IListPopupActions<Path> completeActions) {
		super(style, parentComponent, focusable, favouritePnls, completeActions);

	}


	protected void setupList(JWindow window) {
		
		super.setupList(window);
		list.setCellRenderer(new ListCellRenderer<Path>() {
			@Override 
			public Component getListCellRendererComponent(JList<? extends Path> list, Path value, int index,
					boolean isSelected, boolean cellHasFocus) {
				
				String lblStr = "";
				String tip = "";
				if (value != null) {
					lblStr =  value.getFileName() == null ? "" : value.getFileName().toString();
					tip = value.toString();
				}
				
				JLabel lbl = new JLabel(
							lblStr,
							IconManager.ICONS.iconForFile(value), 
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
