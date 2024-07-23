package com.github.bmTas.recedui.listManagers;

import java.util.ArrayList;
import java.util.List;

import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IStandardUpdateableList;

/**
 * 
 * @author Bruce Martin
 *
 */
public class BasicItemList  implements IStandardUpdateableList {

	private List<IListItem> items;// = new ArrayList<IListItem>();
	private List<IListChangeListner> listners = new ArrayList<IListChangeListner>(3);
	
	private final String listName;
	private final boolean allowListUpdates;
	
	public BasicItemList(String listName, String... filenames) {
		this(listName, false, toItemList(filenames));
	}

	
	public BasicItemList(String listName,  boolean allowListUpdates, String... filenames) {
		this(listName, allowListUpdates, toItemList(filenames));
	}

	
	public BasicItemList(String listName,  boolean allowListUpdates, List<IListItem> fileNames) {
		this.listName = listName;
		this.allowListUpdates = allowListUpdates;
		this.items = fileNames;
	}



	/**
	 * @param filenames
	 */
	protected final static List<IListItem> toItemList(String[] filenames) {
		List<IListItem> items = new ArrayList<IListItem>();
		for (String fn : filenames) {
			items.add(BasicListItem.basicFileListItem(fn));
		}
		
		return items;
	}

	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IBasicList#getListName()
	 */
	@Override
	public String getListName() {
		return listName;
	}

	
	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IBasicList#getItemList()
	 */
	@Override
	public List<IListItem> getItemList() {
		return items;
	}

	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IBasicList#addChangeListner(com.alee.extended.bm.listManagers.IListChangeListner)
	 */
	@Override
	public void addChangeListner(IListChangeListner listner) {
		listners.add(listner);
	}
	

	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IRecentList#moveToTop(com.alee.extended.bm.listManagers.IListItem)
	 */
	@Override
	public void moveToTop(IListItem listItem) {

		removeItem(listItem.getItemText());
		items.add(0, listItem);
		
		notifyListners();
	}


	private IListItem removeItem(String itemText) {
		if (items == null) {
			items = new ArrayList<IListItem>();
		}
		for (int i = items.size() - 1; i >= 0; i--  ) {
			if (itemText.equals(items.get(i).getItemText())) {
				return items.remove(i);
			}
		}
		return null;
	}
	
	
	@Override
	public void addItem(String itemText) {
		if (itemText == null) { return; }
		
		IListItem itm = removeItem(itemText);

		items.add(0, itm == null ? BasicListItem.recentListItem(itemText) : itm);
		
		notifyListners();
	}
	@Override
	public void addFileItem(String itemText) {
		if (itemText == null) { return; }
		
		IListItem itm = removeItem(itemText);

		items.add(0, itm == null ? BasicListItem.basicFileListItem(itemText) : itm);
		
		notifyListners();
	}
	
	
	


	private void notifyListners() {

		for (IListChangeListner cl : listners) {
			cl.listChanged(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IUpdateableFileList#updateList(java.util.List)
	 */
	@Override
	public void setItemList(List<IListItem> items) {
		this.items = items;
	}

	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IUpdateableFileList#allowUpdates()
	 */
	@Override
	public boolean allowUpdates() {
		return allowListUpdates;
	}
	
	
}
