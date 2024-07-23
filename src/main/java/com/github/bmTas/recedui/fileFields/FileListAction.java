package com.github.bmTas.recedui.fileFields;

import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.bmTas.recedui.common.AbstractListAction;
import com.github.bmTas.recedui.common.DropDownFavouriteBtns;
import com.github.bmTas.recedui.common.MultiComponentBuilder;
import com.github.bmTas.recedui.common.UiSelectionList;
import com.github.bmTas.recedui.common.DropDownFavouriteBtns.IListActionBuilder;
import com.github.bmTas.recedui.def.common.IListPopupActions;
import com.github.bmTas.recedui.def.file.IFileListItem;
import com.github.bmTas.recedui.def.file.IGetPath;
import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.listManagers.FileListItem;
import com.github.bmTas.recedui.styles.StyleManager;

public class FileListAction extends AbstractListAction<IFileListItem> implements ActionListener, IListChangeListner {

	public static DropDownFavouriteBtns<IFileListItem> createDropDown(MultiComponentBuilder componentBuilder,
			JComponent owner,
			IRecentList recentList, IUpdateableList favouriteList,
			IListPopupActions<IFileListItem> listner, Action addToFavourite) {
		String typeTxt = recentList == null	
					? favouriteList == null 
							? "" 
							: " " + favouriteList.getListName()
					: " " + recentList.getListName();
		return new DropDownFavouriteBtns<IFileListItem>(
				componentBuilder, owner, typeTxt, recentList, favouriteList, 
				new ListActionBldr(listner), 
				addToFavourite);
	}

//	private final JComponent  container;
	private final IListPopupActions<IFileListItem> listner;
	private final JPanel favouritePnl;
	

	
	public FileListAction(IBasicList updList,  
			JComponent displayItm, JComponent container, 
			IListPopupActions<IFileListItem> listner) {
		this(updList, null, displayItm, container, null, listner);
	}

	public FileListAction(IBasicList stdList, IUpdateableList updList, 
			JComponent displayItm, JComponent container, 
			JPanel favouritePnl,
			IListPopupActions<IFileListItem> listner) {
		super(stdList, updList, displayItm, container);
		this.listner = listner;
		this.favouritePnl = favouritePnl;
		
	}


	 
	@Override
	protected UiSelectionList<IFileListItem> createList() {
		return new UiFileSelectionList(StyleManager.EMPTY_STYLE, container, true, favouritePnl, listner);
	}

	protected List<IFileListItem> loadPopup(List<IListItem> itemList) {
		List<IFileListItem> paths = new ArrayList<IFileListItem>(itemList.size());
		
		for (IListItem itm : itemList) {
			paths.add(FileListItem.convert(itm));
		}
		
		return paths;
	}

	private static class ListActionBldr implements IListActionBuilder {
		private final IListPopupActions<IFileListItem> listner;
		
		public ListActionBldr(IListPopupActions<IFileListItem> listner) {
			this.listner = listner;
		}

		@Override
		public ActionListener createAction(IBasicList stdList, IUpdateableList updList, JComponent displayItm,
				JComponent container, JPanel favouritePnl) {
			return new FileListAction(stdList, updList, displayItm, container, favouritePnl, listner);
		}
		
	}
}
