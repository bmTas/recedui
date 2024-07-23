package com.github.bmTas.recedui.grid;

import java.util.List;

public interface IDataTable<Item> {

	public int getRowCount();
	public int getColumnCount();

	public Item get(int row, int column);

	public List<Item> getRow(int row);
}
