package com.github.bmTas.recedui.frame;

import javax.swing.JDialog;

import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IGetContainer;

public interface IUiDialogParam {

	ISetVisible getVisibleAction();
	JDialog getDialog();
	IGetContainer getDisplayComponent();
}
