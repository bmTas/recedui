package com.github.bmTas.recedui.def.listManagers;

import java.io.File;

public interface IRecentAndFavoriteFiles extends IRecentFavourite {
	File getInitialPath();
//	IRecentList getRecent();
//	IUpdateableList getFavourites();
	
	IRecentList getRecentDirectory();
	IUpdateableList getFavouriteDirectory();
}
