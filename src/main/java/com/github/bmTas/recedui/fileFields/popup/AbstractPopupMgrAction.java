package com.github.bmTas.recedui.fileFields.popup;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;



/**
 * Base class to display/hide a Dropdown (Combo Like) menu 
 * @author Bruce Martin
 *
 */
public abstract class AbstractPopupMgrAction implements ActionListener {
	private long popupBecameInvisibleAt = 0;
	private int popupHeight = -1;
	
	protected final JPopupMenu currentPopup;
	protected final JComponent displayItm, container;
	
	private PopupMenuListener popupListner = new PopupMenuListener() {

		@Override 
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			popupBecameInvisibleAt = System.currentTimeMillis();
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			highlightItem(currentPopup, false);
		}
	};


	public AbstractPopupMgrAction(JPopupMenu currentPopup, JComponent displayItm, JComponent container) {
		super();
		this.currentPopup = currentPopup;
		this.displayItm = displayItm;
		this.container = container;
		
		currentPopup.addPopupMenuListener(popupListner);
	}

	protected abstract void highlightItem(JPopupMenu currentPopup, boolean visible);
	
	/**
	 * @param popupHeight the popupHeight to set
	 */
	protected void setPopupHeight(int popupHeight) {
		if (popupHeight > 0) {
			this.popupHeight = popupHeight;

			currentPopup.setPopupSize(new Dimension(
						container.getWidth(),
						popupHeight));
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	       long timeDiff = System.currentTimeMillis() - popupBecameInvisibleAt;

	 		if (currentPopup.isVisible() || (timeDiff > 0 && timeDiff < 650) ) {
	        	currentPopup.setVisible(false);
	        	highlightItem(currentPopup, false);
	        } else {
	         	if (currentPopup != null) {
	        		int xCoord = 0;
	        		int yCoord = container.getHeight();

	        		currentPopup.show(container, xCoord, yCoord);
	        		if (currentPopup.getWidth() < container.getWidth()) {
	        			currentPopup.setPopupSize(new Dimension(
	        					container.getWidth(),
	        					Math.max(currentPopup.getHeight(), popupHeight)));
	        		}

	        		highlightItem(currentPopup, true);
	         	}
		    }
	}
	
//
//	@SuppressWarnings("serial")
//	public static class MenuListItem extends JMenuItem implements ActionListener {
//
//		private final IListItem listItem;
//		private final IProcessMenuSelection owner;
//		
//		/**
//		 * 
//		 * @param listItem
//		 */
//		public MenuListItem(IListItem listItem, IProcessMenuSelection owner ) {
//			super(listItem.getDisplayText());
//			super.addActionListener(this);
//			
//			this.listItem = listItem;
//			this.owner = owner;
//		}
//		
//		
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			owner.processSelection(listItem);
//		}
//	}
}
