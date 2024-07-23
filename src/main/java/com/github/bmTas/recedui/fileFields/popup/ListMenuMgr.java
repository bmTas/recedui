package com.github.bmTas.recedui.fileFields.popup;

import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;

public class ListMenuMgr  implements IListChangeListner  {

	public final JMenu menu;
//	private final IBasicList list;
	private final IUpdateableList updList;
	private final Action addToFavouriteAction;
	private final IProcessMenuSelection owner;
	
	public ListMenuMgr(String menuName, IBasicList list, IProcessMenuSelection owner) {
		this(menuName, list, null, owner, null);
	}
	public ListMenuMgr(String menuName, IUpdateableList updList, IProcessMenuSelection owner, Action addToFavouriteAction) {
		this(menuName, updList, updList, owner, addToFavouriteAction);
	}

	
	private ListMenuMgr(String menuName, IBasicList list, IUpdateableList updList, 
			IProcessMenuSelection owner, Action addToFavouriteAction) {
		super();
		
		this.menu = new JMenu(menuName);
//		this.list = list;
		this.updList = updList;
		this.owner = owner;
		this.addToFavouriteAction = addToFavouriteAction;
		
		loadPopup(list.getItemList());
		
		list.addChangeListner(this);
	}

	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IListChangeListner#listChanged(com.alee.extended.bm.listManagers.IBasicList)
	 */
	@Override
	public void listChanged(final IBasicList chgdList) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				loadPopup(chgdList.getItemList());
			}
		});
	}

	/**
	 * @param itemList
	 */
	protected void loadPopup(List<IListItem> itemList) {
		
		menu.removeAll();
		
		if (addToFavouriteAction != null && updList != null && updList.allowUpdates()) {
			menu.add(addToFavouriteAction);
			
			if (updList != null) {
				menu.add(new EditFavouriteAction(menu, updList));
			}
			menu.addSeparator();
		}
		
		for (IListItem itm : itemList) {
			menu.add(new MenuListItem(itm, owner));
		}
		
		menu.revalidate();
		menu.repaint();
		
//		int height = 0;
//		for (int i = 0; i < menu.getComponentCount(); i++) {
//			height += menu.getComponent(i).getPreferredSize().height;
//		}
//		setPopupHeight(Math.max(30, height));
	}

	
}
