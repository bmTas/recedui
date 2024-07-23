package com.github.bmTas.recedui.def.file;

public class FileChooserDefs {

	public static enum ControlButtonDisplay {
		FILTERS,
		FILTERS_APPROVE,
		ALL_CONTROLS
	};
	
	public static enum FileChooserType {
			open("Open", "Open"),
			save("Save", "Save"),
			other("Other", "Accept");
			
		public final String dialogName, acceptButtonName;
		
		FileChooserType(String name, String acceptButtonName) {
			this.dialogName = name;
			this.acceptButtonName = acceptButtonName;
		}
	};
	public static enum FileChooserDetailDisplay {
			list("List", false),
			smallIcon("Small Icons", false),
			tiles("Tiles", false),
			icon("Icons", false),
			details("Details", true)
		;
	
		public final String screenName;
		public final boolean useTable;
		FileChooserDetailDisplay(String screenName, boolean useTable) {
			this.screenName = screenName;
			this.useTable = useTable;
		}
	}
	
	public static enum FileChooserSide {
		FOLDER_TREE,
		PLACES
	}
//	
//	public enum FileChooserType {
//		openStd(FileChooserDialogType.open, true);
//		
//		
//		public final FileChooserDialogType dialogType;
//		public final boolean standard;
//		FileChooserType(FileChooserDialogType dialogType, boolean standard) {
//			this.dialogType = dialogType;
//			this.standard = standard;
//		}
//		
//		public String dialogName() {
//			return dialogType.dialogName;
//		}
//		
//		public boolean isOpen() {
//			return dialogType == FileChooserDialogType.open;
//		}
//		
//		public boolean isSave() {
//			return dialogType == FileChooserDialogType.save;
//		}
//	}
		
}