package com.github.bmTas.recedui.common;


import java.awt.Component;

import javax.swing.JPopupMenu;

import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IUiFrame;

public class SetVisible implements ISetVisible {

	public static final SetVisible SET_VISIBLE = new SetVisible();
	
	public static ISetVisible getSetVisible(ISetVisible setVisible) {
		return setVisible == null	? SET_VISIBLE
									: setVisible;
	}
	
	@Override
	public void setVisible(IUiFrame container, boolean visible) {
		container.getGuiContainer().setVisible(visible);
	}

	@Override
	public void show(Component invoker, JPopupMenu popup, int x, int y) {
		popup.show(invoker, x, y);
	}
}
