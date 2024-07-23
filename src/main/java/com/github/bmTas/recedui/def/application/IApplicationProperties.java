package com.github.bmTas.recedui.def.application;

import java.io.IOException;

import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFilesExtended;
import com.github.bmTas.recedui.def.listManagers.IRecentFavouriteExtended;
import com.github.bmTas.recedui.listManagers.BasicItemList;

public interface IApplicationProperties {

	BasicItemList getFileList(String name);

	void saveList(IBasicList fileList);

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 */
	Object setProperty(String key, String value);

	/**
	 * @param key lookup key for the required value
	 * @return requested value
	 * @see java.util.Properties#getProperty(java.lang.String)
	 */
	String getProperty(String key);


	/**
	 * @param key lookup key for the required value
	 * @param defaultValue default value if the key does not exist
	 * @return requested value
	 * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
	 */
	String getProperty(String key, String defaultValue);

	void save() throws IOException;

	/**
	 * Get the Recent/Favorite lists for an id
	 * @param id identifier for the lists
	 * @return Recent/Favorite Files container class
	 */
	IRecentAndFavoriteFilesExtended getRecentFavoriteFiles(String id);

	/**
	 * Get the Recent/Favorite lists for an id
	 * @param id identifier for the lists,
	 * @return IRecentFavourite
	 */
	IRecentFavouriteExtended getRecentFavorites(String id);

}