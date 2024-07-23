package com.github.bmTas.recedui.application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JRootPane;

import com.github.bmTas.recedui.application.displays.DocumentDetails;
import com.github.bmTas.recedui.def.application.IGuiApplication;
import com.github.bmTas.recedui.def.application.displays.IDocumentDetails;
import com.github.bmTas.recedui.def.application.displays.IGuiApplicationPanel;
import com.github.bmTas.recedui.def.application.displays.IGuiInternalFrame;
import com.github.bmTas.recedui.def.component.IGetContainer;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.tabbedPane.ITabChange;
import com.github.bmTas.recedui.tabbedPane.ITabClose;
import com.github.bmTas.recedui.tabbedPane.TabCloseOption;
import com.github.bmTas.recedui.tabbedPane.UiTabbedPane;

public class UiApplicationInternalFrame implements IGuiInternalFrame {

	private final IDocumentDetails documentDetails;
	private ArrayList<IGuiApplicationPanel> mainScreens = new ArrayList<>();

    private UiTabbedPane<IGuiApplicationPanel> pane = null;
    private final JInternalFrame frame;
    private boolean toInit = true;
    private String title = "??";
    private final TabCloseOption tabCloseOption;
    
    private final IGuiApplication application;
    private ITabChange<IGuiApplicationPanel> tabListner = new ITabChange<IGuiApplicationPanel>() {
		@Override public void tabSelected(int index, IGuiApplicationPanel pane) {
			application.setActivePanel(pane);
		}
    };
    ITabClose<IGuiApplicationPanel> tabCloseListner = new ITabClose<IGuiApplicationPanel>() {
		@Override public void tabClosed(int index, IGuiApplicationPanel panel) {
			if (index < 2 && pane.getTabCount() == 1) {
				mainScreens.add(pane.getTabDisplay(0));
				buildScreen();
			}
		}
	};
    

	public UiApplicationInternalFrame(IGuiApplication application, IDocumentDetails documentDetails, String title) {
		this(	application, documentDetails, title,
				new JInternalFrame(title, true, true, true, true), TabCloseOption.CLOSE_ALL_EXCEPT_FIRST);
    };
    

	public UiApplicationInternalFrame(
			IGuiApplication application,
			IDocumentDetails documentDetails, 
			String title,
			JInternalFrame frame,
			TabCloseOption tabCloseOption) {
		super();
		this.documentDetails = documentDetails == null ? DocumentDetails.NULL_DOCUMENT : documentDetails;
		this.frame = frame;
		this.application = application;
		this.tabCloseOption = tabCloseOption;
		
		this.title = title;

		
		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public String getTitle() {
		return title;
	}


	/**
	 * @return the application
	 */
	@Override
	public IGuiApplication getApplication() {
		return application;
	}


	@Override
	public void setVisible(boolean visible) {
		if (visible && toInit) {
			buildScreen();
			frame.pack();
			frame.setResizable(true);
		}
		application.setVisible(this, visible);
	}

	@Override
	public IGetContainer getMainDisplay() {
		return getCurrentApplicationPanel();
	}

	@Override
	public JInternalFrame getGuiContainer() {
		return frame;
	}

	@Override
	public IDocumentDetails getDocumentDetails() {
		return documentDetails;
	}


	@Override
	public JRootPane getRootPane() {
		return frame.getRootPane();
	}


	@Override
	public void setPreferredSize(Dimension preferredSize) {
		frame.setPreferredSize(preferredSize);
	}


	@Override
	public Dimension getPreferredSize() {
		return frame.getPreferredSize();
	}


	public void addFocusListener(FocusListener l) {
		frame.addFocusListener(l);
	}


	@Override
	public void addApplicationPanel(IGuiApplicationPanel applicationPanel) {
		if (toInit) {
			mainScreens.add(applicationPanel);
		} else if (pane == null) {
			mainScreens.add(applicationPanel);
			buildScreen();
		} else {
			synchronized (pane) {
				pane.addTab(applicationPanel.getTitle(), applicationPanel);
				frame.revalidate();
			}
		}
	}
	
	@Override
	public void setToLastApplicationPanel() {
		if (toInit && mainScreens.size() > 1) {
			buildScreen();
		}
		
		if (pane != null) {
			pane.setSelectedIndex(pane.getTabCount() - 1);
		} 
	}



	@Override
	public IGuiApplicationPanel getCurrentApplicationPanel() {
		if (pane == null) {
			return mainScreens.get(0);
		}
		return pane.getSelectedTabDisplay();
	}
	
	@Override
	public void moveToFront() {

		boolean max = frame.isMaximum();

		frame.moveToFront();
		frame.requestFocus(true);
		try {
			frame.setSelected(true);
			frame.setMaximum(max);
		} catch (Exception ex) {
		}
	}
	
	/**
	 * Window closing actions
	 */
	@Override
	public void windowWillBeClosing() {

	}
	
	@Override
	public void doWindowClose() {
		 windowWillBeClosing();
	}

	   
	@Override
	public int getApplicationPanelCount() {
		if (pane == null) {
			return mainScreens.size();
		}
		return pane.getTabCount();
	}
	
	@Override
	public IGuiApplicationPanel getApplicationPanel(int index) {
    	if (pane != null) {
    		return pane.getTabDisplay(index);
    	}
    	return mainScreens.get(index);
	
	}
	
	@Override
	public void setMaximised() {
    	try {
			frame.setMaximum(true);
		} catch (PropertyVetoException e) {
		}
    }
	
	private Component getGuiContainer(int idx) {
		if (pane != null) {
			return pane.getTabDisplay(idx).getGuiContainer();
		}
		return mainScreens.get(idx).getGuiContainer();
	}


	private final void buildScreen() {

        //JInternalFrame guiContainer = this.getGuiContainer();
        synchronized (frame) {
            frame.getContentPane().removeAll();
        	
			if (mainScreens.size() == 1) {
	            addMainComponent( getGuiContainer(0));
	
	            if (pane != null) {
	                pane.removeTabChangeListner(tabListner);
	                pane.removeTabCloseListner(tabCloseListner);

	                pane = null;
	            }
	        } else {
	        	if (pane == null) {
					pane = new UiTabbedPane<>(StyleManager.EMPTY_STYLE, tabCloseOption);
					pane.addTabChangeListner(tabListner);
	        		pane.addTabCloseListner(tabCloseListner);
	        	} else {
	        		pane.removeAll();
	        	}
	
	            addTabs();
	            addMainComponent( pane.getGuiContainer());
	        }
        }

        frame.revalidate();
        toInit = false;
        //System.out.println("Building " + this.title);
    }

    private void addTabs() {
        for (int i = 0; i < mainScreens.size(); i++) {
            pane.addTab(mainScreens.get(i).getTitle(), mainScreens.get(i));
        }
        mainScreens.clear();
    }
 

    /**
     * Add main component panel
     * @param panel panel to add
     */
    private void addMainComponent( Component panel) {

		frame.getContentPane().add(panel);

    	frame.pack();
//    	frame.setResizable(true);
    }


}
