package com.github.bmTas.recedui.value;

import com.github.bmTas.recedui.def.common.IGetSetValue;
import com.github.bmTas.recedui.def.component.IUiComponent;

public interface IUiValueComponent<Value> extends IUiComponent, IGetSetValue<Value> {
	public void addListner(IValueChangedListner listner);
	public void fireDataChanged();
}
