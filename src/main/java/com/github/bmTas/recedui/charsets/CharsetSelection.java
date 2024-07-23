package com.github.bmTas.recedui.charsets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.github.bmTas.recedui.common.SetVisible;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IGetContainer;
import com.github.bmTas.recedui.form.FormBuilder;
import com.github.bmTas.recedui.form.FormHelper;
import com.github.bmTas.recedui.form.TwoColumnFormBuilder;
import com.github.bmTas.recedui.frame.UiDialog;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.tableCell.ButtonTableRendor;
import com.github.bmTas.recedui.text.UiTextField;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class CharsetSelection implements IGetContainer {

	private static String[] COLUMN_NAMES = {"", "Character set", "Description"};
	
	private String charset;
	private UiDialog dialog;
	//private JDialog dialog;
	private JPanel pnl;
	private TblMdl tblMdl = new TblMdl();

	private UiTextField charsetTxt = new UiTextField(StyleManager.styles().panelField());
	
	private ButtonTableRendor tableBtn = new ButtonTableRendor();
	private JTable tbl = new JTable(tblMdl);
	//private JButton goBtn = new JButton("Go");
	
	public CharsetSelection(Frame owner, String characterSet, ISetVisible setVisible) {
		this.charset = characterSet;
		this.charsetTxt.setText(charset);
		setVisible = SetVisible.getSetVisible(setVisible);

        TableColumn tc = tbl.getColumnModel().getColumn(0);
        tc.setCellRenderer(tableBtn);
        tc.setPreferredWidth(5);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(16 * SwingUtils.values().CHAR_FIELD_WIDTH);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(59 * SwingUtils.values().CHAR_FIELD_WIDTH);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TwoColumnFormBuilder form = FormBuilder.twoColumnForm("FontPanel")	.form();
		pnl = form
					.line("Selected Encoding", charsetTxt)
							.setGap(FormHelper.GAP2)
					.incRow()
					.cellSwing(new JScrollPane(tbl), FormBuilder.RIGHT, 2, form.getLabelColumn(), form.getLastFieldColumn())
					
				.build();
//		pnl.addLineRE("Current Encoding", charsetTxt)
//			.setGapRE(BasePanel.GAP1);
//		
//	    pnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
//                   BasePanel.FULL, BasePanel.FULL,
//                   tbl);
	   
	
		dialog = new UiDialog(UiDialog.newParamBldr(this)
							.setParentContainer(owner)
							.setModality(ModalityType.APPLICATION_MODAL)
							.setVisableAction(setVisible));
//				owner, true);
//		dialog.getContentPane().add(pnl);
		
		charsetTxt.getMainComponent().setEditable(false);
		
		if (characterSet != null && characterSet.length() > 0) {
			for (int i = 0; i < tblMdl.charsets.size(); i++) {
				CharsetDtls charsetDtls = tblMdl.charsets.get(i);
				if (characterSet.equalsIgnoreCase(charsetDtls.id)) {
					HighlightRender cellRenderer = new HighlightRender(characterSet, charsetDtls.description);
					tbl.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
					tbl.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
					break;
				}
			}
		}
		
		tbl.addMouseListener(new MouseAdapter() {
	           @Override public void mousePressed(MouseEvent m) {
	                int row = tbl.rowAtPoint(m.getPoint());

	                accept(tblMdl.charsets.get(row).id);
	           }
		});
		
		

        dialog.setVisible(true);
	}
	
	@Override
	public Component getGuiContainer() {
		return pnl;
	}

	private void accept(String c) {
		charset = c;
		dialog.setVisible(false);
	}
	
	/**
	 * @return the charset
	 */
	public final String getCharset() {
		return charset;
	}

	@SuppressWarnings("serial")
	private static class TblMdl extends AbstractTableModel {

		private List<CharsetDtls> charsets = CharsetMgr.getCharsets();
		
		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return charsets.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 3;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0: return "";
			case 1:	return charsets.get(rowIndex).id;
			default:
				return charsets.get(rowIndex).description;
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}
		
	}
	
	private static class HighlightRender  implements TableCellRenderer {
		private final JTextField fld = new JTextField();
		private final String valueToHighlight, description;
		//private final Color foregroundColor;
		private final Color background = new Color(255, 255, 128);
		

		public HighlightRender(String valueToHighlight, String description) {
			super();
			this.valueToHighlight = valueToHighlight;
			this.description = description;
			fld.setBorder(null);
		}



		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			String val = value == null ? "" : value.toString();
			fld.setText(val);
			if (val.equalsIgnoreCase(valueToHighlight) || val.equals(description)) {
				fld.setBackground(background);
			} else {
				SwingUtils.values().setTableCellBackGround(fld, table, row, isSelected);
			}
			return fld;
		}
		
		
	}
}
