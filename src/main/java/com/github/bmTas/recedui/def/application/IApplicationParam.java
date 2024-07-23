package com.github.bmTas.recedui.def.application;

import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IBasicFrame;

/**
 * Application-Parameter
 * @author Bruce Martin
 *
 */
public interface IApplicationParam {
	
	IBasicFrame getFrame();
	//String getFrameName();
	boolean isCreateMenuBar();
	boolean isCreateToolBar();
	ISetVisible getSetVisibleClass();
	IApplicationProperties getApplicationProperties();
	String getAppPropertiesDirectory();
}
