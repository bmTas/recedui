package com.github.bmTas.recedui.application.displays;

import java.awt.Container;

import com.github.bmTas.recedui.def.application.displays.IActionExecuter;
import com.github.bmTas.recedui.def.application.displays.IActionId;
import com.github.bmTas.recedui.def.application.displays.IGuiApplicationPanel;
import com.github.bmTas.recedui.def.application.displays.IGuiInternalFrame;
import com.github.bmTas.recedui.def.application.displays.ISetInternalFrame;

/**
 * Basic Single Application panel display in a MDI (Multi Document Application).
 * 
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
 *                               This class BasicGuiApplicationPanel is a basic implementation of a panel
 * </pre>
 * 
 * This is a basic implementation of {@link IGuiApplicationPanel}. It can be used directly or extended as needed
 * (If you are implementing `Actions` 
 * 
 * @author Bruce Martin
 *
 * @param <Panel>
 */
public class BasicGuiApplicationPanel<Panel extends Container> implements IGuiApplicationPanel, ISetInternalFrame {
	private static IActionExecuter NULL_HANDLER = new IActionExecuter() {
		@Override public boolean isActionAvailable(IActionId actionId) {	return false; 	}
		@Override public void executeAction(IActionId actionId, Object data) {}
		@Override public void executeAction(IActionId actionId) {}
	};
	
	private final String title;
	private final Panel panel;
	private final IActionExecuter actionHandler;
	private IGuiInternalFrame internalFrame;

	/**
	 * 
	 * In this Ui package, each application (MDI) {@link com.github.bmTas.recedui.def.application.IGuiApplication}
	 * there a multiple Internal Frames {@link et.sf.recordEditor.ui.def.application.displays.IGuiInternalFrame}
	 * and each Internal-Frame can have one or more Application panels {@link IGuiApplicationPanel}
	 * 
	 * <pre>
	 *    IGuiApplication
	 *         |
	 *         +----  IGuiInternalFrame   (1 application has multiple Internal-Frames
	 *                      |
	 *                      +----    IGuiApplicationPanel each  Internal-Frame has one or more Panels
	 *                               This class BasicGuiApplicationPanel is a basic implementation of a panel
	 * </pre>
	 * 
	 * This is a basic implementation of {@link IGuiApplicationPanel}. It can be used directly or extended as needed
	 * (If you are implementing `Actions` 
	 * 
	 * @param title Panel title
	 * 
	 * @param panel panel to be displayed
	 */
	public BasicGuiApplicationPanel(String title, Panel panel) {
		this(title, panel, NULL_HANDLER);
	}
	
	public BasicGuiApplicationPanel(String title, Panel panel, IActionExecuter actionHandler) {

		super();
		this.title = title;
		this.panel = panel;
		this.actionHandler = actionHandler;
	}



	@Override
	public Panel getGuiContainer() {
		return panel;
	}

	@Override
	public boolean isActionAvailable(IActionId actionId) {
		return actionHandler.isActionAvailable(actionId);
	}

	@Override
	public void executeAction(IActionId actionId) {
		actionHandler.executeAction(actionId);
	}

	@Override
	public void executeAction(IActionId actionId, Object data) {
		actionHandler.executeAction(actionId, data);
	}

	@Override
	public String getTitle() {
		return title;
	}



	/**
	 * @return the internalFrame
	 */
	public IGuiInternalFrame getInternalFrame() {
		return internalFrame;
	}

	@Override
	public void setInternalFrame(IGuiInternalFrame internalFrame) {
		this.internalFrame = internalFrame;
	}
}
