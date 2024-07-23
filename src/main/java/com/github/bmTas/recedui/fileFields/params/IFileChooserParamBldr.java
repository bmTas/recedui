package com.github.bmTas.recedui.fileFields.params;

import java.io.File;

import com.github.bmTas.recedui.def.file.FileChooserDefs;
import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFiles;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.xfiles.IFileChooserFilter;


public interface IFileChooserParamBldr extends IFileChooserOptionsBldr {
	IFileChooserParamBldr style(IStyle styleId);

	IFileChooserParamBldr addFileExtensionOption(String description, String... extensions);
	IFileChooserParamBldr addFileFilterOption(IFileChooserFilter filter);
	
	IFileChooserParamBldr recentFiles(IRecentList recentList);
	IFileChooserParamBldr favouriteFiles(IUpdateableList favouriteList);
	
	IFileChooserParamBldr recentDirectory(IRecentList recentDir);
	IFileChooserParamBldr favouriteDirectory(IUpdateableList favouriteDir);
	
	IFileChooserParamBldr regExpFilterFavourites(IUpdateableList filterFavourites);
	
	IFileChooserParamBldr directory(boolean isDirectory);
	IFileChooserParamBldr showHiddenFiles(boolean showHidden);

	IFileChooserParamBldr chooserType(FileChooserDefs.FileChooserType chooserType);
	IFileChooserParamBldr showControlButtons(FileChooserDefs.ControlButtonDisplay buttonDisplay);
	IFileChooserParamBldr fileListType(FileChooserDefs.FileChooserDetailDisplay fileListType);
	
	IFileChooserParamBldr initialPath(File initialPath);

	IFileChooserParamBldr setRecentFavourite(IRecentAndFavoriteFiles recentFav);

	IFileChooserParamAndBuilder asBuilderAndParameter();


}
