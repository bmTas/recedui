package com.github.bmTas.recedui.fileChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.github.bmTas.recedui.def.file.FileChooserDefs;
import com.github.bmTas.recedui.icons.IconManager;


/**
 * This class controls the File Chooser filay display.
 * It will change display from list, icon, details mode
 * as the radio buoons are selected.
 * 
 * @author Bruce Martin
 *
 */
public class FileDetailsPane implements ActionListener {

	protected final JScrollPane fileScrollPane = new JScrollPane();
	final FolderFiles folderFiles; 
//	protected final AbstractTableModel fileTblMdl ;
//	protected final AbstractListModel fileListModel ;
//	protected final IUiTable fileTbl;
//	protected final JList fileList;

//	protected final JRadioButtonMenuItem listMI = new JRadioButtonMenuItem("List");
//	protected final JRadioButtonMenuItem smallIconMI = new JRadioButtonMenuItem("Small Icons");
//	protected final JRadioButtonMenuItem iconMI = new JRadioButtonMenuItem("Icons");
//	protected final JRadioButtonMenuItem detailMI = new JRadioButtonMenuItem("Details");
	
	protected final JRadioButtonMenuItem[] buttons;
	
	private final FileChooserDefs.FileChooserDetailDisplay[] displayTypes;
	
	private boolean lastDisplayWasTable;
	private int lastIndex=-1;
	
	private final ListRender  listRendor = new ListRender();
	private final IconRender  iconRender = new IconRender();
	private final TilesRender tilesRender = new TilesRender();
	
	public FileDetailsPane(FileChooserDefs.FileChooserDetailDisplay displayType, FolderFiles fileList) {
		super();
		this.folderFiles = fileList;
		
		this.displayTypes = FileChooserDefs.FileChooserDetailDisplay.values();
		this.buttons= new JRadioButtonMenuItem[displayTypes.length];
		
		ButtonGroup bg = new ButtonGroup();
		int index = displayTypes.length - 1;
		for (int idx = 0;idx < displayTypes.length; idx++) {
			buttons[idx] = new JRadioButtonMenuItem(displayTypes[idx].screenName) ;
			bg.add(buttons[idx]);
			
			if (displayType == displayTypes[idx]) {
				index = idx;
			}
		}
		
		buttons[index].setSelected(true);
		//lastWasTable = displayTypes[index].useTable;
		
		fileScrollPane.getViewport().setOpaque(true);
		fileScrollPane.getViewport().setBackground(Color.WHITE);

		setupDisplay(true);
		for (JRadioButtonMenuItem mi : buttons) {
			mi.addActionListener(this);
		}
	}
	
	public void addListOptions(JMenu menu) {
		for (JRadioButtonMenuItem mi : buttons) {
			menu.add(mi);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		setupDisplay(false);
	}


	@SuppressWarnings("unchecked")
	private void setupDisplay(boolean init) {
		int index = -121;
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].isSelected()) {
				index = i;
			}
		}
		if (index < 0 || index == lastIndex) { return; }
		
		FileChooserDefs.FileChooserDetailDisplay display = displayTypes[index];
		switch (display) {
		case list:
			folderFiles.fileList.setCellRenderer(listRendor);
			folderFiles.fileList.setLayoutOrientation(JList.VERTICAL_WRAP);
//			fileScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//			fileScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			folderFiles.fileList.setVisibleRowCount(-1);
			break;
		case smallIcon:
			folderFiles.fileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			folderFiles.fileList.setCellRenderer(listRendor);
			folderFiles.fileList.setVisibleRowCount(folderFiles.fileList.getVisibleRowCount());
			
			
//			fileScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//			fileScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			break;
		case tiles:
			folderFiles.fileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			folderFiles.fileList.setCellRenderer(tilesRender);
			folderFiles.fileList.setVisibleRowCount(folderFiles.fileList.getVisibleRowCount());

			break;
		case icon:
			folderFiles.fileList.setCellRenderer(iconRender);
			folderFiles.fileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			folderFiles.fileList.setVisibleRowCount(folderFiles.fileList.getVisibleRowCount());
			break;
		case details:	
			break;
		}
		folderFiles.fileList.setVisibleRowCount(-1);
		
		if (display.useTable) {
			folderFiles.fileTblMdl.fireTableDataChanged();
		} else {
			folderFiles.fileListMdl.fireDataChanged();
		}
		if (init || (lastDisplayWasTable != display.useTable)) {
			if (display.useTable) {
				fileScrollPane.setViewportView(folderFiles.fileTbl.getMainComponent());
			} else {
				fileScrollPane.setViewportView(folderFiles.fileList);
			}
			lastDisplayWasTable = display.useTable;
		}
	}

	@SuppressWarnings("rawtypes")
	private static class ListRender implements ListCellRenderer {
		JLabel display = new JLabel();
		{
			display.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 2));			
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			
			if (value instanceof FolderFiles.FileDetails) {
				FolderFiles.FileDetails val = (FolderFiles.FileDetails) value;
				Path path = Paths.get(val.fullname);
				display.setIcon(IconManager.ICONS.iconForFile(path));
				display.setText(path.getFileName().toString());
			} else {
				display.setIcon(null);
				display.setText(value == null ? "null" : value.toString());
			}
			return display;
		}
	}
	

	@SuppressWarnings("rawtypes")
	private static class TilesRender implements ListCellRenderer {
		JLabel display = new JLabel();
		{
			display.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 2));			
		}
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			
			if (value instanceof FolderFiles.FileDetails) {
				FolderFiles.FileDetails val = (FolderFiles.FileDetails) value;
				Path path = Paths.get(val.fullname);
				
				val.loadDetails();
				display.setIcon(IconManager.ICONS.largeIconForFile(path));
				display.setText(
						"<html>" + path.getFileName().toString() + "<br/><br>"
								 + "<font color=\"NAVY\">Size: " + val.sizeStr + "</font><br></html>"
						);
			} else {
				display.setIcon(null);
				display.setText(value == null ? "null" : value.toString());
			}
			return display;
		}
	}

	@SuppressWarnings("rawtypes")
	private static class IconRender implements ListCellRenderer {
		JLabel display = new JLabel();
		
		IconRender() {
			display.setVerticalTextPosition(JLabel.BOTTOM);
			display.setHorizontalTextPosition(JLabel.CENTER);
			display.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			
			if (value instanceof FolderFiles.FileDetails) {
				FolderFiles.FileDetails val = (FolderFiles.FileDetails) value;
				Path path = Paths.get(val.fullname);
				
				String s = path.getFileName().toString();
				if (s.length() > 30) {
					s = s.substring(0, 27) + "...";
				}
				StringBuilder b = new StringBuilder(40);
				String sep = "";
				
				while (s.length() > 10) {
					b.append(sep).append(s.substring(0, 10));
					
					sep = "<br/>";
					s = s.substring(10);
				}
				b.append(sep).append(s);
				
				display.setIcon(IconManager.ICONS.largeIconForFile(path));
				display.setText("<html>" + b.toString() + "</html>");
			} else {
				display.setIcon(null);
				display.setText(value == null ? "null" : value.toString());
			}
			return display;
		}
	}

}
