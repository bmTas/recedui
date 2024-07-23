package com.github.bmTas.recedui.application.properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.bmTas.recedui.def.application.IApplicationProperties;
import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFilesExtended;
import com.github.bmTas.recedui.def.listManagers.IRecentFavouriteExtended;
import com.github.bmTas.recedui.listManagers.BasicItemList;
import com.github.bmTas.recedui.listManagers.BasicListItem;
import com.github.bmTas.recedui.xEnvironment.Env;

public class PropertiesDataStore implements IApplicationProperties {
	
	/**
	 * load
	 * @param fileName
	 * @param addDotToStartOnMacLinx
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static PropertiesDataStore newPropertiesInHomeDirectory(String fileName, boolean addDotToStartOnMacLinx) 
	throws FileNotFoundException, IOException {
		if (fileName == null) {
			throw new RuntimeException("File Name cane not be null !!!");
		}
		if (addDotToStartOnMacLinx && (Env.IS_MAC || Env.IS_NIX) && (! fileName.startsWith("."))) {
			fileName = "." + fileName;
		}
		Path path = Paths.get(System.getProperty("user.home"), fileName);
		return newPropertiesFullFileName(path.toString());
	}

		
	public static PropertiesDataStore newPropertiesFullFileName(String fileName) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		
		if (Files.exists(Paths.get(fileName))) {
			p.load(new FileReader(fileName));
		}
		
		return new PropertiesDataStore(p, fileName);
	}

	private static final int LIST_LIMIT = 25;
	
	private Properties properties;
	private String fileName;
	
	/**
	 * Use PropertiesDataStore
	 * @param properties file
	 */
	private PropertiesDataStore(Properties properties, String fileName) {
		this.properties = properties;
		this.fileName = fileName;
	}
	
	@Override
	public BasicItemList getFileList(String name) {
		ArrayList<IListItem> list = new ArrayList<>();
		
		for (int idx = 1; idx < 26; idx++) {
			String s = properties.getProperty(formatId(name, idx));
			if (s == null || s.length() == 0) { break; }
			list.add(BasicListItem.basicFileListItem(s));
		}
		return new BasicItemList(name, true, list);
	}
	
	@Override
	public void saveList(IBasicList fileList) {
		List<IListItem> list = fileList.getItemList();
		String id = fileList.getListName();
		int idx = 1;
		
		for (IListItem li : list ) {
			String s =li.getItemText();
			if (s != null && s.length() > 0) {
				properties.put(formatId(id, idx), s);
				if (idx++ > LIST_LIMIT) { break; }
			}
		}
	}

	

	private String formatId(String id, int idx) {
		return id + "." + idx;
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public Object setProperty(String key, String value) {
		return properties.setProperty(key, value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Properties#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	/**
	 * Get the Recent/Favorite lists for an id
	 * @param id identifier for the lists,
	 * @return IRecentAndFavouriteFiles
	 */
	@Override
	public IRecentFavouriteExtended getRecentFavorites(String id) {
		return new RecentFavouriteLists(this, id);
	}

	/**
	 * Get the Recent/Favorite lists for an id
	 * @param id identifier for the lists,
	 * @return IRecentAndFavouriteFiles
	 */
	@Override
	public IRecentAndFavoriteFilesExtended getRecentFavoriteFiles(String id) {
		return new RecentFavouriteLists(this, id);
	}

	@Override
	public void save() throws IOException {
		Path filenamePath = Paths.get(fileName);
		if (! Files.exists(filenamePath)) {
			Files.createDirectories(filenamePath.getParent());
		}
		properties.store(new FileWriter(fileName), "");
	}
}
