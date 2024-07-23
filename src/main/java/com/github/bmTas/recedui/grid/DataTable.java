package com.github.bmTas.recedui.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataTable<Item> implements IDataTable<Item> {

	private final List<List<Item>> tbl;
	
	private final int rowCount, colCount;

	public DataTable(Item[][] table) {
		this(convertToList(table));
	}
	public DataTable(List<List<Item>> table) {
		super();
		this.tbl = table;
		
		rowCount = tbl.size();
		int cc = 0;
		for (List<Item> itms : tbl) {
			cc = Math.max(cc, itms.size());
		}
		colCount = cc;
	}
	
	public DataTable(IDataTable<Item> table) {
		super();
		
		this.rowCount = table.getRowCount();
		this.colCount = table.getColumnCount();
		this.tbl = new ArrayList<List<Item>>(rowCount);

		for (int i = 0; i < rowCount; i++) {
			tbl.add(new ArrayList<Item>(table.getRow(i)));
		}
	}

	private  static <Itm> List<List<Itm>> convertToList(Itm[][] tbl) {
		ArrayList<List<Itm>> table = new ArrayList<List<Itm>>();
		
		for (Itm[] dataRow : tbl) {
			table.add(Arrays.asList(dataRow));
		}
		
		return table;
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return colCount;
	}

	@Override
	public Item get(int row, int column) {
		StringBuilder b = new StringBuilder();
		if (row < 0 || row > rowCount) {
			b.append("\nInvalid Row : ").append(row).append("it should be between 0 and ").append(rowCount);
		}
		if (column < 0 || column > colCount) {
			b.append("\nInvalid Column : ").append(column).append("; it should be between 0 and ").append(colCount);
		}
		if (b.length() > 0) { throw new RuntimeException(b.toString());	}
		
		return tbl.get(row).size() > column ? tbl.get(row).get(column) : null;
	}
	
	@Override
	public List<Item> getRow(int row) {
		if (row < 0 || row > rowCount) {
			throw new RuntimeException("\nInvalid Row : "+ row+"; it should be between 0 and "+rowCount);
		}
		return new ArrayList<Item>(tbl.get(row));
	}
	
	
}
