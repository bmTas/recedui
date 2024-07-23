package com.github.bmTas.recedui.def.file;

import java.io.File;

import com.github.bmTas.recedui.def.listManagers.IListItem;

public interface IFileListItem extends IListItem, IGetPath {

	String getFileName();

	File getFile();

}