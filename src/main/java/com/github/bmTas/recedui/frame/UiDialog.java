package com.github.bmTas.recedui.frame;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import com.github.bmTas.recedui.common.SetVisible;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IGetContainer;
import com.github.bmTas.recedui.def.component.IUiFrame;

public class UiDialog implements IUiFrame {
	
	public static DialogParamBuilder newParamBldr(IGetContainer display) {
		return new DialogParamBuilder(display);
	}

	private static final SetVisible VISIBLE_ACTION = new SetVisible();

	private final ISetVisible setVisibleAction;
	private final JDialog dialog;
	private  IGetContainer mainDisplay;
	
	public UiDialog(IUiDialogParam diagParam) {
		super();
		JDialog d = diagParam.getDialog();
		ISetVisible visibleAction = diagParam.getVisibleAction();
		
		this.setVisibleAction = visibleAction == null ? VISIBLE_ACTION : visibleAction;
		this.dialog = d == null ? new JDialog() : d;
		this.mainDisplay = diagParam.getDisplayComponent();
		
		if (mainDisplay != null) {
			dialog.add(mainDisplay.getGuiContainer());
		}
		
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public JDialog getGuiContainer() {
		return dialog;
	}
	

	@Override
	public JRootPane getRootPane() {
		return dialog.getRootPane();
	}


	@Override
	public void setVisible(boolean visible) {
		dialog.pack();
		setVisibleAction.setVisible(this, visible);
	}

	@Override
	public IGetContainer getMainDisplay() {
		return mainDisplay;
	}


	@Override
	public void setPreferredSize(Dimension preferredSize) {
		dialog.setPreferredSize(preferredSize);
	}


	@Override
	public Dimension getPreferredSize() {
		return dialog.getPreferredSize();
	}
}
