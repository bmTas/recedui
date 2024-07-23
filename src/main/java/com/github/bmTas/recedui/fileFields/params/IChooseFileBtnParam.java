package com.github.bmTas.recedui.fileFields.params;

import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFiles;
import com.github.bmTas.recedui.styles.IStyle;

public interface IChooseFileBtnParam extends IRecentAndFavoriteFiles {

	IStyle getStyle();
	IFileChooserOptions getFileChooserOptions();
	boolean isDirectory();
	boolean isMultiSelectionEnabled();

}
