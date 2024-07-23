package com.github.bmTas.recedui.def.listManagers;

public interface IRecentAndFavoriteFilesExtended extends IRecentAndFavoriteFiles, IRecentFavouriteExtended {


	@Override IStandardUpdateableList getRecent();

	@Override IStandardUpdateableList getRecentDirectory();

}
