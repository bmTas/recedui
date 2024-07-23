package com.github.bmTas.recedui.text;

import com.github.bmTas.recedui.def.component.IUiComponent;

public interface ITextChangedListner {
	void textChanged(IUiComponent source, String old, String value);
}
