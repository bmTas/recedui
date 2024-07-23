package com.github.bmTas.recedui.text.param;

import com.github.bmTas.recedui.def.listManagers.IRecentFavourite;
import com.github.bmTas.recedui.styles.IStyle;

public interface IRecentText extends IRecentFavourite {

	IStyle getStyle();
	String getInitialValue();

}
