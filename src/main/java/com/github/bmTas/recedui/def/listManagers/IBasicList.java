package com.github.bmTas.recedui.def.listManagers;

import java.util.List;

public interface IBasicList {

	public List<IListItem> getItemList();
	
	public void addChangeListner(IListChangeListner listner);
	
	public String getListName();
	
	public void addFileItem(String itemText);

	public void addItem(String itemText);
	
}
