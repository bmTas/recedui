package com.github.bmTas.recedui.fileFields.params;

import java.io.File;

import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFiles;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;

public interface IChooseFileBtnParamBldr {
	IChooseFileBtnParamBldr style(IStyle styleId);

	IChooseFileBtnParamBldr recentFiles(IRecentList recentList);
	IChooseFileBtnParamBldr favouriteFiles(IUpdateableList favouriteList);
	
	IChooseFileBtnParamBldr recentDirectory(IRecentList recentDir);
	IChooseFileBtnParamBldr favouriteDirectory(IUpdateableList favouriteDir);
	
	IChooseFileBtnParamBldr initialPath(File initialPath);

	IChooseFileBtnParamBldr directory(boolean isDirectory);
	 //
	//IChooseFileBtnParamBldr chooserType(FileChooserDefs.FileChooserType type);
	IChooseFileBtnParamBldr multiSelectionEnabled(boolean multiSelection);


	IChooseFileBtnParamBldr setRecentFavourite(IRecentAndFavoriteFiles recentFav);
	IChooseFileBtnParamBldr fileChooserOptions(IFileChooserOptions options);


	IChooseFileBtnParam buildParam();
	
	IChooseFileBtnParamAndBuilder asBuilderAndParameter();
}
