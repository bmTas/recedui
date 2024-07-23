package com.github.bmTas.recedui.fileFields.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.bmTas.recedui.def.listManagers.IListItem;


/**
 * 
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class MenuListItem extends JMenuItem implements ActionListener {

	private final IListItem listItem;
	private final IProcessMenuSelection processSelection;
	
	/**
	 * 
	 * @param listItem
	 */
	public MenuListItem(IListItem listItem, IProcessMenuSelection processSelection) {
		this(listItem, listItem.getDisplayText(), processSelection);

	}
	
	/**
	 * 
	 * @param listItem
	 */
	public MenuListItem(IListItem listItem, String txt, IProcessMenuSelection processSelection) {
		super(txt);
		super.addActionListener(this);
		
		this.listItem = listItem;
		this.processSelection = processSelection;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		processSelection.processSelection(listItem);
	}

}
