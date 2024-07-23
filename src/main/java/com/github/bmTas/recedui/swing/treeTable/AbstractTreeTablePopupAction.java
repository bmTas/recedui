package com.github.bmTas.recedui.swing.treeTable;


import javax.swing.AbstractAction;
import javax.swing.Icon;



@SuppressWarnings("serial")
public abstract class AbstractTreeTablePopupAction extends AbstractAction implements ITreeTablePopupAction {

	private ITreeTablePositionDetails treeTablePositionDetails;
	
	public AbstractTreeTablePopupAction() {
		super();
	}

	public AbstractTreeTablePopupAction(String name, Icon icon) {
		super(name, icon);
	}

	public AbstractTreeTablePopupAction(String name) {
		super(name);
	}

	@Override
	public void setTreeTablePosition(ITreeTablePositionDetails treeTablePositionDetails) {
		this.treeTablePositionDetails = treeTablePositionDetails;
	}

	/**
	 * @return the treeTablePositionDetails
	 */
	public ITreeTablePositionDetails getTreeTablePosition() {
		return treeTablePositionDetails;
	}

}
