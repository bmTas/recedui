package com.github.bmTas.recedui.listManagers;

import java.io.File;

import com.github.bmTas.recedui.def.listManagers.IListItem;

public class BasicListItem implements IListItem {

	final String shortText, displayText, itemText;
	
	public static BasicListItem  basicFileListItem(String itemText) {
		File file = new File(itemText);
		return new BasicListItem(file.getName(), getReducedName(file), itemText);
	}

	public static BasicListItem  basicFileListItem(File f, String itemText) {
		return new BasicListItem(f.getName(), getReducedName(new File(itemText)), itemText);
	}
	
	public static BasicListItem  recentListItem( String itemText) {
		return new BasicListItem(itemText, itemText, itemText);
	}

	
//	public BasicListItem(String itemText) {
//		this((new File(itemText)).getName(), getReducedName(new File(itemText)), itemText);
//	}
//	public BasicListItem(File f, String itemText) {
//		this(f.getName(), getReducedName(new File(itemText)), itemText);
//	}
	
	public BasicListItem(String shortText, String displayText, String itemText) {
		super();
		this.shortText = shortText;
		this.displayText = displayText;
		this.itemText = itemText;
	}

	@Override
	public String getShortText() {
		return shortText;
	}

	@Override
	public String getDisplayText() {
		return displayText;
	}

	@Override
	public String getItemText() {
		return itemText;
	}
	
	


	@Override
	public String toString() {
		return displayText;
	}

	protected static String getReducedName(File f) {
		StringBuffer s = new StringBuffer(f.getName());
		f = f.getParentFile();
		String parentName = null;

		while ( f != null
			&& (parentName = f.getName()) != null
			&&  s.length() + parentName.length() < 45
			&&  ! "".equals(parentName))  {
			s.insert(0, parentName + "/");
			f = f.getParentFile();
		}

		if (parentName != null) {
			s.insert(0, "../");
		}

		return s.toString();
	}

}
