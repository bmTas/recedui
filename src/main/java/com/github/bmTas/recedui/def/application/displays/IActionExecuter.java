package com.github.bmTas.recedui.def.application.displays;

public interface IActionExecuter {
	boolean isActionAvailable(IActionId actionId);
	void executeAction(IActionId actionId);
	void executeAction(IActionId actionId, Object data);
}
