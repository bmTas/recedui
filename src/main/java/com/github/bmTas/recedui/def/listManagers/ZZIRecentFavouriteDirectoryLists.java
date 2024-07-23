package com.github.bmTas.recedui.def.listManagers;

public interface ZZIRecentFavouriteDirectoryLists extends IRecentFavourite {

	/**
	 * @return the recentFiles
	 */
	IStandardUpdateableList getRecent();

	/**
	 * @return the recentDirectory
	 */
	IStandardUpdateableList getRecentDirectory();

	/**
	 * @return the favouriteFiles
	 */
	IStandardUpdateableList getFavourites();

	/**
	 * @return the favouriteDirectory
	 */
	IStandardUpdateableList getFavouriteDirectory();

}