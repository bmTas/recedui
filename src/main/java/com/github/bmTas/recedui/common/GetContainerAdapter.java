package com.github.bmTas.recedui.common;

import java.awt.Container;

import com.github.bmTas.recedui.def.component.IGetContainer;

public class GetContainerAdapter<C extends Container> implements IGetContainer {

	private C container;
	public GetContainerAdapter(C container) {
		this.container = container;
	}

	@Override
	public C getGuiContainer() {
		return container;
	}

}
