package com.github.bmTas.recedui.listManagers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.github.bmTas.recedui.def.common.IGetSelectedFile;
import com.github.bmTas.recedui.def.common.IGetText;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;

/**
 * Action to update a favourite list
 * 
 * @author Bruce Martin 
 *
 */
@SuppressWarnings("serial")
public class AddToFavouriteAction extends AbstractAction {

	private final IUpdateableList favList;
	private final IGetText sourceOfFavourite;
	private final IGetSelectedFile fileSource;
	
	public AddToFavouriteAction(IUpdateableList list, IGetSelectedFile sourceOfFavourite) {
		super("Add to Favourite " + (list == null ? "" : list.getListName()));
		this.favList = list;
		this.sourceOfFavourite = null;
		this.fileSource = sourceOfFavourite;
	}
	public AddToFavouriteAction(IUpdateableList list, IGetText sourceOfFavourite) {
		super("Add to Favourite " + (list == null ? "" : list.getListName()));
		this.favList = list;
		this.sourceOfFavourite = sourceOfFavourite;
		this.fileSource = null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = fileSource == null ? sourceOfFavourite.getText() : fileSource.getSelectedFile().getPath();
		favList.addFileItem(text);
	}

}
