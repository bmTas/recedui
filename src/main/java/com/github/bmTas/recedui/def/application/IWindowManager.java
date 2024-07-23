package com.github.bmTas.recedui.def.application;

import com.github.bmTas.recedui.def.application.displays.IGuiInternalFrame;

public interface IWindowManager {

	void remove(IGuiInternalFrame frame);

	void add(IGuiInternalFrame frame);

}