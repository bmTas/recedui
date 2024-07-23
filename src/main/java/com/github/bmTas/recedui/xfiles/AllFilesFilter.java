package com.github.bmTas.recedui.xfiles;

import java.io.IOException;
import java.nio.file.Path;

public class AllFilesFilter implements IFileChooserFilter {

	public static final AllFilesFilter ALL_FILES = new AllFilesFilter();
	
	@Override
	public boolean accept(Path entry) throws IOException {
		return true;
	}

	@Override
	public String description() {
		return "All Files";
	}

}
