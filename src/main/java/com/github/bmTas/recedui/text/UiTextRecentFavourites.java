package com.github.bmTas.recedui.text;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.bmTas.recedui.common.AbstractListAction;
import com.github.bmTas.recedui.common.DropDownFavouriteBtns;
import com.github.bmTas.recedui.common.MultiComponentBuilder;
import com.github.bmTas.recedui.common.UiSelectionList;
import com.github.bmTas.recedui.common.DropDownFavouriteBtns.IListActionBuilder;
import com.github.bmTas.recedui.def.common.IListPopupActions;
import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.listManagers.AddToFavouriteAction;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.text.param.IRecentText;
import com.github.bmTas.recedui.text.param.IRecentTextBuilder;
import com.github.bmTas.recedui.text.param.RecentTextBuilder;

public class UiTextRecentFavourites extends UiTextPanel implements IListPopupActions<String> {
	public static IRecentTextBuilder paramBldr() { return new RecentTextBuilder(); }
	
	private final DropDownFavouriteBtns<String> recentFavBtns;
	private String old;
	private IRecentList recentList;
	
	public UiTextRecentFavourites(IRecentText param) {
		super(param.getStyle());
		
		recentList = param.getRecent();
		JTextField textField = this.getMainComponent();
		IListActionBuilder bldr = new IListActionBuilder() {
			
			@Override
			public ActionListener createAction(IBasicList stdList, IUpdateableList updList, JComponent displayItm,
					JComponent container, JPanel favouritePnl) {
				return new AbstractListAction<String>(stdList, updList, displayItm, container) {
					
					@Override
					protected List<String> loadPopup(List<IListItem> itemList) {
						int size = itemList.size();
						ArrayList<String> ret = new ArrayList<String>(size);
						for (int i = 0; i < size; i++) {
							ret.add(itemList.get(i).getItemText());	
						}
						return ret;
					}
					
					@Override
					protected UiSelectionList<String> createList() {
						return new UiSelectionList<String>(
								StyleManager.EMPTY_STYLE, super.container, true, 
								null,
								UiTextRecentFavourites.this);
					}
				} ;
			}
		};
		MultiComponentBuilder componentBldr = new MultiComponentBuilder(this.getGuiContainer(), textField);
		this.recentFavBtns = new DropDownFavouriteBtns<String>(
				componentBldr, this.getGuiContainer(), 
				recentList, param.getFavourites(), 
				bldr, 
				new AddToFavouriteAction(param.getFavourites(), this));
				
//				new DropDownFavouriteBtns(
//				componentBldr, this.getUiContainer(), param.getRecent(), param.getFavourites(), 
//				this, 
//				new AddToFavouriteAction(param.getFavourites(), this));
		
//		System.out.println(" >>> " + fileField.getPreferredSize().height
//				 + " "+ fileField.getMinimumSize().height);
//		if (recentFavBtns.recentBtn  != null) {
//			Dimension preferredSize = recentFavBtns.recentBtn.getPreferredSize();
//			int h = Math.max(preferredSize.height, textField.getPreferredSize().height);
//			recentFavBtns.recentBtn.setPreferredSize(new Dimension((h + 1) * 3 / 4, h));
//		}
		componentBldr.build();
		
		if (param.getInitialValue() != null) {
			textField.setText(param.getInitialValue());
		}
		textField.addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				notifyListners();
			}
		});
		
		old = getText();
	}
	
	
	@Override
	public void fireFileChanged(String item) {
		this.setText(item);
		notifyListners();
	}

	@Override
	public void popupHidden() {
		// TODO Auto-generated method stub
		
	}

	private ArrayList<ITextChangedListner> listners = new ArrayList<ITextChangedListner>(3);
	
	public void addTextChangeListner(ITextChangedListner listner) {
		listners.add(listner);
	}
	
	private void notifyListners() {
		
		String value = getText();
		if (old == null || ! old.equals(value)) {
			for (ITextChangedListner l : listners) {
				l.textChanged(this, old, value);
			}
			
			old = value;
		}
	}


	@Override
	public String getText() {
		String s = super.getText();
	
		recentList.addFileItem(s);
		return s;
	}
}
