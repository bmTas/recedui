package com.github.bmTas.recedui.multiPath.fileTree;

import java.nio.file.attribute.BasicFileAttributes;

import com.github.bmTas.recedui.swing.treeTable.AbstractTreeTableModel;
import com.github.bmTas.recedui.swing.treeTable.ITreeTableModel;

public abstract class AbstractMultiPathTreeTableModel extends AbstractTreeTableModel {


	private final int treeColumn;
	private final String[] columnNames;
	private int leftFileDateColumn = -1;
	private int rightFileDateColumn = -1;
	private int leftFileSizeColumn = -1;
	private int rightFileSizeColumn = -1;
	private boolean leftDateColumn;

	/**
	 * Create Dual Path Tree Table Model
	 * @param root File Tree root
	 * @param treeColumn column where the Tree is displayed
	 * @param columnNames Table Column names
	 */
	public AbstractMultiPathTreeTableModel(MultiPathFileNode root, int treeColumn, String[] columnNames) {
		super(root);
		
		
		this.treeColumn = treeColumn;
		this.columnNames = columnNames;
	}

	/**
	 * @param leftFileDateColumn the leftFileDateColumn to set
	 */
	public void setLeftFileDateColumn(int leftFileDateColumn) {
		this.leftFileDateColumn = leftFileDateColumn;
	}

	/**
	 * @param rightFileDateColumn the rightFileDateColumn to set
	 */
	public void setRightFileDateColumn(int rightFileDateColumn) {
		this.rightFileDateColumn = rightFileDateColumn;
	}

	/**
	 * @param leftFileSizeColumn the leftFileSizeColumn to set
	 */
	public void setLeftFileSizeColumn(int leftFileSizeColumn) {
		this.leftFileSizeColumn = leftFileSizeColumn;
	}

	/**
	 * @param rightFileSizeColumn the rightFileSizeColumn to set
	 */
	public void setRightFileSizeColumn(int rightFileSizeColumn) {
		this.rightFileSizeColumn = rightFileSizeColumn;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public final Object getValueAt(Object node, int column) {
		if (column == treeColumn) {
			return null;
		}
		MultiPathFileNode n = (MultiPathFileNode) node;
		
		leftDateColumn = column == leftFileDateColumn;
		if (leftDateColumn || column == leftFileSizeColumn) {
			return getPathColumnValue(leftDateColumn, n.getPathAttributes(0));
		} else {
			boolean rightDateColumn = column == rightFileDateColumn;
			if (rightDateColumn || column == rightFileSizeColumn) {
				return getPathColumnValue(rightDateColumn, n.getPathAttributes(1));
			}
		}
		
		return getTableValueAt(n, column);
	}

	private Object getPathColumnValue(boolean dateColumn, BasicFileAttributes pathAttributes) {
		if (pathAttributes == null) {
			return null;
		}
		if (dateColumn) {
			return  pathAttributes.lastModifiedTime();
		}
		return pathAttributes.size();
	}
	
	@Override
    public Class<?> getColumnClass(int column) {

        if (column == treeColumn) {
            return ITreeTableModel.class;
        }
        return super.getColumnClass(column);
    }

//	@Override
//	public boolean isCellEditable(Object node, int column) {
//		if (column == treeColumn) {
//            return true;
//        }
//		
//		return super.isCellEditable(node, column);
//	}


	public abstract Object getTableValueAt(MultiPathFileNode node, int column);

}
