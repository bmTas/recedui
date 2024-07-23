package com.github.bmTas.recedui.def.component;

import java.awt.Container;

/**
 * Basic Frame for use in UiApplication class
 * @author Bruce Martin
 *
 */
public interface IBasicFrame extends IFrameCommonMethods {

	void setContentPane(Container contentPane);

	Container getContentPane();

}
