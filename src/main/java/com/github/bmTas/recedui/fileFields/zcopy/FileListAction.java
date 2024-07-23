package com.github.bmTas.recedui.fileFields.zcopy;

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
import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListChangeListner;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.StyleManager;

public class FileListAction extends AbstractListAction<Path> implements ActionListener, IListChangeListner {

	public static DropDownFavouriteBtns<Path> createDropDown(MultiComponentBuilder componentBuilder,
			JComponent owner,
			IRecentList recentList, IUpdateableList favouriteList,
			IListPopupActions<Path> listner, Action addToFavourite) {
		String typeTxt = recentList == null	
					? favouriteList == null 
							? "" 
							: " " + favouriteList.getListName()
					: " " + recentList.getListName();
		return new DropDownFavouriteBtns<Path>(
				componentBuilder, owner, typeTxt, recentList, favouriteList, 
				new ListActionBldr(listner), 
				addToFavourite);
	}

//	private final JComponent  container;
	private final IListPopupActions<Path> listner;
	private final JPanel favouritePnl;
	

	
	public FileListAction(IBasicList updList,  
			JComponent displayItm, JComponent container, 
			IListPopupActions<Path> listner) {
		this(updList, null, displayItm, container, null, listner);
	}

	public FileListAction(IBasicList stdList, IUpdateableList updList, 
			JComponent displayItm, JComponent container, 
			JPanel favouritePnl,
			IListPopupActions<Path> listner) {
		super(stdList, updList, displayItm, container);
		this.listner = listner;
		this.favouritePnl = favouritePnl;
		
	}


	 
	@Override
	protected UiSelectionList<Path> createList() {
		return new UiFileSelectionList(StyleManager.EMPTY_STYLE, container, true, favouritePnl, listner);
	}

	protected List<Path> loadPopup(List<IListItem> itemList) {
		List<Path> paths = new ArrayList<Path>(itemList.size());
		
		for (IListItem itm : itemList) {
			paths.add(Paths.get(itm.getItemText()));
		}
		
		return paths;
	}

	private static class ListActionBldr implements IListActionBuilder {
		private final IListPopupActions<Path> listner;
		
		public ListActionBldr(IListPopupActions<Path> listner) {
			this.listner = listner;
		}

		@Override
		public ActionListener createAction(IBasicList stdList, IUpdateableList updList, JComponent displayItm,
				JComponent container, JPanel favouritePnl) {
			return new FileListAction(stdList, updList, displayItm, container, favouritePnl, listner);
		}
		
	}
}
