/*
 * @Author Bruce Martin
 * Created on 9/02/2007
 *
 * Purpose:
 */
package com.github.bmTas.recedui.tableCell;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.github.bmTas.recedui.def.table.ITableCellEditAndRender;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;


/**
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class CheckboxTableRenderStringBased extends AbstractCellEditor
    implements ITableCellEditAndRender {

    private JCheckBox checkBox = new JCheckBox();
    private String yesStr;
    private String noStr;
    private boolean defaultVal;
    private boolean ignoreCase;

    /**
     * Create a Checkbox table render based on Strings
     * @param yes yes string
     * @param no no string
     * @param defaultValue default value
     */
    public CheckboxTableRenderStringBased(final String yes,
            final String no, final boolean defaultValue, boolean ignoreTheCase) {

        yesStr = yes;
        noStr  = no;
        defaultVal = defaultValue;
        ignoreCase = ignoreTheCase;
    }
    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
     * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(
            JTable tbl,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        boolean val = defaultVal;

        try {
            if (value != null) {
                String s = value.toString();
                if (ignoreCase) {
                	if (s.equals(yesStr)) {
                		val = true;
                	} else if (s.equals(noStr)) {
                		val = false;
                	}
                } else {
                	if (s.equalsIgnoreCase(yesStr)) {
                		val = true;
                	} else if (s.equalsIgnoreCase(noStr)) {
                		val = false;
                	}
                }
            }
        } catch (Exception e) {
        }
        checkBox.setSelected(val);

        SwingUtils.values().setTableCellBackGround(checkBox, tbl, row, isSelected);

        return checkBox;
    }



    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        return checkBox;
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        if (checkBox.isSelected()) {
            return yesStr;
        }
        return noStr;
    }
}
