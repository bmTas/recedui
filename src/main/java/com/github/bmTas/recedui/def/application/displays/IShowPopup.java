package com.github.bmTas.recedui.def.application.displays;

import java.awt.Component;

import javax.swing.JPopupMenu;

/**
 * Display a popup a a specified location. When testing, it allows 
 * you to track what is being displayed.
 * 
 * @author Bruce Martin
 *
 */
public interface IShowPopup {
	/**
	 * Display a popup at a specified position on the screen
	 * @param invoker parent comp[onent
	 * @param popup popup to be displayed
	 * @param x x-position
	 * @param y y-position
	 */
	void show(Component invoker, JPopupMenu popup, int x, int y);
}
