package com.github.bmTas.recedui.fileChooser;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.github.bmTas.recedui.def.file.IFileDirectoryChangedListner;
import com.github.bmTas.recedui.def.table.IUiTable;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.table.UiTable;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;


/**
 * Holds data For the FileChooser detail display
 * 
 * @author Bruce Martin
 *
 */
public class FolderFiles {

	private ArrayList<FileDetails> fileDetails = new ArrayList<FileDetails>();
	
	FileTableMdl fileTblMdl  = new FileTableMdl();
	FileListMdl  fileListMdl = new FileListMdl();
	
	IUiTable fileTbl = new UiTable(StyleManager.styles().fcFileTable(), new JTable(fileTblMdl));
	JList fileList = new JList(fileListMdl);
	
	protected boolean directoryAllowed = false;
	private final IFileDirectoryChangedListner fileChg;

	public FolderFiles(IFileDirectoryChangedListner listner) {
		this.fileChg = listner;
		JTable fileJTbl = fileTbl.getMainComponent();
		fileJTbl.getTableHeader().addMouseListener(new HeaderSort());
		
		TableColumnModel columnModel = fileJTbl.getColumnModel();
		DefaultTableCellRenderer sizeRender = new DefaultTableCellRenderer();
		sizeRender.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		//sizeRender.setBackground(c);
		
		fileJTbl.setName("FC_fileTbl");
		fileList.setName("FC_fileList");
		
		columnModel.getColumn(FileTableMdl.FILE_NAME_IDX).setCellRenderer(new FileNameRender());
		columnModel.getColumn(FileTableMdl.SIZE_IDX).setCellRenderer(sizeRender);
	}
	
	public void addListner() {
		MouseAdapter clickListner = new MouseAdapter() {
			@Override public void mouseReleased(MouseEvent e) {
				
				FileDetails fDetails;
				int row;
				Point p = e.getPoint();
				if ( e.getComponent() instanceof JTable) {
					row = fileTbl.getMainComponent().rowAtPoint(p);
				} else {
					row = fileList.locationToIndex(p);
				}
				fDetails = fileDetails.get(row);

				File file = new File(fDetails.fullname);
				if (fDetails.isDirectory && (e.getClickCount() == 2 || !directoryAllowed)) {
					if (e.getClickCount() == 2 && ! e.isConsumed()) {
						e.consume();
						fileChg.directoryChanged(file);
//						setDirectory(file, SOURCE_UNKNOWN);
					}
				} else {
					fileChg.fileChanged(file);
//					fileField.setSelectedFile(file, false);
//					fireFileChanged(file);
				}
			}
		};
		fileTbl.getMainComponent().addMouseListener(clickListner);
		fileList.addMouseListener(clickListner);

	}

	public void loadFileDetails(Iterator<Path> pathIterator) {
		fileDetails.clear();
		for (int i = 0; pathIterator.hasNext(); i++) {
			fileDetails.add(new FileDetails(pathIterator.next()));
		}
		Collections.sort(fileDetails);
		
		if (fileTbl.getMainComponent().getParent() != null) {
			fileTblMdl.fireTableDataChanged();
			SwingUtils.calcColumnWidths(fileTbl.getMainComponent(), 0);
		} else {
			fileListMdl.fireDataChanged();
		}
	}
	
	
	private static String[] FILE_TABLE_NAMES = {
			"File Name", "Extension", "Changed", "Size"	
	};


	@SuppressWarnings("serial")
	public class FileTableMdl extends AbstractTableModel {

		public static final int FILE_NAME_IDX = 0;
		public static final int EXTENSION_IDX = 1;
		private static final int CHANGED_IDX = 2;
		public static final int SIZE_IDX = 3;

		@Override
		public int getRowCount() {
			return fileDetails.size();
		}

		@Override
		public int getColumnCount() {
			return FILE_TABLE_NAMES.length;
		}

		
		@Override
		public String getColumnName(int column) {
			return FILE_TABLE_NAMES[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			FileDetails row = fileDetails.get(rowIndex);
			row.loadDetails();
			switch (columnIndex) {
			case FILE_NAME_IDX: return row.fullname;
			case EXTENSION_IDX: return row.extension;
			case CHANGED_IDX: return row.changedStr;
			case SIZE_IDX:
				if (row.size > 0) {
					return row.sizeStr;
				}
			}
			return "";
		}
	};
	
	@SuppressWarnings("serial")
	public class FileListMdl extends AbstractListModel {

		@Override
		public int getSize() {
			return fileDetails.size();
		}

		@Override
		public Object getElementAt(int index) {
			return fileDetails.get(index);
		}
		
		public void fireDataChanged() {
			fireContentsChanged(this, 0, fileDetails.size());
		}
	}


	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yy HH:mm");
	public static class FileDetails implements Comparable<FileDetails> {
		final boolean isDirectory;
		final String fullname;
		String extension;
		boolean getAttributes = true;
		long changed;
		String changedStr;
		Long size=(long) 0;
		String sizeStr = "";
		
		FileDetails(Path path) {
			this.fullname = path.toString();
			this.isDirectory = Files.isDirectory(path);
			
			int p = fullname.lastIndexOf('.');
			
			String ext = "";
			if (p >= 0) {
				ext = fullname.substring(p+1);
			}
			extension = ext;
		}
		
		void loadDetails() {
			if (getAttributes) {
				File f = new File(fullname);
				changed = f.lastModified();
				changedStr = dateFormat.format(new Date(changed));
				if (isDirectory) {
					extension = "";
				} else {
					size = f.length();
					if (size > 10 * 1024 * 1024) {
						long l = (size + (long)500000) >> 24;
						sizeStr = l + " MB";
					} else if (size > 10 * 1024) {
						long l = (size + (long)500) >> 12;
						sizeStr = l + " KB";
					} else {
						sizeStr = size + "   ";
					}
				}
				getAttributes = false;
			}
		}

		@Override
		public int compareTo(FileDetails o) {
			if (isDirectory == o.isDirectory) {
				return fullname.compareToIgnoreCase(o.fullname);
			} 
			return isDirectory ? -1 : 1;
		}
	}
	
	
	private static class FileNameRender implements TableCellRenderer {
		JLabel label = new JLabel();

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			String v = "";
			Icon icon = null;
			if (value != null) {
				Path path = Paths.get(value.toString());
				icon = IconManager.ICONS.iconForFile(path);
				v = path.getFileName().toString();
			}
			
			if (isSelected || table.getBackground() == null) {
				SwingUtils.values().setTableCellBackGround(label, table, row, isSelected);
			} else {				
				label.setOpaque(true);
				label.setBackground(table.getBackground());
			}
			
			label.setIcon(icon);
			label.setText(v);
			
			return label;
		}
		
	}

	private final class HeaderSort extends MouseAdapter {

		boolean[] ascending = {true, true, true, true};

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 2 && ! e.isConsumed()) {
				JTableHeader header = (JTableHeader) e.getSource();
				int col = header.columnAtPoint(e.getPoint());
				int mult =  ascending[col] ? 1 : -1;

				Comparator<FileDetails> comparator = new ComparatorFileDetails(mult) { 
					@Override public int compareFile(FileDetails o1, FileDetails o2) {
						return o1.compareTo(o2);
					}  
				};
				switch (col) {
				case FileTableMdl.FILE_NAME_IDX:
					break;
				case FileTableMdl.EXTENSION_IDX:
					comparator = new ComparatorFileDetails(mult) { 
						@Override public int compareFile(FileDetails o1, FileDetails o2) {
							return o1.extension.compareTo(o2.extension);
						}  
					};
					break;
				case FileTableMdl.SIZE_IDX:
					comparator = new ComparatorFileDetails(mult) { 
						@Override public int compareFile(FileDetails o1, FileDetails o2) {
							return Long.compare(o1.size, o2.size);
						}  
					};
					break;
				case FileTableMdl.CHANGED_IDX:
					comparator = new ComparatorFileDetails(mult) { 
						@Override public int compareFile(FileDetails o1, FileDetails o2) {
							return (new Date(o1.changed)).compareTo(new Date(o2.changed));
						}  
					};
					break;
				}
				Collections.sort(fileDetails, comparator);
				ascending[col] = ! ascending[col];
			}
		}
	}

	private static abstract class ComparatorFileDetails implements Comparator<FileDetails> {
		private final int multBy;

		public ComparatorFileDetails(int mult) {
			this.multBy = mult;
		}
		
		public final int compare(FileDetails o1, FileDetails o2) {
			int ret = 1;
			if (o1.isDirectory == o2.isDirectory) {
				ret = multBy * compareFile(o1, o2);
			} else if (o1.isDirectory) {
				ret = -1;
			}
			return ret;
		}
		
		public abstract int compareFile(FileDetails o1, FileDetails o2);

	}
}
