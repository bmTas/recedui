package com.github.bmTas.recedui.def.application;

import com.github.bmTas.recedui.def.application.displays.IGuiApplicationPanel;

/**
 * A class that generates an action to be executed by the current screen. Typically
 * it will be a button or a drop-down menu.
 * It 
 * 
 * @author Bruce Martin
 *
 */
public interface IMenuItemStatusUpdater {
	/**
	 * Check if action is available
	 */
	void checkActionActive(IGuiApplicationPanel activeScreen);
}
