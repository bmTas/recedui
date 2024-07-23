package com.github.bmTas.recedui.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CommonTableCode {
	
	private static final int TABLE_WINDOW_SIZE_TO_CHECK = 200;
	private static final int MINIMUM_MAX_COLUMN_WIDTH   = 100;
	private static final int DEFAULT_ROOM_AROUND_SCREEN = 34;


	public static void stopCellEditing(JTable table) {
		if (table != null) {
		   	try {
	    		TableCellEditor cellEdit = table.getCellEditor();
	    		if (cellEdit != null) {
	    			cellEdit.stopCellEditing();
	    		}
	    	} catch (Exception e) { }
		}
	}
	
	   /**
     * Adjusts the column widths based on the component widths
     * @param table table to fix
     * @param minColumns minumum number of columns in table (
     */
    public static void calcColumnWidths(JTable table, int minColumns) {
    	calcColumnWidths(table, minColumns, -1, 0);
    }

    public static void calcColumnWidths(JTable table, int minColumns, int maxColWidth) {
    	calcColumnWidths(table, minColumns, maxColWidth, 0);
    }

    public static void calcColumnWidths(JTable table, int minColumns, int maxColWidth, int fieldWidthAddition) {
    	if (maxColWidth < 0) {
    		int screenWidth = table.getVisibleRect().width;
        	maxColWidth = Math.max(screenWidth * 2 / 3, MINIMUM_MAX_COLUMN_WIDTH);
     	}
        JTableHeader header = table.getTableHeader();

        TableCellRenderer defaultHeaderRenderer = null;

        if (header != null) {
            defaultHeaderRenderer = header.getDefaultRenderer();
        }

        TableColumnModel columns = table.getColumnModel();
        TableModel data = table.getModel();

        int margin = Math.min(4, columns.getColumnMargin()); // only JDK1.3
        Rectangle visbleRect = table.getVisibleRect();
        int screenWidth = visbleRect.width;
        int screenStart = visbleRect.y;
//        int screenheight = visbleRect.height;

        if (screenWidth == 0) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            screenWidth = dim.width - DEFAULT_ROOM_AROUND_SCREEN;
//            screenheight = dim.height - DEFAULT_ROOM_AROUND_SCREEN;
        }
        int firstRow = Math.max(0, screenStart - TABLE_WINDOW_SIZE_TO_CHECK);
        int rowCount = Math.min(data.getRowCount(), 2 * TABLE_WINDOW_SIZE_TO_CHECK);
        
        //System.out.println("First Row: " + firstRow + "\t\tRow Count: " + rowCount);
        //System.out.println(screenStart + " " + screenheight);
        //int totalWidth = 0;
        //System.out.println("Column Widths ==> " + visbleRect.width + " " + maxColWidth);

        //System.out.println();
        TableColumn column;
        for (int i = columns.getColumnCount() - 1; i >= 0; --i) {
            column = columns.getColumn(i);

            int width = -1;

            TableCellRenderer h = column.getHeaderRenderer();

            if (h == null) {
                h = defaultHeaderRenderer;
            }

            if (h != null) {
                Component c = h.getTableCellRendererComponent(table, column
                        .getHeaderValue(), false, false, -1, i);

                width = c.getPreferredSize().width;
            }

            width = getColumnWidth(table, i, firstRow, rowCount, width, fieldWidthAddition);
//            for (int row = rowCount - 1; row >= firstRow; --row) {
//                TableCellRenderer r = table.getCellRenderer(row, i);
//
//                try {
//	               Component c = r.getTableCellRendererComponent(
//	                		table,
//	                		data.getValueAt(row, columnIndex), false, false, row, i);
//                	width = Math.max(width, c.getPreferredSize().width);
//                } catch (Exception e) {
//                	System.out.println("Error Row,col= " + row + ", " + columnIndex);
//				}
//            }

            if (width >= 0) {
                //System.out.println("### " + columns.getColumnCount() + " " + i + " Width=" + width);
                if (columns.getColumnCount() == minColumns) {
                    column.setPreferredWidth(width + margin); // <1.3: without margin
                } else {
                    column.setPreferredWidth(Math.min(width + margin, maxColWidth)); // <1.3: without margin
                }

                //column.setPreferredWidth(width + margin);
//                if (width > maxColWidth) {
//                    System.out.println("~~ " + width + " " + maxColWidth);
//                }
            }

            //totalWidth += column.getPreferredWidth();
        }
    }
    

    public static int getColumnWidth(JTable table, int idx, int firstRow, int rowCount, int defaultWidth) {
    	return getColumnWidth(table, idx, firstRow, rowCount, defaultWidth, 0);
    }

    public static int getColumnWidth(JTable table, int idx, int firstRow, int rowCount, int defaultWidth, int incBy) {
   	int width= defaultWidth;
    	int columnIndex = table.getColumnModel().getColumn(idx).getModelIndex();
    	TableModel data = table.getModel();

//    	System.out.println();
//    	System.out.println(" ~~~ idx = " + idx);
//    	System.out.println();
        for (int row = rowCount - 1; row >= firstRow; --row) {
            TableCellRenderer r = table.getCellRenderer(row, idx);

            try {
            	Component c = r.getTableCellRendererComponent(
                		table,
                		data.getValueAt(row, columnIndex), false, false, row, idx);
                //System.out.print("  >" + width + " " + c.getPreferredSize().width);
            	width = Math.max(width, c.getPreferredSize().width + incBy);
            } catch (Exception e) {
            	System.out.println("Error Row,col= " + row + ", " + columnIndex);
			}
        }
        return width;
   }


}
