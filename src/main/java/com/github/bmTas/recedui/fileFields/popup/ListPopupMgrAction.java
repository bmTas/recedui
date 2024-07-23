package com.github.bmTas.recedui.fileFields.popup;

import java.util.List;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;

/**
 * Display recent / favourite list
 * @author Bruce Martin
 *
 */
public class ListPopupMgrAction extends AbstractPopupMgrAction implements IListChangeListner {



	private final IBasicList list;
	private final IUpdateableList updList;
	private final Action addToFavouriteAction;
	private final IProcessMenuSelection owner;

	
	public ListPopupMgrAction(IBasicList stdList, JComponent displayItm, JComponent container, IProcessMenuSelection owner) {
		this(stdList, null, displayItm, container, owner, null);
	}
	
	public ListPopupMgrAction(IUpdateableList updList,  
			JComponent displayItm, JComponent container, 
			IProcessMenuSelection owner, Action addToFavourite) {
		this(updList, updList, displayItm, container, owner, addToFavourite);
	}
	
	private ListPopupMgrAction(IBasicList stdList, IUpdateableList updList, 
			JComponent displayItm, JComponent container, 
			IProcessMenuSelection owner, Action addToFavourite) {
		super(new JPopupMenu(), displayItm, container);
		
		this.list = stdList;
		this.updList = updList;
		this.owner = owner;
		this.addToFavouriteAction = addToFavourite;
		
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
		JPopupMenu popup = super.currentPopup;
		
		popup.removeAll();
		
		if (addToFavouriteAction != null && updList != null && updList.allowUpdates()) {
			popup.add(addToFavouriteAction);
			
			popup.add(new EditFavouriteAction(popup, updList));
			
			popup.addSeparator();
		}
		
		for (IListItem itm : itemList) {
			popup.add(new MenuListItem(itm, owner));
		}
		
		int height = 0;
		for (int i = 0; i < popup.getComponentCount(); i++) {
			height += popup.getComponent(i).getPreferredSize().height;
		}
		setPopupHeight(Math.max(30, height));
	}
	
	@Override
	protected void highlightItem(JPopupMenu currentPopup, boolean visible) {
		if (visible) {
	//		searchChildren(new ArrayList<TreeComboItem>(), items);
		}
	}
}
