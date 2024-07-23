package com.github.bmTas.recedui.fileChooser;

public enum FileView {
		tiles("Tiles"),
		mediumIcons("Medium Icon"),
		list("List"),
		detail("Detail")
	;
	public final String text;

	private FileView(String t) {
		this.text = t;
	}
}
