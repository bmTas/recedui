package com.github.bmTas.recedui.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import com.github.bmTas.recedui.application.displays.ActivePanelAction;
import com.github.bmTas.recedui.application.displays.BasicGuiApplicationPanel;
import com.github.bmTas.recedui.application.displays.InternalFrameApplicationLink;
import com.github.bmTas.recedui.application.displays.InternalFrameDetailsBuilder;
import com.github.bmTas.recedui.application.displays.StandardActionId;
import com.github.bmTas.recedui.def.application.IApplicationMenu;
import com.github.bmTas.recedui.def.application.IApplicationParam;
import com.github.bmTas.recedui.def.application.IApplicationProperties;
import com.github.bmTas.recedui.def.application.IGuiApplication;
import com.github.bmTas.recedui.def.application.IMenuItemStatusUpdater;
import com.github.bmTas.recedui.def.application.IWindowManager;
import com.github.bmTas.recedui.def.application.displays.IActionId;
import com.github.bmTas.recedui.def.application.displays.IGuiApplicationPanel;
import com.github.bmTas.recedui.def.application.displays.IGuiInternalFrame;
import com.github.bmTas.recedui.def.application.displays.IInternalFrameCreationDetails;
import com.github.bmTas.recedui.def.application.displays.ISetInternalFrame;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IBasicFrame;
import com.github.bmTas.recedui.def.component.IUiFrame;
import com.github.bmTas.recedui.frame.UiBasicFrame;
import com.github.bmTas.recedui.icons.IIconHolder;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class UiApplication implements IGuiApplication {
	
	private static IWindowManager NULL_WINOW_MANAGER = new IWindowManager() {	
		@Override public void remove(IGuiInternalFrame frame) {}	
		@Override public void add(IGuiInternalFrame frame) {}
	};
	
	public static ApplicationFrameBuilder newBuilder(String frameName) {
		return new ApplicationFrameBuilder(new UiBasicFrame(frameName));
	}
	public static ApplicationFrameBuilder newBuilder(IBasicFrame frame) {
		return new ApplicationFrameBuilder(frame);
	}
	public static InternalFrameDetailsBuilder newFrameDetailsBuilder() {
		return new InternalFrameDetailsBuilder();
	}

	//private ArrayList<IGuiApplicationPanel> panelsInUse = new ArrayList<>();
	//private ArrayList<IActionExecuter> activeScreenActions = new ArrayList<>();
	
	private ArrayList<IMenuItemStatusUpdater> menuItems = new ArrayList<>();
	private IGuiApplicationPanel activeScreen;
	private ArrayList<IGuiInternalFrame> framesInUse = new ArrayList<>();
	
	private final ISetVisible setVisible;
	private final IBasicFrame applicationFrame;
    private JMenuBar menuBar;// = new JMenuBar();
    private JToolBar toolBar;// = new JToolBar();

	private JDesktopPane desktop = new JDesktopPane();
	private IWindowManager windowManager = NULL_WINOW_MANAGER;
	private final IApplicationProperties applicationProperties;
	
	private final String propertiesDirectory;
	
	public UiApplication(IApplicationParam params) {
		this.applicationFrame = params.getFrame();
		this.propertiesDirectory = params.getAppPropertiesDirectory();
		this.applicationProperties = params.getApplicationProperties();
		setVisible = params.getSetVisibleClass();
		
	    JPanel fullPanel = new JPanel();

	    fullPanel.setLayout(new BorderLayout());
	    if (params.isCreateToolBar()) {
	    	toolBar = new JToolBar();
	    	fullPanel.add("North", toolBar);
	    }
	    fullPanel.add("Center", desktop);

	    if (params.isCreateMenuBar()) {
	    	menuBar = new JMenuBar();
	    	applicationFrame.getRootPane().setJMenuBar(menuBar);
	    }
	    applicationFrame.setContentPane(fullPanel);
	    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	    
	    applicationFrame.setPreferredSize(new Dimension(1000, 500));
	}
	
	
	/**
	 * @return the propertiesDirectory
	 */
	@Override
	public String getPropertiesDirectory() {
		return propertiesDirectory;
	}
	
	@Override
	public String getFileNamePropertiesDirectory(String fileName) {
		return Paths.get(propertiesDirectory, fileName).toString();
	}

	
	@Override
	public IApplicationMenu newMenu(JMenu menu) {
		if (menuBar == null) {
			throw new RuntimeException("You must specify you want a menu when you start the application ");
		}
		UiApplicationMenu applicationMenu = new UiApplicationMenu(this, menu);
		menuBar.add(menu);
		return applicationMenu;
	}
	
	@Override
	public IApplicationMenu newMenu(String name) {
		return newMenu(new JMenu(name));
	}
	
	@Override
	public void addWindowsMenu() {
		if (windowManager == NULL_WINOW_MANAGER) {
			WindowManager windowMgr = new WindowManager();
			windowManager = windowMgr;
			newMenu(windowMgr.getWindowMenu());
			
			for (IGuiInternalFrame frame : framesInUse) {
				windowMgr.add(frame);
			}
		}
	}

	public JButton addToToolBar(StandardActionId a) {
		IIconHolder iconDetails = a.getIconDetails();
		ActivePanelAction action = iconDetails == null
				? new ActivePanelAction(this, a, a.getName())
				: new ActivePanelAction(this, a, a.getName(), iconDetails.toolbarIcon());
		return addToToolBar(action);
	}
	
	@Override
	public JButton addToToolBar(Action a) {
		if (a instanceof IMenuItemStatusUpdater) {
			menuItems.add((IMenuItemStatusUpdater) a);
		}
		return toolBar.add(a);
	}
	
	@Override
	public void setVisible(IUiFrame container, boolean visible) {
		setVisible.setVisible(container, visible);
	}

	@Override
	public IBasicFrame getApplicationFrame() {
		return applicationFrame;
	}
	
	/**
	 * @return the applicationProperties
	 */
	@Override
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}
	
	@Override
	public void setVisible(boolean visible) {
		applicationFrame.setVisible(visible);
	}
	
	
	
	/**
	 * @param preferredSize
	 * @see com.github.bmTas.recedui.def.component.IFrameCommonMethods#setPreferredSize(java.awt.Dimension)
	 */
	public void setPreferredSize(Dimension preferredSize) {
		applicationFrame.setPreferredSize(preferredSize);
	}
	
	/**
	 * @return
	 * @see com.github.bmTas.recedui.def.component.IFrameCommonMethods#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return applicationFrame.getPreferredSize();
	}
	
	@Override
	public void show(Component invoker, JPopupMenu popup, int x, int y) {
		setVisible.show(invoker, popup, x, y);
	}
	
	
	@Override
	public IGuiApplicationPanel getActivePanel() {
		return activeScreen;
	}
	
	@Override
	public void setActivePanel(IGuiApplicationPanel applicationPanel) {
//		if (applicationPanel != null) {
//			synchronized (panelsInUse) {
//				panelsInUse.remove(applicationPanel);
//				panelsInUse.add(applicationPanel);
//			}
//		}
		activeScreen = applicationPanel;
		checkEnabledStatusOfActions();
	}

	
	@Override
	public void addMenuItem(IMenuItemStatusUpdater menuItemStatusUpdater) {
		menuItems.add(menuItemStatusUpdater);
	}
	
	public void checkEnabledStatusOfActions() {
		for (int i = menuItems.size()- 1; i >= 0; i--) {
			menuItems.get(i).checkActionActive(activeScreen);
		}

	}

	
	
	@Override
	public void setCurrentInternalFrame(IGuiInternalFrame frame) {
		if (frame == null) {
			if (framesInUse.size() > 0) {
				setActivePanel(framesInUse.get(framesInUse.size() - 1).getCurrentApplicationPanel());
			} else {
				setActivePanel(null);
			}
		} else {
			setActivePanel(frame.getCurrentApplicationPanel());
			synchronized (framesInUse) {
				framesInUse.remove(frame);
				framesInUse.add(frame);
			}
		}
	}

	@Override
	public IGuiInternalFrame newInternalFrame(String title, Container container) {
		return newInternalFrame(title, new BasicGuiApplicationPanel<Container>(title, container));
	}

	@Override
	public IGuiInternalFrame newInternalFrame(String title, IGuiApplicationPanel applicationPanel) {
		return newInternalFrame(
					newFrameDetailsBuilder()
						.setTitle(title)
						.addPanel(applicationPanel)
					.buildParameter());
	}
	
	@Override
	public IGuiInternalFrame newInternalFrame(IInternalFrameCreationDetails frameDetails) {
		UiApplicationInternalFrame iFrame = new UiApplicationInternalFrame(
				this, frameDetails.getDocumentDetails(), frameDetails.getTitle());
		
		
		for (IGuiApplicationPanel applicationPanel : frameDetails.getApplicationPanels()) {
			iFrame.addApplicationPanel(applicationPanel);
			if (applicationPanel instanceof ISetInternalFrame) {
				((ISetInternalFrame) applicationPanel).setInternalFrame(iFrame);
			}

		}
		iFrame.getGuiContainer().setResizable(true);

		desktop.add(iFrame.getGuiContainer());
		
		new InternalFrameApplicationLink(this, iFrame);
		
		windowManager.add(iFrame);
		
		if (frameDetails instanceof ISetInternalFrame) {
			((ISetInternalFrame) frameDetails).setInternalFrame(iFrame);
		}
		return iFrame;
	}

	
	@Override
	public void focusLost(IGuiInternalFrame frame) {
		if (activeScreen == frame) {
			setActivePanel(null);
		}
	}
	
	@Override
	public void activateLastFrame(IGuiInternalFrame currentFrame) {
		int last = framesInUse.size() - 1;
		if (last >= 0 && framesInUse.get(last) == currentFrame ) {
			synchronized (framesInUse) {
				if (last == framesInUse.size() - 1 && framesInUse.get(last) == currentFrame) {
					framesInUse.remove(last);
					if (last > 0) {
						IGuiInternalFrame activeFrame = framesInUse.get(last - 1);
						setActivePanel(activeFrame.getCurrentApplicationPanel());
						
						activeFrame.moveToFront();
					}
				}
				
			}
		}
	}
	
	@Override
	public void removeFrame(IGuiInternalFrame frame) {
//		int count = frame.getApplicationPanelCount(); 
//		for (int i = 0; i < count; i++) {
//			menuItems.remove(frame.getApplicationPanel(i));
//		}
		framesInUse.remove(frame);
        desktop.remove(frame.getGuiContainer());
        windowManager.remove(frame);

        activateLastFrame(frame);
	}


	@Override
	public boolean isActionAvailable(IActionId actionId) {
		return activeScreen == null ? false : activeScreen.isActionAvailable(actionId);
	}

	@Override
	public void executeAction(IActionId actionId) {
		if (activeScreen != null) {
			activeScreen.executeAction(actionId);
		}
	}

	@Override
	public void executeAction(IActionId actionId, Object data) {
		if (activeScreen != null) {
			activeScreen.executeAction(actionId, data);
		}
	}
	
	@Override
	public SwingUtils getSwingInformation() {
		return SwingUtils.values();
	}
	
	@Override
	public Dimension getDesktopPreferredSize() {
		return desktop.getPreferredSize();
	}
	
	
	@Override
	public Dimension getDesktopSize() {
		return desktop.getSize();
	}

}
