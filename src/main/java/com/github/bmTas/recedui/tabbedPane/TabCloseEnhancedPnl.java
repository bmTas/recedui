package com.github.bmTas.recedui.tabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import com.github.bmTas.recedui.def.component.ICleanUp;

@SuppressWarnings("serial")
public class TabCloseEnhancedPnl<Pane> extends TabWithClosePnl implements ActionListener {
	private final JTabbedPane tabPane;
	private final Pane tabDisplayPnl;
	private final ArrayList<? extends ITabClose<Pane>> closeListners;
	
	
	public TabCloseEnhancedPnl(String screenName, boolean addCloseBtn, 
			JTabbedPane tabPane, Pane tabDisplayPnl, 
			ArrayList<? extends ITabClose<Pane>> closeListners) {
		super(screenName, addCloseBtn);
		this.tabPane = tabPane;
		this.tabDisplayPnl = tabDisplayPnl;
		this.closeListners = closeListners;
		
		this.addCloseAction(this);
	}


	public Pane getTabDisplayPnl() {
		return tabDisplayPnl;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		int idx = tabPane.indexOfTabComponent( this );
		ArrayList<? extends ITabClose<Pane>> tempCloseListners = new ArrayList<>(closeListners);
		
		tabPane.removeTabAt(idx);
		for (ITabClose<Pane> closeListner : tempCloseListners) {
			closeListner.tabClosed(idx, tabDisplayPnl);	
		}
		
		if (tabDisplayPnl instanceof ICleanUp) {
			((ICleanUp) tabDisplayPnl).cleanUpOnClose();
		}
	}
}
