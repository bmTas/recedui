package com.github.bmTas.recedui.fileFields.popup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;


/**
 * Edit the favourite Table
 * 
 * @author Bruce Martin
 *
 */
public class FavouriteEditor  {
	private final JDialog dialog;
	private final JTable favTable;
	private final ItemListMdl favMdl;
	private final IUpdateableList itemList;
	
	private boolean changed = false;
	
	@SuppressWarnings("serial")
	private final JButton deleteButton   = new JButton(new AbstractAction("Delete") {
		@Override public void actionPerformed(ActionEvent e) {
			int[] selRows = favTable.getSelectedRows();
			if (selRows != null && selRows.length > 0) {
				for (int i = selRows.length - 1; i >= 0; i--) {
					favMdl.itemList.remove(selRows[i]);
				}
				favTable.clearSelection();
				favTable.addRowSelectionInterval(selRows[0] + 1, selRows[selRows.length-1] + 1);
				changed = true;
				favMdl.fireTableDataChanged();
			}
		}
	});
	@SuppressWarnings("serial")
	private final JButton moveUpButton   = new JButton(new AbstractAction("Move Up") {
		@Override public void actionPerformed(ActionEvent e) {
			int[] selRows = favTable.getSelectedRows();
			if (selRows != null && selRows.length > 0 && selRows[0] > 0) {
				for (int i = 0; i < selRows.length; i++) {
					favMdl.itemList.add(
							selRows[i] - 1,
							favMdl.itemList.remove(selRows[i]));
				}
				tblUpdate(selRows, -1);
			}
		}
	});
	@SuppressWarnings("serial")
	private final JButton moveDownButton = new JButton(new AbstractAction("Move Down") {
		@Override public void actionPerformed(ActionEvent e) {
			int[] selRows = favTable.getSelectedRows();
			if (selRows != null && selRows.length > 0 && selRows[selRows.length - 1] < favMdl.itemList.size() - 1) {
				for (int i = selRows.length - 1; i >= 0; i--) {
					favMdl.itemList.add(
							selRows[i] + 1,
							favMdl.itemList.remove(selRows[i]));
				}
				tblUpdate(selRows, 1);	
			}
		}
	});
	@SuppressWarnings("serial")
	private final JButton saveButton     = new JButton(new AbstractAction("Save") {
		@Override public void actionPerformed(ActionEvent e) {
			saveTbl();
		}
	});

	/**
	 * Edit Favorite list
	 * @param owner dialog owner
	 * @param list lit to edit
	 */
	public FavouriteEditor(JComponent owner, IUpdateableList list) {
		this.itemList = list;
		this.favMdl = new ItemListMdl(list.getItemList());
		this.favTable = new JTable(favMdl);
		
		dialog = new JDialog(SwingUtilities.getWindowAncestor(owner), "Favourite " + list.getListName() + " Editor");
		
		layoutScreen();
		addListners();
	}
	/**
	 * 
	 */
	private void layoutScreen() {
		JPanel pnl = new JPanel();
		JPanel btnPnl = new JPanel(new GridLayout(1, 4));
//		Insets margin = btnPnl.getMargin();
		
		
//		btnPnl.setMargin(margin.top + 7, margin.left + 5, margin.bottom + 15, margin.right + 5);
		
		btnPnl.add(deleteButton);
		btnPnl.add(moveUpButton);
		btnPnl.add(moveDownButton);
		btnPnl.add(saveButton);
		
		pnl.add(btnPnl,   BorderLayout.NORTH);
		pnl.add(new JScrollPane(favTable), BorderLayout.CENTER);
		
		//Dimension preferredSize = dialog.getPreferredSize();
//		dialog.setPreferredSize(new Dimension(Math.max(preferredSize.width, 800), preferredSize.height));
		dialog.getContentPane().add(pnl);
		dialog.pack();
		
		//preferredSize = dialog.getPreferredSize();
		Rectangle bounds = dialog.getBounds();
		dialog.setBounds(bounds.x, bounds.y, Math.max(bounds.width, 550), bounds.height);
	}
	
	
	private void addListners() {
		
		dialog.setVisible(true);
		
		dialog.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				if (changed) {
					int answer = JOptionPane.showConfirmDialog(
							dialog, 
							"Do you want to save changes ???", "Save Changes", 
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						saveTbl();
					}
				}
			}
			
		});
	}
	
	
	private void saveTbl() {
		itemList.setItemList(favMdl.itemList);
		changed = false;
	}
	
	private void tblUpdate(int[] selRows, int amt) {
		changed = true;
		favMdl.fireTableDataChanged();
		favTable.clearSelection();
		favTable.addRowSelectionInterval(selRows[0] + amt, selRows[selRows.length-1] + amt);
	}





	@SuppressWarnings("serial")
	private static class ItemListMdl extends AbstractTableModel {
		private final List<IListItem> itemList;

		public ItemListMdl(List<IListItem> itemList) {
			super();
			this.itemList = itemList;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 1;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return "Favourite";
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return itemList.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return itemList.get(rowIndex).getItemText();
		}
		
		
	}
}
