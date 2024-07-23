package com.github.bmTas.recedui.frame;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Window;

import javax.swing.JDialog;

import com.github.bmTas.recedui.common.SetVisible;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IGetContainer;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class DialogParamBuilder implements IUiDialogParam {

	private static final SetVisible VISIBLE_ACTION = new SetVisible();
	private final IGetContainer display;
	private ISetVisible visableAction;
//	private JDialog dialog;
	private Window parent;
	private String title = "";
	private ModalityType modalityType = ModalityType.MODELESS;
	
	
	public DialogParamBuilder(IGetContainer display) {
		super();
		this.display = display;
	}

	@Override
	public ISetVisible getVisibleAction() {
		return visableAction == null ? VISIBLE_ACTION : visableAction;
	}

	@Override
	public JDialog getDialog() {
		return new JDialog(parent, title, modalityType);
	}

	@Override
	public IGetContainer getDisplayComponent() {
		return display;
	}

	public DialogParamBuilder setVisableAction(ISetVisible visableAction) {
		this.visableAction = visableAction;
		return this;
	}

//	public void setDialog(JDialog dialog) {
//		this.dialog = dialog;
//	}
	
	public DialogParamBuilder setParentContainer(Component c) {
		this.parent = SwingUtils.getParent(c);
		return this;
	}

	
	public DialogParamBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public DialogParamBuilder setModality(ModalityType modalityType) {
		this.modalityType = modalityType;
		return this;
	}

	public DialogParamBuilder setModality(boolean modality) {
		this.modalityType = modality ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS;
		return this;
	}

}
