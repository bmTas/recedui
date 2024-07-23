package com.github.bmTas.recedui.frame;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import com.github.bmTas.recedui.def.component.IBasicFrame;

public class UiBasicFrame implements IBasicFrame {

	private final JFrame frame;
	
	
	public UiBasicFrame() {
		this((JFrame) null);
	}

	public UiBasicFrame(String name) {
		this(new JFrame(name));
	}

	public UiBasicFrame(JFrame frame) {
		super();
		this.frame = frame == null ? new JFrame() : frame;
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public JFrame getGuiContainer() {
		return frame;
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			frame.pack();
		}
		frame.setVisible(visible);
	}

	@Override
	public JRootPane getRootPane() {
		return frame.getRootPane();
	}

	@Override
	public Container getContentPane() {
		return frame.getContentPane();
	}

	@Override
	public void setContentPane(Container contentPane) {
		frame.setContentPane(contentPane);
	}

	@Override
	public Dimension getPreferredSize() {
		return frame.getPreferredSize();
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		frame.setPreferredSize(preferredSize);
	}
    public void setMaximised() {
    	frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

}
