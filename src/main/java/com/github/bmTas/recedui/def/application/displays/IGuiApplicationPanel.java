package com.github.bmTas.recedui.def.application.displays;

import com.github.bmTas.recedui.def.component.IGetContainer;

/**
 * In this Ui package, each application (MDI) {@link com.github.bmTas.recedui.def.application.IGuiApplication}
 * there a multiple Internal Frames {@link et.sf.recordEditor.ui.def.application.displays.IGuiInternalFrame}
 * and each Internal-Frame can have one or more Application panels {@link IGuiApplicationPanel}
 * i.e.
 * <pre>
 *    IGuiApplication
 *         |
 *         +----  IGuiInternalFrame   (1 application has multiple Internal-Frames
 *                      |
 *                      +----    IGuiApplicationPanel each  Internal-Frame has one or more Panels
 * </pre>
 * 
 * @author Bruce Martin
 *
 */
public interface IGuiApplicationPanel extends IGetContainer, IActionExecuter {
	public String getTitle();
}
