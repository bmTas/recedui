package com.github.bmTas.recedui.fileFields.popup;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.github.bmTas.recedui.def.listManagers.IUpdateableList;


@SuppressWarnings("serial")
public class EditFavouriteAction extends AbstractAction {

	private final IUpdateableList updList;
	private final  JComponent owner;
	
	
	public EditFavouriteAction(JComponent popup, IUpdateableList updList) {
		super("Edit Favourites");
		this.owner = popup;
		this.updList = updList;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (updList.getItemList().size() == 0) {
			JOptionPane.showMessageDialog(owner, "No Favourites to edit !!!");
		} else {
			new FavouriteEditor(owner, updList);
		}
	}

}
