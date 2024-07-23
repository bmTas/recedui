package com.github.bmTas.recedui.fileChooser;

import java.io.File;
import java.util.List;

public interface FileChooserListener {
	public void selectionChanged(List<File> selectedFiles);
	
	public void directoryChanged(File newDirectory);
}
