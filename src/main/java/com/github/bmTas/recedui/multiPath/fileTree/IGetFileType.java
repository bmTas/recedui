package com.github.bmTas.recedui.multiPath.fileTree;

public interface IGetFileType {
	
	FileType getFileType();

	enum FileType {
		DRIVE,
		DIRECTORY,
		FILE
	}
}
