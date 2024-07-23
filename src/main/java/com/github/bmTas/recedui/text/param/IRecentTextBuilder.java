package com.github.bmTas.recedui.text.param;

import com.github.bmTas.recedui.def.listManagers.IRecentFavouriteExtended;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;

public interface IRecentTextBuilder {
	IRecentTextBuilder setRecent(IRecentList recent);
	IRecentTextBuilder setFavourites(IUpdateableList favs);
	IRecentTextBuilder setStyle(IStyle style);
	IRecentTextBuilder setInitialValue(String initialValue);

	ITextRecentParamAndBuilder asParamAndBuilder();
	
	IRecentText buildParam();

	IRecentTextBuilder setRecentFavourite(IRecentFavouriteExtended recentFav);

	public static interface ITextRecentParamAndBuilder extends IRecentTextBuilder, IRecentText {	}
}
