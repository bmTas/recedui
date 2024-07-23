package com.github.bmTas.recedui.common;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public abstract class AbstractListAction<What> implements ActionListener, IListChangeListner {


	private final IBasicList stdList;
	protected final JComponent  container;
	
	private       UiSelectionList<What> selList;
	private boolean doLoad = true;
	private long popupBecameInvisibleAt = 0;



	public AbstractListAction(IBasicList stdList, IUpdateableList updList, 
			JComponent displayItm, JComponent container) {
		this.stdList = stdList;
		this.container = container;

		
	}

	@Override
	public final void listChanged(final IBasicList basicList) {
		
		doLoad = true;
		if (selList.getGuiContainer().isVisible()) {
			SwingUtilities.invokeLater(new Runnable() {	
				@Override public void run() {
					selList.updateFileList(loadPopup(basicList.getItemList()));
					doLoad = false;
				}
			});

			loadPopup(basicList.getItemList());
		}
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
	    long timeDiff = System.currentTimeMillis() - popupBecameInvisibleAt;
		
 		if (selList != null && (selList.getGuiContainer().isVisible() || (timeDiff > 0 && timeDiff < 650)) ) {
 			selList.getGuiContainer().setVisible(false);
 		} else {
 			if (selList == null) {
	 			this.selList = createList();
	 			this.selList.getGuiContainer().addComponentListener(new ComponentAdapter() {
	 				@Override public void componentHidden(ComponentEvent e) {
	 					popupBecameInvisibleAt = System.currentTimeMillis();
	 				}
	 			});
 			}

			if (doLoad) {
				selList.updateFileList(loadPopup(stdList.getItemList()));
			}
			
			final Point pos = container.getLocationOnScreen();
			
			int posY = pos.y + container.getHeight() - 1;
			SwingUtils swingVals = SwingUtils.values();
			int fldHeight = swingVals.NORMAL_FIELD_HEIGHT;
			int height = Math.min(
					Math.max(selList.getGuiContainer().getPreferredSize().height, 
							 selList.getMainComponent().getPreferredSize().height + fldHeight/2), 
					swingVals.getFullScreenSize().height - posY - 3 * fldHeight);
			selList.setPosition(pos.x, posY, container.getWidth(), height);
 		}
	}

	protected abstract UiSelectionList<What> createList();
	protected abstract List<What> loadPopup(List<IListItem> itemList);

}
