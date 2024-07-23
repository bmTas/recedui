package com.github.bmTas.recedui.def.common;

import com.github.bmTas.recedui.def.component.IUiComponent;

public interface IFieldChangedListner<SwingItem extends IUiComponent, Value> {
	void valueChanged(SwingItem field, Value  oldValue, Value newValue);
}
