package com.github.bmTas.recedui.def.application;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.github.bmTas.recedui.def.component.IUiComponent;

/**
 * RecordEditor-UI version of a Main menu
 *  
 * @author Bruce Martin
 *
 */
public interface IApplicationMenu extends IUiComponent {

	JMenu getMainComponent();
	
	IApplicationMenu add(JMenuItem menuItem);

	IApplicationMenu add(Action action);

	IApplicationMenu addSeparator();

	//void add(PopupMenu popupMenu);

}