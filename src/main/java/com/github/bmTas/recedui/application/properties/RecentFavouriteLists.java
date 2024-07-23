package com.github.bmTas.recedui.application.properties;

import java.io.File;
import java.util.List;

import com.github.bmTas.recedui.def.application.IApplicationProperties;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFilesExtended;
import com.github.bmTas.recedui.def.listManagers.IStandardUpdateableList;

public class RecentFavouriteLists implements IRecentAndFavoriteFilesExtended {
	
	private final IStandardUpdateableList recentFiles, recentDirectory, favouriteFiles, favouriteDirectory;

	public RecentFavouriteLists(IApplicationProperties properties, String listName) {
		this.recentFiles = properties.getFileList(listName + "_Recent");
		this.recentDirectory = properties.getFileList(listName + "_RecentDirectory");
		this.favouriteFiles = properties.getFileList(listName + "_Favourite");
		this.favouriteDirectory = properties.getFileList(listName + "_FavouriteDirectory");
	}

	@Override
	public File getInitialPath() {
		List<IListItem> itemList = recentFiles.getItemList();
		return itemList.size() > 0 ? new File(itemList.get(0).getItemText()) : null;
	}

	@Override
	public IStandardUpdateableList getRecent() {
		return recentFiles;
	}

	@Override
	public IStandardUpdateableList getFavourites() {
		return favouriteFiles;
	}

	@Override
	public IStandardUpdateableList getRecentDirectory() {
		return recentDirectory;
	}

	@Override
	public IStandardUpdateableList getFavouriteDirectory() {
		return favouriteDirectory;
	}



}
