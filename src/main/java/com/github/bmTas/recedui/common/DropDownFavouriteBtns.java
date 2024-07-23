package com.github.bmTas.recedui.common;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.bmTas.recedui.buttons.ArrowButton;
import com.github.bmTas.recedui.buttons.UiButton;
import com.github.bmTas.recedui.buttons.UiPanelButton;
import com.github.bmTas.recedui.def.component.IUiButton;
import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.fileFields.popup.EditFavouriteAction;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.styles.StyleManager;

public class DropDownFavouriteBtns<What> {

	public final JButton    recentBtn;
	public final JButton  favoriteBtn;
	
	public final MultiComponentBuilder componentBuilder;
	
	public final String typeTxt;
	
	
	public DropDownFavouriteBtns(
			MultiComponentBuilder componentBuilder,
			JComponent owner,
			IRecentList recentList, IUpdateableList favouriteList,
			IListActionBuilder actionBldr, Action addToFavourite) {
		this(   componentBuilder, owner, 
				recentList == null	? favouriteList == null ? "" 
															: " " + favouriteList.getListName()
									: " " + recentList.getListName(), 
				recentList, favouriteList, actionBldr, addToFavourite);
	}
	
	public DropDownFavouriteBtns(
			MultiComponentBuilder componentBuilder,
			JComponent owner, String  typeTxt,
			IRecentList recentList, IUpdateableList favouriteList,
			IListActionBuilder actionBldr, Action addToFavourite) {

		this.componentBuilder = componentBuilder;
		this.typeTxt = typeTxt;
		
		JButton    tRecentBtn = null;
		JButton  tFavoriteBtn = null;
		
        if (recentList == null && favouriteList == null) {
        	
        } else if (recentList != null && favouriteList != null) {       	
        	tRecentBtn = generateRecentBtn(owner, recentList, actionBldr, true);
        	tFavoriteBtn = generateFavoriteBtn(owner, favouriteList, actionBldr, addToFavourite);
//        	System.out.println("--> " + owner.getPreferredSize().height + " " + recentBtn.getPreferredSize().height
//        			+ " " + favoriteBtn.getPreferredSize().height + " " + favourite.getIconHeight());
        } else if (recentList != null) {
        	tRecentBtn = generateRecentBtn(owner, recentList, actionBldr, false);
         } else {
        	tFavoriteBtn = generateFavoriteBtn(owner, favouriteList, actionBldr, addToFavourite);
        }
        
        this.recentBtn   = tRecentBtn;
        this.favoriteBtn = tFavoriteBtn;
	}

	/**
	 * @param favoriteAction
	 * @return
	 */
	public JButton generateFavoriteBtn(JComponent owner, IUpdateableList favList, 
			IListActionBuilder actionBldr, Action addToFavourite) {
		UiButton favoriteButton = new UiButton(
				StyleManager.styles().comboFavorite(), IconManager.ICONS.favourite.icon());
			
		JPanel pnl = null;
		if (addToFavourite != null && favList != null && favList.allowUpdates()) {
			pnl = new JPanel(new GridLayout(2, 1));
			pnl.add(new JButton(addToFavourite));
			
			pnl.add(new JButton(new EditFavouriteAction(owner, favList)));
		}

//		favoriteButton.getMainComponent().addActionListener(
//				new FileListAction(favList, favList, favoriteButton.getUiContainer(), owner, pnl, favoriteAction));
					
		favoriteButton.getMainComponent().addActionListener(
				actionBldr.createAction(favList, favList, favoriteButton.getGuiContainer(), owner, pnl));
		//favoriteButton.setToolTip(msg);

		if (componentBuilder != null) {
			componentBuilder.add(favoriteButton.getMainComponent());
		}
		return favoriteButton.getMainComponent();
	}

	/**
	 * @param recentAction
	 * @return
	 */
	public JButton generateRecentBtn(JComponent owner, IRecentList recentList, 
			IListActionBuilder actionBldr, 
			boolean both) {
		
		//ArrowButton recentButton = new ArrowButton(StyleManager.styles().comboArrow(), ShapePainters.DOWN);
		IUiButton recentButton = new UiPanelButton(StyleManager.styles().comboArrow());
		String msg = "List recent " + typeTxt;

		
//		recentButton.addActionListener(
//				new FileListAction(recentList, recentButton.getUiContainer(), recentUpd));
		
		recentButton.addActionListener(actionBldr.createAction(recentList, null, recentButton.getGuiContainer(), owner, null));
		
		recentButton.setToolTipText(msg);
	
		if (componentBuilder != null) {
			componentBuilder.add(recentButton.getGuiContainer());
		}
		


		return recentButton.getMainComponent();
	}
	
	public void setButtonNames(String name) {
		setNameOfBtn(favoriteBtn, name + "_Fav");
		setNameOfBtn(recentBtn, name + "_Recent");
	}
	
    
	private void setNameOfBtn(JButton btn, String name) {
		if (btn != null) {
			btn.setName(name);
		}
	}
	
	public static interface IListActionBuilder {
		ActionListener createAction(IBasicList stdList, IUpdateableList updList, 
			JComponent displayItm, JComponent container, 
			JPanel favouritePnl);
		
	}
//	
//	private static class StringListActionBuilder implements IListActionBuilder {
//		final IListPopupActions<String> listner;
//		StringListActionBuilder(IListPopupActions<String> listner) {
//			this.listner = listner;
//			
//		}
//		@Override
//		public ActionListener createAction(IBasicList stdList, IUpdateableList updList, JComponent displayItm,
//				JComponent container, JPanel favouritePnl) {
//			return 
//		}
//		
//		
//	}
}
