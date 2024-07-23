package com.github.bmTas.recedui.radioBtn;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JRadioButton;


public abstract class AbstractActionRadionBtnDtls extends BasicRadioBtnDtls implements ActionListener {

	public AbstractActionRadionBtnDtls(JRadioButton b) {
		super(b);
		super.getButton().addActionListener(this);
	}

	public AbstractActionRadionBtnDtls(String text) {
		this(text, null);
	}

	public AbstractActionRadionBtnDtls(String text, Icon icon) {
		super(text, icon);
		super.getButton().addActionListener(this);
	}
}
