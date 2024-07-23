package com.github.bmTas.recedui.def.component;

import java.awt.Dimension;

import javax.swing.JRootPane;

/**
 * Common frame methods. 
 * @author Bruce Martin
 *
 */
interface IFrameCommonMethods extends IGetContainer {
	void setVisible(boolean visible);

	JRootPane getRootPane();

	void setPreferredSize(Dimension preferredSize);

	Dimension getPreferredSize();
}
