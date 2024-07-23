package com.github.bmTas.recedui.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataTableBuilder<Item> {
	
	private ArrayList<List<Item>> table = new ArrayList<>();

	public DataTableBuilder<Item> addRow(Item[] rowContents) {
		table.add(Arrays.asList(rowContents));
		return this;
	}
	
	public DataTableBuilder<Item> addRow(List<Item> rowContents) {
		table.add(rowContents);
		return this;
	}
	
	public DataTableBuilder<Item> newRow() {
		table.add(new ArrayList<>());
		return this;
	}
	
	public DataTableBuilder<Item> addToRow(Item item) {
		if (table.size() == 0) {
			table.add(new ArrayList<>());
		}
		table.get(table.size() - 1).add(item);
		return this;
	}

	public IDataTable<Item> build() {
		return new DataTable<>(table);
	}
}
