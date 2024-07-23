package com.github.bmTas.recedui.grid;

import java.util.List;

public class TableBuilders {

	/**
	 * Create a table consisting of a single row
	 * @param <Item> class of items in the table
	 * @param rowData the row in the table
	 * @return requested table
	 */
	public static <Item> IDataTable<Item> newSingleRowTable(Item[] rowData) { 
		return new ListTable<>(1, rowData.length, rowData); 
	}
	
	/**
	 * Create a table consisting of a single row
	 * @param <Item> class of items in the table
	 * @param rowData the row in the table
	 * @return requested table
	 */
	public static <Item> IDataTable<Item> newSingleRowTable(List<Item> rowData) { 
		return new ListTable<>(1, rowData.size(), rowData); 
	}


	/**
	 * Create a table consisting of a single column
	 * @param <Item> class of items in the table
	 * @param columnData the single column in the table
	 * @return requested table
	 */
	public static <Item> IDataTable<Item> newSingleColumnTable(Item[] columnData) { 
		return new ListTable<>(columnData.length, 1, columnData); 
	}
	
	public static <Item> IDataTable<Item> newSingleColumnTable(List<Item> columnData) { 
		return new ListTable<>(columnData.size(), 1, columnData); 
	}


	public static <Item> DataTableBuilder<Item> tableBldr(Class<Item> c) { 
		return new DataTableBuilder<Item>(); 
	}

	public static <Item> DataTableBuilder<Item> tableBldr(List<Item> firstRow) { 
		return new DataTableBuilder<Item>()	.addRow(firstRow); 
	}

	public static <Item> DataTableBuilder<Item> tableBldr(Item[] firstRow) { 
		return new DataTableBuilder<Item>()	.addRow(firstRow); 
	}

}
