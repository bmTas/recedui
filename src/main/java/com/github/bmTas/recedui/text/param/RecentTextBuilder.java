package com.github.bmTas.recedui.text.param;

import com.github.bmTas.recedui.def.listManagers.IRecentFavouriteExtended;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;

public class RecentTextBuilder implements IRecentTextBuilder.ITextRecentParamAndBuilder {

	private IRecentList recent;
	private IUpdateableList favourites;

	private IStyle style;
	private String initialValue="";
	
	@Override
	public RecentTextBuilder setRecentFavourite(IRecentFavouriteExtended recentFav) {
		
		this.recent = recentFav.getRecent();
		this.favourites = recentFav.getFavourites();
		
		return this;
	}

	
	@Override
	public RecentTextBuilder setRecent(IRecentList recent) {
		this.recent = recent;
		
		return this;
	}

	@Override
	public RecentTextBuilder setFavourites(IUpdateableList favs) {
		this.favourites = favs;
		
		return this;
	}

	@Override
	public RecentTextBuilder setStyle(IStyle style) {
		this.style = style;
		return this;
	}

	@Override
	public RecentTextBuilder setInitialValue(String initialValue) {
		this.initialValue = initialValue;
		return this;
	}

	@Override
	public ITextRecentParamAndBuilder asParamAndBuilder() {
		return this;
	}

	@Override
	public IStyle getStyle() {
		return style;
	}

	@Override
	public String getInitialValue() {
		return initialValue;
	}

	@Override
	public IRecentList getRecent() {
		return recent;
	}

	@Override
	public IUpdateableList getFavourites() {
		return favourites;
	}

	@Override
	public IRecentText buildParam() {
		RecentTextBuilder ret = new RecentTextBuilder();
		ret.favourites = favourites;
		ret.initialValue = initialValue;
		ret.recent = recent;
		ret.style = style;
		
		return ret;
	}

	
	
}
