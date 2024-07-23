package com.github.bmTas.recedui.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTable<Item> implements IDataTable<Item> {

	public static  <Item> ListTable<Item> horizontalTable(Item[] componentlist) {
		return new ListTable<Item>(1, componentlist.length, componentlist);
	}
	
	public static  <Item> ListTable<Item> verticallTable(Item[] componentlist) {
		return new ListTable<Item>(componentlist.length, 1, componentlist);
	}
	
	private final int rowCount, columnCount;
	final List<Item> itemlist;

	public ListTable(int rowCount, int columnCount, Item[] componentlist) {
		this(rowCount, columnCount, Arrays.asList(componentlist));
	}

	public ListTable(int rowCount, int columnCount, List<Item> componentlist) {
		super();
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.itemlist = componentlist;
	}
	
	public ListTable(ListTable<Item> grid) {
		this.rowCount = grid.rowCount;
		this.columnCount = grid.columnCount;
		
		this.itemlist = new ArrayList<Item>(grid.itemlist);
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public Item get(int row, int column) {
		if (row < 0 || column < 0) { throw new RuntimeException("Row and column must be >= 0; but they where: " + row + ", " + column);}
		
		int idx = row * columnCount + column;
		return idx >= itemlist.size() || column >= columnCount ? null : itemlist.get(idx);
	}

	@Override
	public List<Item> getRow(int row) {
		
		int fromIndex = row*columnCount;
		return itemlist.subList(fromIndex, fromIndex + columnCount);
	}

}
