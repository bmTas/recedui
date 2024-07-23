package com.github.bmTas.recedui.frame;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import com.github.bmTas.recedui.common.SetVisible;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IGetContainer;
import com.github.bmTas.recedui.def.component.IUiFrame;

public class UiFrame implements IUiFrame {

	private final ISetVisible setVisibleAction;
	private final JFrame frame;
	private  IGetContainer mainDisplay;
	
	
	public UiFrame(IGetContainer mainComponent) {
		this(null, null, mainComponent);
	}

	public UiFrame(ISetVisible setVisibleAction, JFrame frame, IGetContainer mainComponent) {
		super();
		this.setVisibleAction = setVisibleAction == null ? new SetVisible() : setVisibleAction;
		this.frame = frame == null ? new JFrame() : frame;
		this.mainDisplay = mainComponent;
		
		if (mainComponent != null) {
			frame.add(mainComponent.getGuiContainer());
		}
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public JFrame getGuiContainer() {
		return frame;
	}
	

	@Override
	public JRootPane getRootPane() {
		return frame.getRootPane();
	}


	@Override
	public void setVisible(boolean visible) {
		frame.pack();
		setVisibleAction.setVisible(this, visible);
	}

	@Override
	public IGetContainer getMainDisplay() {
		return mainDisplay;
	}


	@Override
	public void setPreferredSize(Dimension preferredSize) {
		frame.setPreferredSize(preferredSize);
	}


	@Override
	public Dimension getPreferredSize() {
		return frame.getPreferredSize();
	}
	
    public void setMaximised() {
    	frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

}
