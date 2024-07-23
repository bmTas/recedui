package com.github.bmTas.recedui.application.displays;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.github.bmTas.recedui.def.application.IGuiApplication;
import com.github.bmTas.recedui.def.application.displays.IGuiInternalFrame;

public class InternalFrameApplicationLink {
	private final IGuiApplication application;
	private final IGuiInternalFrame frame;
	
	private InternalFrameAdapter listener = new InternalFrameAdapter() {
	       public void internalFrameActivated(InternalFrameEvent e) {
	        	//setActiveFrame(ReFrame.this);
	    	   application.setCurrentInternalFrame(frame);
	        }

	        public void internalFrameClosed(InternalFrameEvent e) {

	        	try {
	        		frame.getGuiContainer().removeInternalFrameListener(InternalFrameApplicationLink.this.listener);
	        	} catch (Exception ee) {
				}
	        	application.removeFrame(frame);
	
	            frame.doWindowClose();
	        }

	        public void internalFrameClosing(InternalFrameEvent e) {

	        	frame.windowWillBeClosing();
	        	application.activateLastFrame(frame);
	        	if (frame.getGuiContainer().getDefaultCloseOperation() == JInternalFrame.DISPOSE_ON_CLOSE) {
	        		frame.getGuiContainer().removeInternalFrameListener(listener);
	        	}
	        }

	        public void internalFrameDeactivated(InternalFrameEvent e) {
	        	application.focusLost(frame);
	        }

//	        public void internalFrameDeiconified(InternalFrameEvent e) {
//	        }

	        public void internalFrameIconified(InternalFrameEvent e) {
	        	application.focusLost(frame);
	        }

//	        public void internalFrameOpened(InternalFrameEvent e) {
//	        }

	};
	
	public InternalFrameApplicationLink(IGuiApplication application, IGuiInternalFrame frame) {
		super();
		this.application = application;
		this.frame = frame;
		
		frame.getGuiContainer().addInternalFrameListener(listener);
	}

	public IGuiApplication getApplication() {
		return application;
	}

	public IGuiInternalFrame getFrame() {
		return frame;
	}
	
	public void deactivateFrame() {
		try {
       		//System.out.println(" !! Loosing Focus " + activeFrame.getClass().getName() );
			frame.getGuiContainer().removeInternalFrameListener(listener);
			frame.getGuiContainer().setSelected(false);
			frame.getGuiContainer().addInternalFrameListener(listener);
		} catch (Exception ex) {
		}
	}

	
}
