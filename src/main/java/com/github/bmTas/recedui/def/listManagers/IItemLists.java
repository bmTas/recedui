package com.github.bmTas.recedui.def.listManagers;

/**
 * recent / favourite lists
 * @author Bruce Martin
 *
 */
public interface IItemLists {

	public IRecentList recentList();
	
	public IUpdateableList favouriteList();
}
