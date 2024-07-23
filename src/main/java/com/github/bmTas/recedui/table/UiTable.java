package com.github.bmTas.recedui.table;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.table.IUiTable;
import com.github.bmTas.recedui.def.table.IUiTableMouseListner;
import com.github.bmTas.recedui.styles.ITableStyle;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.table.event.IGetListnerManager;
import com.github.bmTas.recedui.table.event.TableMouseListnerManager;

public class UiTable extends UiStdComponent<JTable> implements IUiTable, IGetListnerManager {
	public static TableParam newParam() { return new TableParam(); }

	//private final List<IUiTableMouseListner> mouseListners = new ArrayList<>(2);
	
	private final TableMouseListnerManager mouseListnerManager;

	public UiTable() {
		this(StyleManager.EMPTY_STYLE, new JTable());
	}

	public UiTable(ITableParam param) {
		this(param.getStyle(), param.getTable());
	}

	//JTable component;
	public UiTable(ITableStyle style, JTable table) {
		super(style == null ? StyleManager.EMPTY_STYLE : style, table);
		
		if (style != null) {
			style.applyTable(this);
		}
		
		mouseListnerManager = new TableMouseListnerManager(table);
	}

	@Override
	public void setModel(TableModel dataModel) {
		component.setModel(dataModel);
	}
	@Override
	public TableModel getModel() {
		return component.getModel();
	}
	public void setColumnModel(TableColumnModel columnModel) {
		component.setColumnModel(columnModel);
	}
	@Override
	public TableColumnModel getColumnModel() {
		return component.getColumnModel();
	}
	
	@Override
	public void setAutoResizeMode(int mode) {
		component.setAutoResizeMode(mode);
	}

	
	@Override
	public void terminateEditOnFocusLost(boolean terminateOption) {
		component.putClientProperty("terminateEditOnFocusLost",  Boolean.valueOf(terminateOption));
	}
	

	@Override
	public void addTableMouseListner(IUiTableMouseListner listner) {
		mouseListnerManager.add(listner);
	}
	
	@Override
	public void removeTableMouseListner(Object listner) {
		mouseListnerManager.remove(listner);
	}

	
	@Override
	public TableMouseListnerManager getMouserListnerManager() {
		return mouseListnerManager;
	}

	@Override
	public void stopCellEditing() {
		CommonTableCode.stopCellEditing(component);
	}
}
