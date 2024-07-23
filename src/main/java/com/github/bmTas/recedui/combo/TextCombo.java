package com.github.bmTas.recedui.combo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.github.bmTas.recedui.standard.component.BaseCombo;

@SuppressWarnings("serial")
public class TextCombo extends BaseCombo {

	public TextCombo() {
		
	}
	
	
	public TextCombo(JComponent... btns) {
		super(null, true, btns);
	}

	
	public TextCombo(List<ITextComboItem> items ) {
		setDropDownList(items);
	}

	
	public TextCombo(ITextComboItem... items ) {
		setDropDownList(items);
	}

	/**
	 * Set the list of drop downs to be displayed
	 * @param items items to be displayed
	 */
	public <Dropdowns extends ITextComboItem> void setDropDownList(Dropdowns[] dropdowns ) {
		Dropdowns[] dd = dropdowns.clone();
		setDropDownList(Arrays.asList(dd));
	}
	
	/**
	 * Set the list of drop downs to be displayed
	 * @param items items to be displayed
	 */
	public void setDropDownList(List<? extends ITextComboItem> items) {
		if (items != null) {
			JPopupMenu menu = new JPopupMenu();
			
			for (ITextComboItem itm : items) {
				menu.add(new ComboAction(itm));
			}
			
			Dimension popupPreferredSize = menu.getPreferredSize();
			Dimension preferredSize = this.getPreferredSize();
			if (popupPreferredSize.width > preferredSize.width) {
				this.setPreferredSize(new Dimension(popupPreferredSize.width, preferredSize.height));
			}
			System.out.println(popupPreferredSize.width);
		
			super.setCurrentPopup(menu);
		}
	}

	protected int getPopupWidth(JPopupMenu currentPopup) {
		return Math.max(this.getWidth(), currentPopup.getPreferredSize().width);
	}

	
	private class ComboAction extends JMenuItem implements ActionListener   {
		private final ITextComboItem item;
		
		private ComboAction(ITextComboItem item) {
			super(item.toString());
			this.item = item;
			this.addActionListener(this);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			TextCombo.this.setText(item.getCode());
		}
	}
}
