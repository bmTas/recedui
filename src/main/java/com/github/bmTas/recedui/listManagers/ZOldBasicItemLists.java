package com.github.bmTas.recedui.listManagers;

import com.github.bmTas.recedui.def.listManagers.IItemLists;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;

/**
 * Basic implementation of Recent / Favourite lists
 * @author Bruce Martin
 *
 */
public class ZOldBasicItemLists implements IItemLists {

	private final IRecentList recentLst;
	private final IUpdateableList favouriteList;
	
	public ZOldBasicItemLists(IRecentList recentLst, IUpdateableList favouriteList) {
		super();
		this.recentLst = recentLst;
		this.favouriteList = favouriteList;
	}

	@Override
	public IRecentList recentList() {
		return recentLst;
	}

	@Override
	public IUpdateableList favouriteList() {
		return favouriteList;
	}

}
