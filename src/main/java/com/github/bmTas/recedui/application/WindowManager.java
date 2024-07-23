package com.github.bmTas.recedui.application;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.github.bmTas.recedui.application.displays.DocumentDetails;
import com.github.bmTas.recedui.def.application.IWindowManager;
import com.github.bmTas.recedui.def.application.displays.IDocumentDetails;
import com.github.bmTas.recedui.def.application.displays.IGuiInternalFrame;

public class WindowManager implements IWindowManager {
	private JMenu windowMenu = new JMenu("Window");
	private DocumentFrames topLevelFrames  = new DocumentFrames(windowMenu);
	private List<DocumentFrames> docFrames = new ArrayList<>();
	
	 
	public JMenu getWindowMenu() {
		return windowMenu;
	}


	@Override
	public void add(IGuiInternalFrame frame) {
		if (frame == null) { return; }
		DocumentFrames frames = find(frame);
		if (frames != null) {
			frames.add(frame);
			return;
		}
		DocumentFrames docFrame = new DocumentFrames(frame);
		docFrames.add(docFrame);
		windowMenu.add(docFrame.menu);
	}
	
	
	@Override
	public void remove(IGuiInternalFrame frame) {
		if (frame == null) { return; }
		DocumentFrames frames = find(frame);
		if (frames != null) {
			frames.remove(frame);
		}
		
	}
	
	private DocumentFrames find(IGuiInternalFrame frame) {
		IDocumentDetails docDetails = frame.getDocumentDetails();
		if (docDetails == null || docDetails.getDocument() == null) {
			return topLevelFrames;
		}
		
		Object document = frame.getDocumentDetails().getDocument();
		for (DocumentFrames docF : docFrames) {
			if (docF.documentDetails.getDocument() == document) {
				return docF;
			}
		}
		return null;
	}


	private static class DocumentFrames {
		private final JMenu menu;
		private final IDocumentDetails documentDetails;
		
		private final List<FrameMenuItemDetails> menuItemDetailss = new ArrayList<>();
		
		private DocumentFrames(IGuiInternalFrame frame) {
			this.documentDetails = frame.getDocumentDetails();
			this.menu = new JMenu(documentDetails.getDocumentName());
			add(frame);
		}
		
		private DocumentFrames(JMenu menu) {
			this.documentDetails = DocumentDetails.NULL_DOCUMENT;
			this.menu = menu;
		}

		
		private boolean add(IGuiInternalFrame frame) {
			synchronized (menuItemDetailss) {
				for (FrameMenuItemDetails menuItems : menuItemDetailss) {
					if (menuItems.frame == frame) { return false; }
				}
				FrameMenuItemDetails menuItem = new FrameMenuItemDetails(frame);
				menuItemDetailss.add(menuItem);
				menu.add(menuItem.menuItem);
			}
			return true;
		}
		
		
		private void remove(IGuiInternalFrame frame) {
			synchronized (menuItemDetailss) {
				for (int i = 0; i < menuItemDetailss.size(); i++) {
					FrameMenuItemDetails menuItemDtl = menuItemDetailss.get(i);
					if (menuItemDtl.frame == frame) { 
						menu.remove(menuItemDtl.menuItem);
						menuItemDetailss.remove(i);
						break;
					}
				}
				
			}
		}
	}
	
	private static class FrameMenuItemDetails {
		final IGuiInternalFrame frame;
		final JMenuItem menuItem;
		
		@SuppressWarnings("serial")
		public FrameMenuItemDetails(IGuiInternalFrame frame) {
			this.frame = frame;
			this.menuItem = new JMenuItem(new AbstractAction(frame.getTitle()) {
				@Override public void actionPerformed(ActionEvent e) {
					FrameMenuItemDetails.this.frame.moveToFront();
				}
			});
		}
		
		
	}
}
