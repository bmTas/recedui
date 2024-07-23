package com.github.bmTas.recedui.application.displays;

import java.util.ArrayList;
import java.util.List;

import com.github.bmTas.recedui.def.application.displays.IActionId;
import com.github.bmTas.recedui.icons.IIconHolder;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.xBackwardCompatibilty.ReActionHandler;

public class StandardActionId implements IActionId{

	private static List<StandardActionId> standardActionList = new ArrayList<>(83);

	public static StandardActionId SAVE = dcl("SAVE", ReActionHandler.SAVE, IconManager.ICONS.documentSave);
	public static StandardActionId SAVE_AS = dcl("SAVE_AS", ReActionHandler.SAVE_AS, IconManager.ICONS.documentSaveAs);
	public static StandardActionId EXPORT_AS_HTML = dcl("EXPORT_AS_HTML", ReActionHandler.EXPORT_AS_HTML);
	public static StandardActionId EXPORT_AS_HTML_TBL_PER_ROW = dcl("EXPORT_AS_HTML_TBL_PER_ROW", ReActionHandler.EXPORT_AS_HTML_TBL_PER_ROW);
	public static StandardActionId EXPORT_VELOCITY = dcl("EXPORT_VELOCITY", ReActionHandler.EXPORT_VELOCITY);
	public static StandardActionId SAVE_AS_XML = dcl("SAVE_AS_XML", ReActionHandler.SAVE_AS_XML);
	public static StandardActionId SAVE_LAYOUT_XML = dcl("SAVE_LAYOUT_XML", ReActionHandler.SAVE_LAYOUT_XML);
	public static StandardActionId EXPORT_HTML_TREE = dcl("EXPORT_HTML_TREE", ReActionHandler.EXPORT_HTML_TREE);
	public static StandardActionId EXPORT_AS_CSV = dcl("EXPORT_AS_CSV", ReActionHandler.EXPORT_AS_CSV);
	public static StandardActionId EXPORT_AS_FIXED = dcl("EXPORT_AS_FIXED", ReActionHandler.EXPORT_AS_FIXED);
	public static StandardActionId DELETE = dcl("DELETE", ReActionHandler.DELETE, IconManager.ICONS.editDelete);
	public static StandardActionId FIND = dcl("FIND", ReActionHandler.FIND, IconManager.ICONS.editFind);
	public static StandardActionId FILTER = dcl("FILTER", ReActionHandler.FILTER, IconManager.ICONS.viewFilter);
	public static StandardActionId OPEN = dcl("OPEN", ReActionHandler.OPEN, IconManager.ICONS.documentOpen);
	public static StandardActionId CLOSE = dcl("CLOSE", ReActionHandler.CLOSE);
	public static StandardActionId NEW = dcl("NEW", ReActionHandler.NEW, IconManager.ICONS.documentNew);
	public static StandardActionId CORRECT_RECORD_LENGTH = dcl("CORRECT_RECORD_LENGTH", ReActionHandler.CORRECT_RECORD_LENGTH);
	public static StandardActionId TABLE_VIEW_SELECTED = dcl("TABLE_VIEW_SELECTED", ReActionHandler.TABLE_VIEW_SELECTED);
	public static StandardActionId RECORD_VIEW_SELECTED = dcl("RECORD_VIEW_SELECTED", ReActionHandler.RECORD_VIEW_SELECTED);
	public static StandardActionId COLUMN_VIEW_SELECTED = dcl("COLUMN_VIEW_SELECTED", ReActionHandler.COLUMN_VIEW_SELECTED);
	public static StandardActionId SELECTED_VIEW = dcl("SELECTED_VIEW", ReActionHandler.SELECTED_VIEW);
	public static StandardActionId BUILD_FIELD_TREE = dcl("BUILD_FIELD_TREE", ReActionHandler.BUILD_FIELD_TREE);
	public static StandardActionId BUILD_SORTED_TREE = dcl("BUILD_SORTED_TREE", ReActionHandler.BUILD_SORTED_TREE);
	public static StandardActionId BUILD_RECORD_TREE = dcl("BUILD_RECORD_TREE", ReActionHandler.BUILD_RECORD_TREE);
	public static StandardActionId BUILD_LAYOUT_TREE = dcl("BUILD_LAYOUT_TREE", ReActionHandler.BUILD_LAYOUT_TREE);
	public static StandardActionId BUILD_XML_TREE_SELECTED = dcl("BUILD_XML_TREE_SELECTED", ReActionHandler.BUILD_XML_TREE_SELECTED);
	public static StandardActionId BUILD_LAYOUT_TREE_SELECTED = dcl("BUILD_LAYOUT_TREE_SELECTED", ReActionHandler.BUILD_LAYOUT_TREE_SELECTED);
	public static StandardActionId HELP = dcl("HELP", ReActionHandler.HELP, IconManager.ICONS.help);
	public static StandardActionId COPY_SELECTED_CELLS = dcl("COPY_SELECTED_CELLS", ReActionHandler.COPY_SELECTED_CELLS);
	public static StandardActionId COPY_RECORD = dcl("COPY_RECORD", ReActionHandler.COPY_RECORD, IconManager.ICONS.editCopy);
	public static StandardActionId CUT_RECORD = dcl("CUT_RECORD", ReActionHandler.CUT_RECORD, IconManager.ICONS.editCut);
	public static StandardActionId CUT_SELECTED_CELLS = dcl("CUT_SELECTED_CELLS", ReActionHandler.CUT_SELECTED_CELLS);
	public static StandardActionId CLEAR_SELECTED_CELLS = dcl("CLEAR_SELECTED_CELLS", ReActionHandler.CLEAR_SELECTED_CELLS);
	public static StandardActionId PASTE_RECORD = dcl("PASTE_RECORD", ReActionHandler.PASTE_RECORD, IconManager.ICONS.editPaste);
	public static StandardActionId PASTE_RECORD_PRIOR = dcl("PASTE_RECORD_PRIOR", ReActionHandler.PASTE_RECORD_PRIOR);
	public static StandardActionId PASTE_RECORD_POPUP = dcl("PASTE_RECORD_POPUP", ReActionHandler.PASTE_RECORD_POPUP);
	public static StandardActionId PASTE_RECORD_PRIOR_POPUP = dcl("PASTE_RECORD_PRIOR_POPUP", ReActionHandler.PASTE_RECORD_PRIOR_POPUP);
	public static StandardActionId PASTE_TABLE_OVER_SELECTION = dcl("PASTE_TABLE_OVER_SELECTION", ReActionHandler.PASTE_TABLE_OVER_SELECTION);
	public static StandardActionId PASTE_TABLE_OVERWRITE = dcl("PASTE_TABLE_OVERWRITE", ReActionHandler.PASTE_TABLE_OVERWRITE);
	public static StandardActionId PASTE_TABLE_INSERT = dcl("PASTE_TABLE_INSERT", ReActionHandler.PASTE_TABLE_INSERT);
	public static StandardActionId PASTE_INSERT_CELLS = dcl("PASTE_INSERT_CELLS", ReActionHandler.PASTE_INSERT_CELLS);
	public static StandardActionId INSERT_RECORDS = dcl("INSERT_RECORDS", ReActionHandler.INSERT_RECORDS);
	public static StandardActionId DELETE_RECORD = dcl("DELETE_RECORD", ReActionHandler.DELETE_RECORD);
	public static StandardActionId DELETE_SELECTED_CELLS = dcl("DELETE_SELECTED_CELLS", ReActionHandler.DELETE_SELECTED_CELLS);
	public static StandardActionId SORT = dcl("SORT", ReActionHandler.SORT, IconManager.ICONS.sort);
	public static StandardActionId REPEAT_RECORD_POPUP = dcl("REPEAT_RECORD_POPUP", ReActionHandler.REPEAT_RECORD_POPUP);
	public static StandardActionId NEXT_RECORD = dcl("NEXT_RECORD", ReActionHandler.NEXT_RECORD, IconManager.ICONS.goNext);
	public static StandardActionId PREVIOUS_RECORD = dcl("PREVIOUS_RECORD", ReActionHandler.PREVIOUS_RECORD, IconManager.ICONS.goPrevious);
	public static StandardActionId CREATE_CHILD = dcl("CREATE_CHILD", ReActionHandler.CREATE_CHILD);
	public static StandardActionId EDIT_CHILD = dcl("EDIT_CHILD", ReActionHandler.EDIT_CHILD);
	public static StandardActionId PRINT = dcl("PRINT", ReActionHandler.PRINT);
	public static StandardActionId PRINT_SELECTED = dcl("PRINT_SELECTED", ReActionHandler.PRINT_SELECTED);
	public static StandardActionId REBUILD_TREE = dcl("REBUILD_TREE", ReActionHandler.REBUILD_TREE);

	public static StandardActionId ADD_ATTRIBUTES = dcl("ADD_ATTRIBUTES", ReActionHandler.ADD_ATTRIBUTES);
	public static StandardActionId FULL_TREE_REBUILD = dcl("FULL_TREE_REBUILD", ReActionHandler.FULL_TREE_REBUILD);
	public static StandardActionId EXECUTE_SAVED_FILTER = dcl("EXECUTE_SAVED_FILTER", ReActionHandler.EXECUTE_SAVED_FILTER);
	public static StandardActionId EXECUTE_SAVED_SORT_TREE = dcl("EXECUTE_SAVED_SORT_TREE", ReActionHandler.EXECUTE_SAVED_SORT_TREE);
	public static StandardActionId EXECUTE_SAVED_RECORD_TREE = dcl("EXECUTE_SAVED_RECORD_TREE", ReActionHandler.EXECUTE_SAVED_RECORD_TREE);
	public static StandardActionId COMPARE_WITH_DISK = dcl("COMPARE_WITH_DISK", ReActionHandler.COMPARE_WITH_DISK);

	public static StandardActionId SHOW_INVALID_ACTIONS = dcl("SHOW_INVALID_ACTIONS", ReActionHandler.SHOW_INVALID_ACTIONS);
	public static StandardActionId AUTOFIT_COLUMNS = dcl("AUTOFIT_COLUMNS", ReActionHandler.AUTOFIT_COLUMNS);
	public static StandardActionId INSERT_RECORD_PRIOR = dcl("INSERT_RECORD_PRIOR", ReActionHandler.INSERT_RECORD_PRIOR);
	public static StandardActionId EXPORT_XSLT = dcl("EXPORT_XSLT", ReActionHandler.EXPORT_XSLT);
	public static StandardActionId EXPORT = dcl("EXPORT", ReActionHandler.EXPORT);
	public static StandardActionId EXPORT_SCRIPT = dcl("EXPORT_SCRIPT", ReActionHandler.EXPORT_SCRIPT);
	public static StandardActionId RUN_SCRIPT = dcl("RUN_SCRIPT", ReActionHandler.RUN_SCRIPT, IconManager.ICONS.run);
	public static StandardActionId REFRESH = dcl("REFRESH", ReActionHandler.REFRESH);
	public static StandardActionId CLOSE_TAB = dcl("CLOSE_TAB", ReActionHandler.CLOSE_TAB);
	public static StandardActionId UNDOCK_TAB = dcl("UNDOCK_TAB", ReActionHandler.UNDOCK_TAB);
	public static StandardActionId UNDOCK_ALL_TABS = dcl("UNDOCK_ALL_TABS", ReActionHandler.UNDOCK_ALL_TABS);
	public static StandardActionId DOCK_TAB = dcl("DOCK_TAB", ReActionHandler.DOCK_TAB);
	public static StandardActionId DOCK_ALL_SCREENS = dcl("DOCK_ALL_SCREENS", ReActionHandler.DOCK_ALL_SCREENS);
	public static StandardActionId REMOVE_CHILD_SCREEN = dcl("REMOVE_CHILD_SCREEN", ReActionHandler.REMOVE_CHILD_SCREEN);
	public static StandardActionId ADD_CHILD_SCREEN = dcl("ADD_CHILD_SCREEN", ReActionHandler.ADD_CHILD_SCREEN);
	public static StandardActionId ADD_CHILD_SCREEN_RIGHT = dcl("ADD_CHILD_SCREEN_RIGHT", ReActionHandler.ADD_CHILD_SCREEN_RIGHT);
	public static StandardActionId ADD_CHILD_SCREEN_BOTTOM = dcl("ADD_CHILD_SCREEN_BOTTOM", ReActionHandler.ADD_CHILD_SCREEN_BOTTOM);
	public static StandardActionId ADD_CHILD_SCREEN_SWAP = dcl("ADD_CHILD_SCREEN_SWAP", ReActionHandler.ADD_CHILD_SCREEN_SWAP);
	public static StandardActionId INSERT_RECORDS_POPUP = dcl("INSERT_RECORDS_POPUP", ReActionHandler.INSERT_RECORDS_POPUP);
	public static StandardActionId INSERT_RECORD_PRIOR_POPUP = dcl("INSERT_RECORD_PRIOR_POPUP", ReActionHandler.INSERT_RECORD_PRIOR_POPUP);
	public static StandardActionId DELETE_RECORD_POPUP = dcl("DELETE_RECORD_POPUP", ReActionHandler.DELETE_RECORD_POPUP);
	public static StandardActionId DELETE_BUTTON = dcl("DELETE_BUTTON", ReActionHandler.DELETE_BUTTON, IconManager.ICONS.editDelete);
	
	private static StandardActionId dcl(String name, int code) {
		return dcl(name, code, null);
	}
	private static StandardActionId dcl(String name, int code, IIconHolder icon) {
		StandardActionId action = new StandardActionId(name, code, icon);
		
		standardActionList.add(action);
		
		return action;
	}
	
	private final String name;
	private final int code;
	private final IIconHolder iconDetails;

	
	public StandardActionId(String name, int code, IIconHolder iconDtls) {

		super();
		
		this.name = name;
		this.code = code;
		this.iconDetails = iconDtls;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getActionIntCode() {
		return code;
	}
	/**
	 * @return the iconDetails
	 */
	public IIconHolder getIconDetails() {
		return iconDetails;
	}

	/*----------****************************************
	public static void main(String[] args) {
		String[] names = {
				
				"SAVE_AS",
				"EXPORT_AS_HTML",
				"EXPORT_AS_HTML_TBL_PER_ROW",
				"EXPORT_VELOCITY",
				"SAVE_AS_XML",
				"SAVE_LAYOUT_XML",
				"EXPORT_HTML_TREE",
				"EXPORT_AS_CSV",
				"EXPORT_AS_FIXED",
				"DELETE",
				"FIND",
				"FILTER",
				"OPEN",
				"CLOSE",
				"NEW",
				"CORRECT_RECORD_LENGTH",
				"TABLE_VIEW_SELECTED",
				"RECORD_VIEW_SELECTED",
				"COLUMN_VIEW_SELECTED",
				"SELECTED_VIEW",
				"BUILD_FIELD_TREE",
				"BUILD_SORTED_TREE",
				"BUILD_RECORD_TREE",
				"BUILD_LAYOUT_TREE",
				"BUILD_XML_TREE_SELECTED",
				"BUILD_LAYOUT_TREE_SELECTED",
				"HELP",
				"COPY_SELECTED_CELLS",
				"COPY_RECORD",
				"CUT_RECORD",
				"CUT_SELECTED_CELLS",
				"CLEAR_SELECTED_CELLS",
				"PASTE_RECORD",
				"PASTE_RECORD_PRIOR",
				"PASTE_RECORD_POPUP",
				"PASTE_RECORD_PRIOR_POPUP",
				"PASTE_TABLE_OVER_SELECTION",
				"PASTE_TABLE_OVERWRITE",
				"PASTE_TABLE_INSERT",
				"PASTE_INSERT_CELLS",
				"",
				"INSERT_RECORDS",
				"DELETE_RECORD",
				"DELETE_SELECTED_CELLS",
				"SORT",
				"REPEAT_RECORD_POPUP",
				"NEXT_RECORD",
				"PREVIOUS_RECORD",
				"CREATE_CHILD",
				"EDIT_CHILD",
				"PRINT",                                    
				"PRINT_SELECTED",
				"REBUILD_TREE",          
	

			"ADD_ATTRIBUTES",
			"FULL_TREE_REBUILD",
			"EXECUTE_SAVED_FILTER",
			"EXECUTE_SAVED_SORT_TREE",
			"EXECUTE_SAVED_RECORD_TREE",
			"COMPARE_WITH_DISK",
			"",
			"SHOW_INVALID_ACTIONS",
			"AUTOFIT_COLUMNS",
			"INSERT_RECORD_PRIOR",
			"EXPORT_XSLT",
			"EXPORT",
			"EXPORT_SCRIPT",
			"RUN_SCRIPT",
			"REFRESH",
			"CLOSE_TAB",
			"UNDOCK_TAB",
			"UNDOCK_ALL_TABS",
			"DOCK_TAB",
			"DOCK_ALL_SCREENS",
			"REMOVE_CHILD_SCREEN",
			"ADD_CHILD_SCREEN",
			"ADD_CHILD_SCREEN_RIGHT",
			"ADD_CHILD_SCREEN_BOTTOM",
			"ADD_CHILD_SCREEN_SWAP",
			"INSERT_RECORDS_POPUP",
			"INSERT_RECORD_PRIOR_POPUP",
			"DELETE_RECORD_POPUP",
			"DELETE_BUTTON",
		};
		for (String n : names) {
			System.out.println("\t\tpublic static StandardAction " + n + " = dcl(\"" + n + "\", ReActionHandler." + n + ");");
		}
	}
	*****/
}
