package com.github.bmTas.recedui.fileChooser;

import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.fileFields.popup.IProcessMenuSelection;
import com.github.bmTas.recedui.fileFields.popup.MenuListItem;


public class RecentMenuDtl implements IListChangeListner {
	public final JMenu recentMenu;
	
	private final IBasicList list;
	private final IUpdateableList updateableList;
	private final IProcessMenuSelection owner;
	private final Action addAction;
	
	
	public RecentMenuDtl(
			String menuText, IBasicList list, 
			IProcessMenuSelection owner) {
		this(menuText, list, null, null, owner);
	}
	
	public RecentMenuDtl(
			String menuText,    IUpdateableList list, 
			Action addAction, IProcessMenuSelection owner) {
		this(menuText, list, list, addAction, owner);
	}
	
	private RecentMenuDtl(
			String menuText,  IBasicList list, IUpdateableList updlist, 
			Action addAction, IProcessMenuSelection owner) {
		this.recentMenu = new JMenu(menuText);
		this.list = list;
		this.owner = owner;
		this.updateableList = updlist;
		this.addAction = addAction;
		
		loadMenu();
		
		list.addChangeListner(this);
	}
	
	

	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IListChangeListner#listChanged(com.alee.extended.bm.listManagers.IBasicList)
	 */
	@Override
	public void listChanged(IBasicList list) {
		SwingUtilities.invokeLater(new Runnable() {	
			@Override public void run() {
				loadMenu();
			}
		});
	}
	

	/**
	 * @param owner
	 */
	public void loadMenu() {
		List<IListItem> itemList = this.list.getItemList();
		
		recentMenu.removeAll();
		
		if (updateableList != null) {
			recentMenu.add(addAction);
			recentMenu.addSeparator();
		}
		
		for (IListItem itm : itemList) {
			recentMenu.add(new MenuListItem(itm, itm.getDisplayText(), owner));
		}
		recentMenu.repaint();
	}
}
