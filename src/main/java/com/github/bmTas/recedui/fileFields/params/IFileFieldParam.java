package com.github.bmTas.recedui.fileFields.params;

import java.io.File;

import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFiles;
import com.github.bmTas.recedui.styles.IStyle;

/**
 * 
 * @author Bruce Martin
 *
 */
public interface IFileFieldParam extends IRecentAndFavoriteFiles {

	File getInitialPath();
	IStyle getStyle();
	IFileChooserOptions getFileChooserOptions();
	boolean isDirectory();
	boolean isCompleteFile();
	boolean isAllowNewFiles();
	boolean isShowDirectory();
}
