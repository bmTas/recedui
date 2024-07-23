package com.github.bmTas.recedui.xfiles;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public interface IFileChooserFilter extends DirectoryStream.Filter<Path> {
	public String description();
}
