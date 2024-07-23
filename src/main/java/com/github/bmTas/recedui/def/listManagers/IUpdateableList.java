package com.github.bmTas.recedui.def.listManagers;

import java.util.List;

/**
 * Updatable List for <i>Favourite</i> drop down menu's
 * 
 * @author Bruce Martin
 *
 */
public interface IUpdateableList extends IBasicList {

	public void setItemList(List<IListItem> items);
	public boolean allowUpdates();
}
