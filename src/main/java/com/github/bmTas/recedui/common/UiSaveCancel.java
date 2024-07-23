package com.github.bmTas.recedui.common;

import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.bmTas.recedui.def.component.IUiPanel;

public class UiSaveCancel extends UiStdComponent2<JPanel,JPanel> implements IUiPanel {

	/**
	 * Create a Save/Cancel Button display
	 * @param saveAction Action to perform when the Save button is hit
	 * @param cancelAction Action to perform when the Cancel button is hit
	 */
	public UiSaveCancel(Action saveAction, Action cancelAction) {
		this(new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)), saveAction, cancelAction);
	}

	private UiSaveCancel(JPanel pnl, Action saveAction, Action cancelAction) {
		super(null, pnl, pnl);
		
		JButton cancelBtn = new JButton(cancelAction);
		JButton saveBtn = new JButton(saveAction);
		
	
		super.component.add(cancelBtn);
		super.component.add(saveBtn);
	}


}
