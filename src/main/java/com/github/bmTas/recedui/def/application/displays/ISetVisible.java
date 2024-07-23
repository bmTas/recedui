package com.github.bmTas.recedui.def.application.displays;

import com.github.bmTas.recedui.def.component.IUiFrame;

/**
 * Class to make JFrames, JDialogues and JPopups visible. When Testing
 * it allows the capture of Frames/dialog/JPopups as they are made visible.
 * This allows the java swing application to be test without going through
 * Java Swing.
 * 
 * @author Bruce Martin
 *
 */
public interface ISetVisible extends IShowPopup {
	/**
	 * Make a frame/dialog visible
	 * @param container container to be made visible/invisible
	 * @param visible wether it should be visible.
	 */
	void setVisible(IUiFrame container, boolean visible);
}
