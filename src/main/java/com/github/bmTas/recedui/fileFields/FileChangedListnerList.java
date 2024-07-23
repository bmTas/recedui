package com.github.bmTas.recedui.fileFields;

import java.io.File;
import java.util.ArrayList;

import com.github.bmTas.recedui.def.file.IFileChangedListner;

public class FileChangedListnerList {
	public final ArrayList<IFileChangedListner> listners = new ArrayList<IFileChangedListner>();
	
	

	public final void notifyUsersOfNewFile(boolean isDirectory, File old , File f) {
		if (f != null && (f.isDirectory() || ! isDirectory)
		&& (! f.equals(old))) { 
			fireFileChanged(f);
		}
	}

	public final void fireFileChanged(File f) {
		for (IFileChangedListner l : listners) {
			l.fileChanged(f);
		}
	}
}
