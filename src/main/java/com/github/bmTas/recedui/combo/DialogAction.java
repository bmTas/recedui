	package com.github.bmTas.recedui.combo;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.frame.UiDialog;
import com.github.bmTas.recedui.value.IUiValueComponent;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class DialogAction<Value>  extends AbstractAction implements IFinishedCallBack {
	private final IUiValueComponent<Value> parent;
	private final  IContainerComboDetails<Value> details;
	private  Window parentContainer;
	private UiDialog diag;
	
	DialogAction(IUiValueComponent<Value> parent, IContainerComboDetails<Value> details, String text, Icon icon) {
		super(text, icon);
		this.parent = parent;
		this.details = details;
		this.details.setFinishedCallBack(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		if (parentContainer == null) {
			parentContainer = SwingUtils.getParent(parent.getGuiContainer());
		}
		diag = new UiDialog(UiDialog.newParamBldr(details.getUiContainer())
				.setParentContainer(parentContainer)
				.setModality(true)
		);
		
		ISetVisible visibleAction = details.getVisibleAction();
		if (visibleAction == null) {
			diag.setVisible(true);
		} else {
			visibleAction.setVisible(diag, true);
		}
		
		//System.out.println("actionPerformed Closing..");
		if (details.isSaveChangesRequired(parentContainer)) {
			parent.setValue(details.getUpdatedValue());
			parent.fireDataChanged();
		}
	}
	
	@Override
	public void finished(boolean accepted) {
		if (diag != null) {
			diag.setVisible(false);
			//System.out.println("finished Closing..");

			parent.setValue(details.getUpdatedValue());
			parent.fireDataChanged();
		}
	}
	
	
}