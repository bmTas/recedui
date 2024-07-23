package com.github.bmTas.recedui.application;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.application.IApplicationMenu;
import com.github.bmTas.recedui.def.application.IGuiApplication;
import com.github.bmTas.recedui.def.application.IMenuItemStatusUpdater;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiApplicationMenu extends UiStdComponent<JMenu> implements IApplicationMenu {

	private final IGuiApplication application;
	
	public UiApplicationMenu(IGuiApplication application, JMenu menu) {
		this(StyleManager.EMPTY_STYLE, application, menu);
	}
	
	public UiApplicationMenu(IStyle style, IGuiApplication application, JMenu menu) {

		super(style, menu, menu);

		this.application = application;
	}
	
	
	@Override
	public UiApplicationMenu add(Action action) {
		checkIfStatusUpdate(action);

		super.component.add(action);
		
		return this;
	}

	@Override
	public UiApplicationMenu add(JMenuItem menuItem) {
		checkIfStatusUpdate(menuItem);
		
		super.component.add(menuItem);
		
		return this;
	}
	

	private void checkIfStatusUpdate(Object menuItem) {
		if (menuItem instanceof IMenuItemStatusUpdater) {
			application.addMenuItem((IMenuItemStatusUpdater) menuItem);
		}

	}
	
	@Override
	public UiApplicationMenu addSeparator() {
		super.component.addSeparator();
		
		return this;
	}

}
