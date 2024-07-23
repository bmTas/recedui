package com.github.bmTas.recedui.xfiles;

import java.io.IOException;
import java.nio.file.Path;

public class FileExtensionFilter implements IFileChooserFilter {
	private final String desc;
	private final String[] extensions;
	private final int maxLength;
	
	public FileExtensionFilter(String desc, String[] extensions) {
		super();
		this.desc = desc;
		this.extensions = extensions;
		
		int maxLen = 0;
		for (int i = 0; i < extensions.length; i++) {
			if (extensions[i] == null || extensions[i].length() == 0) {
				throw new RuntimeException("null / empty extensions are not allowed");
			}
			extensions[i] = extensions[i].toLowerCase();
			maxLen = Math.max(maxLen, extensions[i].length());
		}
		maxLength = maxLen;
	}

	@Override
	public boolean accept(Path entry) throws IOException {
		String n = entry.toString();
		int p = n.lastIndexOf('.');
		int extLength = n.length() - p-1;

		if (p >= 0 && extLength > 0 && extLength <= maxLength) {
			String ext = n.substring(p+1).toLowerCase();
			for (int i = 0;i < extensions.length; i++) {
				if (ext.equals(extensions[i])) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String description() {
		return desc;
	}	
}
