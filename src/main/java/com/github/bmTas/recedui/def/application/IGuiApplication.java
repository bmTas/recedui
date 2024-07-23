package com.github.bmTas.recedui.def.application;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;

import com.github.bmTas.recedui.def.application.displays.IActionExecuter;
import com.github.bmTas.recedui.def.application.displays.IGuiApplicationPanel;
import com.github.bmTas.recedui.def.application.displays.IGuiInternalFrame;
import com.github.bmTas.recedui.def.application.displays.IInternalFrameCreationDetails;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IBasicFrame;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public interface IGuiApplication extends ISetVisible, IActionExecuter, IGetActivePanel {
	/**
	 * Set the current panel being displayed
	 * 
	 * @param applicationPanel application panel that has focus.
	 */
	void setActivePanel(IGuiApplicationPanel applicationPanel);
	
	/**
	 * Create a new Internal Frame
	 * @param title title for the internal frame
	 * @param applicationPanel application to be displayed on the panel.
	 * You can use the {@link com.github.bmTas.recedui.def.component.ICleanUp#cleanUpOnClose()}
	 * for any cleanup/close actions
	 * @return new internal frame
	 */
	IGuiInternalFrame newInternalFrame(String title, IGuiApplicationPanel applicationPanel);


	/**
	 * Create Internal frame from a container
	 * @param title title of the internal frame;
	 * @param container container to display
	 * @return newly created internal frame
	 */
	IGuiInternalFrame newInternalFrame(String title, Container container);

	/**
	 * Create new Internal Frame
	 * @param frameDetails frame Details
	 * @return new Internal Frame
	 */
	IGuiInternalFrame newInternalFrame(IInternalFrameCreationDetails frameDetails);

	/**
	 * define a MenuItem / Button to the application so that its status can be updated
	 * to active/inactive based on the screen being displayed and wether it supports the function
	 * 
	 * @param menuItemStatusUpdater MenuItem / Button to be checked on change of screen
	 */
	void addMenuItem(IMenuItemStatusUpdater menuItemStatusUpdater);

	/**
	 * Frame has lost focus
	 * @param frame frame that has lost focus
	 */
	void focusLost(IGuiInternalFrame frame);

	void setCurrentInternalFrame(IGuiInternalFrame frame);

	/**
	 * Activate the previous active frame
	 * @param currentFrame current active frame
	 */
	void activateLastFrame(IGuiInternalFrame currentFrame);

	/**
	 * Remove an internalFrame from the application.
	 * @param frame frame to be removed
	 */
	void removeFrame(IGuiInternalFrame frame);

	/** 
	 * Add a button to the Tool-Bar
	 * @param a action to add to the toolbar
	 * @return the button that was created
	 */
	JButton addToToolBar(Action a);

	/**
	 * Add a new Menu to the Menu bar
	 * @param name name of the new menu
	 * @return 
	 */
	IApplicationMenu newMenu(String name);

	/**
	 * Add a new Menu to the Menu bar
	 * @param menu  menu to add
	 * @return application menu
	 */
	IApplicationMenu newMenu(JMenu menu);

	/**
	 * Add a Windows menu to the Menu bar so the user can activate a specific window 
	 */
	void addWindowsMenu();

	/**
	 * Get the frame displaying the application
	 * @return frame where the application is displayed
	 */
	IBasicFrame getApplicationFrame();

	/**
	 * Set the "Application-Frame" visibility
	 * @param visible
	 */
	void setVisible(boolean visible);

	/**
	 * @return the applicationProperties
	 */
	IApplicationProperties getApplicationProperties();


	/**
	 * Get Swing details, you can do
	 * <pre>
	 *      getSwingInformation().getFullScreenSize()      // for the full screen size
	 *      getSwingInformation().getUsableScreenBounds()  // for the size no used by the operatin system
	 * </pre>
	 * @return Swing details
	 */
	SwingUtils getSwingInformation();

	/**
	 * Get the Application desktop preferred size
	 * @return Application desktop preferred size
	 */
	Dimension getDesktopPreferredSize();

	/**
	 * Get the Application desktop size
	 * @return Application desktop size
	 */
	Dimension getDesktopSize();

	/**
	 * @return the propertiesDirectory
	 */
	String getPropertiesDirectory();

	/**
	 * create a filename relative to the properties directory
	 * @param fileName requested
	 * @return
	 */
	String getFileNamePropertiesDirectory(String fileName);

}
