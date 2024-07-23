package com.github.bmTas.recedui.fileFields.zcopy;

import java.awt.GridLayout;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.bmTas.recedui.buttons.ArrowButton;
import com.github.bmTas.recedui.buttons.UiButton;
import com.github.bmTas.recedui.common.MultiComponentBuilder;
import com.github.bmTas.recedui.common.ShapePainters;
import com.github.bmTas.recedui.def.common.IListPopupActions;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.fileFields.popup.EditFavouriteAction;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.styles.StyleManager;

public class ZOldDropDownFavouriteBtns {

	public final JButton    recentBtn;
	public final JButton  favoriteBtn;
	
	public final MultiComponentBuilder componentBuilder;
	
	public final String typeTxt;
	
//	public DropDownFavouriteBtns(
//			MultiComponentBuilder componentBuilder,
//			JComponent owner, boolean isDir,
//			IRecentList recentList, IUpdateableList favouriteList,
//			IProcessMenuSelection processFile, Action addToFavourite) {
//		this(componentBuilder, owner, 
//				isDir ? " Directory" : " Files",
//				recentList, favouriteList, processFile, addToFavourite);
//	}
	
	public ZOldDropDownFavouriteBtns(
			MultiComponentBuilder componentBuilder,
			JComponent owner,
			IRecentList recentList, IUpdateableList favouriteList,
			IListPopupActions<Path> processFile, Action addToFavourite) {
		this(   componentBuilder, owner, 
				recentList == null	? favouriteList == null ? "" 
															: " " + favouriteList.getListName()
									: " " + recentList.getListName(), 
				recentList, favouriteList, processFile, addToFavourite);
	}
	
	public ZOldDropDownFavouriteBtns(
			MultiComponentBuilder componentBuilder,
			JComponent owner, String  typeTxt,
			IRecentList recentList, IUpdateableList favouriteList,
			IListPopupActions<Path> processFile, Action addToFavourite) {

		this.componentBuilder = componentBuilder;
		this.typeTxt = typeTxt;
		
		JButton    tRecentBtn = null;
		JButton  tFavoriteBtn = null;
		
        if (recentList == null && favouriteList == null) {
        	
        } else if (recentList != null && favouriteList != null) {       	
        	tRecentBtn = generateRecentBtn(owner, recentList, processFile, true);
        	tFavoriteBtn = generateFavoriteBtn(owner, favouriteList, processFile, addToFavourite);
//        	System.out.println("--> " + owner.getPreferredSize().height + " " + recentBtn.getPreferredSize().height
//        			+ " " + favoriteBtn.getPreferredSize().height + " " + favourite.getIconHeight());
        } else if (recentList != null) {
        	tRecentBtn = generateRecentBtn(owner, recentList, processFile, false);
         } else {
        	tFavoriteBtn = generateFavoriteBtn(owner, favouriteList, processFile, addToFavourite);
        }
        
        this.recentBtn   = tRecentBtn;
        this.favoriteBtn = tFavoriteBtn;
	}

	/**
	 * @param favoriteAction
	 * @return
	 */
	public JButton generateFavoriteBtn(JComponent owner, IUpdateableList favList, 
			IListPopupActions<Path> favoriteAction, Action addToFavourite) {
		UiButton favoriteButton = new UiButton(
				StyleManager.styles().comboFavorite(), IconManager.ICONS.favourite.icon());
	
		//	StyleManager.styles().comboFavorite().
		//favoriteButton.setBorder(BorderFactory.createEmptyBorder());
		//favoriteButton.setBackground(Color.WHITE);
		
//		favoriteButton.getMainComponent().addActionListener( new ListPopupMgrAction(
//        		favList, favoriteButton.getMainComponent(), owner,
//        		favoriteAction, addToFavourite ));
		
		JPanel pnl = null;
		if (addToFavourite != null && favList != null && favList.allowUpdates()) {
			pnl = new JPanel(new GridLayout(3, 1));
			pnl.add(new JButton(addToFavourite));
			
			pnl.add(new JButton(new EditFavouriteAction(owner, favList)));
		}

		favoriteButton.getMainComponent().addActionListener(
				new FileListAction(favList, favList, favoriteButton.getGuiContainer(), owner, pnl, favoriteAction));
					
		
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
			IListPopupActions<Path> recentUpd, 
			boolean both) {
//		BasicArrowButton rButton = new BasicArrowButton(BasicArrowButton.SOUTH,
//				UIManager.getColor("control"), UIManager.getColor("controlShadow"),
//				Color.BLACK, //UIManager.getColor("controlDkShadow"), 
//		        UIManager.getColor("controlLtHighlight"));
		ArrowButton recentButton = new ArrowButton(StyleManager.styles().comboArrow(), ShapePainters.DOWN);
		//JButton recentButton = rButton;
		String msg = "List recent " + typeTxt;
//		Dimension size = recentButton.getSize();
//		Dimension pref = recentButton.getPreferredSize();
//		System.out.println(" ~~> " + size.width + " " + size.height + "  " + pref.width + " " + pref.height);
		
		
//		recentButton.addActionListener( new ListPopupMgrAction(
//        		recentList, recentButton, owner,
//        		recentUpd) );
		
		recentButton.addActionListener(
				new FileListAction(recentList, recentButton.getGuiContainer(), owner, recentUpd));
		
		recentButton.setToolTipText(msg);
	
		if (componentBuilder != null) {
			componentBuilder.add(recentButton.getGuiContainer());
		}
		
//       	owner.addComponentListener(new ComponentAdapter() {
//				@Override public void componentResized(ComponentEvent e) {
//					Dimension size = owner.getSize();
//					System.out.println("~**~ " + owner.getName() + " " + size.height);
//					recentBtn.setPreferredSize(new Dimension(size.height, size.height));
////					recentBtn.revalidate();
////					recentBtn.repaint();
//				}		
//			});

		return recentButton;
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
}
