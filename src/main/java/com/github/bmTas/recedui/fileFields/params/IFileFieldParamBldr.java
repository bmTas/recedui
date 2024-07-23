package com.github.bmTas.recedui.fileFields.params;

import java.io.File;

import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFiles;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;

public interface IFileFieldParamBldr {
	
	IFileFieldParamBldr recentFiles(IRecentList recentList);
	IFileFieldParamBldr favouriteFiles(IUpdateableList favouriteList);
	
	IFileFieldParamBldr recentDirectory(IRecentList recentDir);
	IFileFieldParamBldr favouriteDirectory(IUpdateableList favouriteDir);
	
	IFileFieldParamBldr initialPath(File initialPath);
	/**
	 * Whether a file or directory is expected 
	 * @param isDirectory wether a directo (or file) is expected
	 * @return ParameterBuilder for further updates
	 */
	IFileFieldParamBldr directory(boolean isDirectory);
	/**
	 * Whether to auto complete file names or not
	 * @param autoComplete  Whether to auto complete file names or not
	 * @return ParameterBuilder for further updates
	 */
	IFileFieldParamBldr completeFile(boolean autoComplete);
	/**
	 * whether to allow new files or restrict the user to existing files.
	 * @param newFilesOk whether to allow new files or restrict the user to existing files.
	 * @return ParameterBuilder for further updates
	 */
	IFileFieldParamBldr allowNewFiles(boolean newFilesOk);
	
	/**
	 * Whether files directory should be displayed (normally yes)
	 * @param showDir show the file including/excluding the directory 
	 * @return ParameterBuilder for further updates
	 */
	IFileFieldParamBldr showDirectory(boolean showDir);
	
	IFileFieldParamBldr setRecentFavourite(IRecentAndFavoriteFiles recentFav);
	
	/**
	 * If you want the File Search button to display you must use this option.
	 * Also you can control what options to use in the file chooser.
	 * Usage:
	 * <pre>
	 * 		.fileChooserOptions(UiFileChooser.newParamBldr()
	 *					.initialPath(null)
	 *					.chooserType(FileChooserType.open)
	 *				.asBuilderAndParameter())
	 * </pre>
	 * @param options
	 * @return
	 */
	IFileFieldParamBldr fileChooserOptions(IFileChooserOptions options);
	IFileFieldParamBldr style(IStyle styleId);

	/**
	 * convert to a <i>File-field parameter</i>
	 * @return File-field parameter
	 */
	IFileFieldParam buildParam();
	
	/**
	 * convert to <i>File-field parameter</i> and a <i>File-field parameter builder</i>
	 * @return
	 */
	IFileFieldParamAndBuilder asBuilderAndParameter();
}
