package com.github.bmTas.recedui.table;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.table.IProcessTableMousePressed;
import com.github.bmTas.recedui.def.table.IUiScrollTable;
import com.github.bmTas.recedui.def.table.IUiTableMouseListner;
import com.github.bmTas.recedui.styles.ITableStyle;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.table.event.IGetListnerManager;
import com.github.bmTas.recedui.table.event.TableMouseListnerManager;
import com.github.bmTas.recedui.table.event.TableMousePressed;

public class UiScrollTable extends UiStdComponent2<JScrollPane, JTable> implements IUiScrollTable, IGetListnerManager {
	public static TableParam newParam() { return new TableParam(); }
	
	
	private final TableMouseListnerManager mouseListnerManager;

//	private final List<IUiTableMouseListner> mouseListners = new ArrayList<>(2);

	public UiScrollTable() {
		this(null, new JTable());
	}
	
	public UiScrollTable(ITableStyle style) {
		this(style, new JTable());
	}


	public UiScrollTable(ITableParam param) {
		this(param.getStyle(), param.getTable());
	}


	public UiScrollTable(ITableStyle style, JTable table) {
		super(style == null ? StyleManager.EMPTY_STYLE : style, new JScrollPane(table), table); 
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
		component.putClientProperty("terminateEditOnFocusLost", Boolean.valueOf(terminateOption) );
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
	
	public void addMousePressed(IProcessTableMousePressed mousePressed) {
		super.component.addMouseListener(new TableMousePressed(this, mousePressed));
	}
}
