package com.github.bmTas.recedui.fileFields.params;

import java.io.File;
import java.util.List;

import com.github.bmTas.recedui.def.file.FileChooserDefs;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.xfiles.IFileChooserFilter;

public interface IFileChooserOptions {

	List<IFileChooserFilter> getFilters();
	File getInitialPath();
	IStyle getStyle();
	FileChooserDefs.FileChooserType getChooserType();
	FileChooserDefs.ControlButtonDisplay getShowControlButtons();
	FileChooserDefs.FileChooserDetailDisplay getFileListType();
	IUpdateableList getRegExpFilterFavourites();
	FileChooserDefs.FileChooserSide getSidePaneType();
	boolean isDirectory();
	boolean isShowHiddenFiles();
}
