package com.github.bmTas.recedui.treeTable;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.component.IGetJScrollPaneContainer;
import com.github.bmTas.recedui.def.table.IUiBasicTable;
import com.github.bmTas.recedui.def.table.IUiTableMouseListner;
import com.github.bmTas.recedui.styles.ITableStyle;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.swing.treeTable.ITreeTableModel;
import com.github.bmTas.recedui.swing.treeTable.JTreeTable;
import com.github.bmTas.recedui.table.CommonTableCode;
import com.github.bmTas.recedui.table.event.IGetListnerManager;
import com.github.bmTas.recedui.table.event.TableMouseListnerManager;

public class UiScrollTreeTable extends UiStdComponent2<JScrollPane, JTreeTable> 
implements IUiBasicTable, IGetJScrollPaneContainer, IGetListnerManager {
//	public static TableParam newParam() { return new TableParam(); }
	
	
	private final TableMouseListnerManager mouseListnerManager;



	public UiScrollTreeTable(ITreeTableModel treeTableModel) {
		this(null, new JTreeTable(treeTableModel));
	}


	public UiScrollTreeTable(ITableStyle style, ITreeTableModel treeTableModel) {
		this(style, new JTreeTable(treeTableModel));
	}


	public UiScrollTreeTable(ITableStyle style, JTreeTable treeTable) {
		super(style == null ? StyleManager.EMPTY_STYLE : style, new JScrollPane(treeTable), treeTable); 
		mouseListnerManager = new TableMouseListnerManager(treeTable);
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
}
