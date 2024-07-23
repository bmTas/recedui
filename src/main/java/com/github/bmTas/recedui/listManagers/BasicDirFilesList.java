package com.github.bmTas.recedui.listManagers;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;

/**
 * Create a Directory List from a File List
 * @author Bruce Martin
 *
 */
public class BasicDirFilesList extends BasicItemList implements IListChangeListner {

	
	public BasicDirFilesList(IBasicList list) {
		super("Directory", toDirNames(list));

		list.addChangeListner(this);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.alee.extended.bm.listManagers.IListChangeListner#listChanged(com.alee.extended.bm.listManagers.IBasicList)
	 */
	@Override
	public void listChanged(IBasicList list) {
		super.setItemList(toItemList(toDirNames(list)));
	}



	private static String[] toDirNames(IBasicList list) {
		TreeSet<String> dirset = new TreeSet<String>();
		List<IListItem> itemList = list.getItemList();
		
		for (IListItem li : itemList) {
			String itemText = li.getItemText();
			if (itemText != null) {
				dirset.add(new File(itemText).getParent());
			}
		}
		
		return dirset.toArray(new String[dirset.size()]);
	}
}
