package com.github.bmTas.recedui.combo;

import java.awt.Component;

import javax.swing.JButton;

import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IGetContainer;

public interface IContainerComboDetails<Value> {

	String getText();
	
	Value getUpdatedValue();
	
	Value getValue();
	
	void setValue(Value value);
	
	void setFinishedCallBack(IFinishedCallBack finishedCallback);
	
	JButton getButton();
	
	ISetVisible getVisibleAction();
	
	IGetContainer getUiContainer();

	boolean isSaveChangesRequired(Component parentComponent);
}
