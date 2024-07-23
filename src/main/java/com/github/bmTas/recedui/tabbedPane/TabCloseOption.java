package com.github.bmTas.recedui.tabbedPane;

public enum TabCloseOption {
	NO_CLOSE_OPTION(false),
	CLOSE_ALL_EXCEPT_FIRST(false),
	CLOSE_ALL(true),
	;
	final boolean closeFirst;

	private TabCloseOption(boolean closeFirst) {
		this.closeFirst = closeFirst;
	}
	
}
