package com.github.bmTas.recedui.application.displays;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import com.github.bmTas.recedui.def.application.IGetActivePanel;
import com.github.bmTas.recedui.def.application.IMenuItemStatusUpdater;
import com.github.bmTas.recedui.def.application.displays.IActionId;
import com.github.bmTas.recedui.def.application.displays.IGuiApplicationPanel;

@SuppressWarnings("serial")
public class ActivePanelAction extends AbstractAction implements IMenuItemStatusUpdater {
	

	private final IGetActivePanel activePanelSource;
	private final IActionId actionId;
	
	public ActivePanelAction(IGetActivePanel activePanelSource, IActionId actionId, String name, Icon icon) {
		super(name, icon);
		this.activePanelSource = activePanelSource;
		this.actionId = actionId;
	}

	public ActivePanelAction(IGetActivePanel activePanelSource, IActionId actionId, String name) {
		super(name);
		
		this.activePanelSource = activePanelSource;
		this.actionId = actionId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IGuiApplicationPanel activePanel = activePanelSource.getActivePanel();
		
		if (activePanel != null) {
			activePanel.executeAction(actionId);
		}		
	}

	@Override
	public void checkActionActive(IGuiApplicationPanel activeScreen) {
		super.setEnabled(activeScreen.isActionAvailable(actionId));
	}
}
