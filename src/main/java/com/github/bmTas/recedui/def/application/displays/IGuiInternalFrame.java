package com.github.bmTas.recedui.def.application.displays;

import javax.swing.JInternalFrame;

import com.github.bmTas.recedui.def.application.IGuiApplication;
import com.github.bmTas.recedui.def.component.IUiFrame;

public interface IGuiInternalFrame extends IUiFrame {

	/**
	 * Get the document being displayed
	 * @return
	 */
	IDocumentDetails getDocumentDetails();
	
	/**
	 * @return frames title
	 */
	String getTitle();
	
	/**
	 * Get the Internal frame being used
	 */
	JInternalFrame getGuiContainer();
	
	
	
	/**
	 * add an application panel to the Frame
	 */
	void addApplicationPanel(IGuiApplicationPanel applicationPanel);
	
	/**
	 * Get the panel being displayed
	 * @return panel being displayed
	 */
	IGuiApplicationPanel getCurrentApplicationPanel();
	

	/**
	 * Set to the last application panel
	 */
	void setToLastApplicationPanel();

	/**
	 * Window closing actions
	 */
	void windowWillBeClosing();
	
	/**
	 * Move the frame to the front and request focus
	 */
	void moveToFront();
	
	/**
	 * Get a specific application panel
	 * @param index index of the application panel
	 * @return specific application panel
	 */
	IGuiApplicationPanel getApplicationPanel(int index);
	
	/**
	 * get the number of application panels displayed
	 * @return  number of application panels 
	 */
	int getApplicationPanelCount();
	
	/**
	 * Close the window
	 */
	void doWindowClose();

	void setMaximised();

	/**
	 * @return the application details
	 */
	IGuiApplication getApplication();
}
