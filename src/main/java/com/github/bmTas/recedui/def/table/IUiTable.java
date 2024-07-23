package com.github.bmTas.recedui.def.table;

import javax.swing.table.TableModel;

import com.github.bmTas.recedui.def.component.IUiComponent;

/**
 * Describes a standard Table Component
 * @author Bruce Martin
 *
 */
public interface IUiTable extends IUiComponent, IUiBasicTable {
	void setModel(TableModel dataModel);
}
