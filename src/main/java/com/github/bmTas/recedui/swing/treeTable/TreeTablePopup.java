package com.github.bmTas.recedui.swing.treeTable;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

public class TreeTablePopup extends MouseAdapter implements ITreeTablePositionDetails {
    private final JPopupMenu popup = new JPopupMenu();
    private final JTreeTable treeTable;
    private 	int row, column;
    private Object node;
    private ArrayList<ISetTreeTableRowDetails> actionsToBeNotifiedOfPosition = new ArrayList<>();
    
	public TreeTablePopup(JTreeTable treeTable) {
		super();
		this.treeTable = treeTable;
		
		treeTable.addMouseListener(this);
	}

	@Override
	public JTreeTable getTreeTable() {
		return treeTable;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public Object getNode() {
		return node;
	}
    
    /**
     * @see MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
      	if (treeTable != null && ! treeTable.hasFocus()) {
      		treeTable.requestFocusInWindow();
      	}
        maybeShowPopup(e);
    }

    /**
     * show popup if it is a popup request
     * @param mouseEvent the mouse event that triggered this action
     */
    private void maybeShowPopup(MouseEvent mouseEvent) {
    	if (mouseEvent != null) {
    		Point point = mouseEvent.getPoint();
			column = treeTable.columnAtPoint(point);
    		row = treeTable.rowAtPoint(point);
    		TreePath pathForRow = treeTable.getPathForRow(row);

    		node = pathForRow == null ? null : pathForRow.getLastPathComponent();
    		if ( mouseEvent.isPopupTrigger()) {
	    		int x = mouseEvent.getX(),
	    			y = mouseEvent.getY();
	    		
	    		for (ISetTreeTableRowDetails a : actionsToBeNotifiedOfPosition) {
	    			a.setTreeTablePosition(this);
	    		}
	    		popup.show(mouseEvent.getComponent(), x, y);
	    	} else {
	    		processNonPopupActions(row, column, node, mouseEvent);
	    	}
    	}
    }
    
    /**
     * Process non popup event.
     * @param event
     */
    public void processNonPopupActions(int row, int col, Object node, MouseEvent mouseEvent) {
    	
    }
    
    public TreeTablePopup add(ITreeTablePopupAction popupAction) {
    	//popupAction.setTreeTablePosition(this);
    	popup.add(popupAction);
    	actionsToBeNotifiedOfPosition.add(popupAction);
    	return this;
    }

    public <MenuItemSetDtls extends JMenuItem & ISetTreeTableRowDetails> TreeTablePopup add(MenuItemSetDtls menuItem) {
    	//menuItem.setTreeTablePosition(this);
    	popup.add(menuItem);
    	actionsToBeNotifiedOfPosition.add(menuItem);
    	return this;
    }

    public TreeTablePopup add(JMenuItem menuItem, ISetTreeTableRowDetails setDetails) {
    	//setDetails.setTreeTablePosition(this);
    	popup.add(menuItem);
    	actionsToBeNotifiedOfPosition.add(setDetails);
    	return this;
    }
 
}
