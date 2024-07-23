package com.github.bmTas.recedui.fileFields.params;

import java.io.File;

import com.github.bmTas.recedui.def.file.FileChooserDefs;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.xfiles.IFileChooserFilter;


public interface IFileChooserOptionsBldr {
	
	/**
	 * Style for the FileChooser
	 * @param styleId Style for the FileChooser
	 * @return
	 */
	IFileChooserOptionsBldr style(IStyle styleId);

	/**
	 * Add a File extension filter
	 * @param name of extension
	 * @param extensions
	 * @return
	 */
	IFileChooserOptionsBldr addFileExtensionOption(String name, String... extensions);
	IFileChooserOptionsBldr addFileFilterOption(IFileChooserFilter filter);
	
	/**
	 * @param filterFavourites List of regular expressions. you ca do
	 * <code>
	 *      regExpFilterFavourites(new BasicItemList("RegExpList", ".*", ".*txt", ".*csv");
	 * </code
	 */
	IFileChooserOptionsBldr regExpFilterFavourites(IUpdateableList filterFavourites);
	
	IFileChooserOptionsBldr directory(boolean isDirectory);
	IFileChooserOptionsBldr showHiddenFiles(boolean showHidden);

	/**
	 * @param chooserType options include FileChooserType.open etc
	 */
	IFileChooserOptionsBldr chooserType(FileChooserDefs.FileChooserType chooserType);
	/**
	 * 
	 * @param buttonDisplay options include<ul>
	 *   <li>FileChooserDefs.ControlButtonDisplay.FILTERS
	 *   <li>FileChooserDefs.ControlButtonDisplay.FILTERS_APPROVE
	 *   <li>FileChooserDefs.ControlButtonDisplay.ALL_CONTROLS
	 * </ul>
	 */
	IFileChooserOptionsBldr showControlButtons(FileChooserDefs.ControlButtonDisplay buttonDisplay);
	/**
	 * @param fileListType Type of file List, options include <ul>
	 *   <li>FileChooserDetailDisplay.list
	 *   <li>FileChooserDetailDisplay.smallIcons
	 *   <li>FileChooserDetailDisplay.details
	 * </ul>
	 */
	IFileChooserOptionsBldr fileListType(FileChooserDefs.FileChooserDetailDisplay fileListType);

	/**
	 * 
	 * @param sidePane
	 * @return
	 */
	IFileChooserOptionsBldr sidePaneType(FileChooserDefs.FileChooserSide sidePane);
	
	IFileChooserOptionsBldr initialPath(File initialPath);
	
	IFileChooserOptionsAndBuilder asBuilderAndParameter();


}
