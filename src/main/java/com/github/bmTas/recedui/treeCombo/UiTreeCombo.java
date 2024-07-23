package com.github.bmTas.recedui.treeCombo;

import java.awt.event.ActionEvent;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;

import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboModel;
import com.github.bmTas.recedui.standard.component.BaseCombo;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;


/**
 * TreeCombo Swing util
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class UiTreeCombo<Code> extends BaseCombo implements IUiTextField {// implements IGetSetObj {
	
	public static ITreeComboParamBldr<Integer> newIntegerParam() {
		return new TreeComboParam<Integer>() .setBlankItem(new TreeComboCodeItem<>("", null, 0));
	}
	public static ITreeComboParamBldr<String> newStringParam() {
		return new TreeComboParam<String>() .setBlankItem(new TreeComboCodeItem<>("", null, ""));
	}

//	private static final ITreeComboItem<Code>[] EMPTY_TREE = {};
//	private static final ITreeComboItem<Code>[] EMPTY_ITEM_LIST = {};
	
	private ITreeComboItem<Code> selectedItem = null;
	private HashMap<Code, ITreeComboItem<Code>> itemMap = new HashMap<Code, ITreeComboItem<Code>>();
	private HashMap<String, ITreeComboItem<Code>> nameMap = new HashMap<String, ITreeComboItem<Code>>();
//	private ITreeComboItem<Code>[] items;
	private ITreeComboModel<Code> treeModel;
	private JPopupMenu menu;
	private HashMap<ITreeComboItem<Code>, JMenu> menuMap = new HashMap<ITreeComboItem<Code>, JMenu>(15);
	private HashMap<ITreeComboItem<Code>, JMenuItem> menuItemMap = new HashMap<ITreeComboItem<Code>, JMenuItem>(25);
	
	public final ITreeComboItem<Code> blankItem;


	private final boolean addBlankItem;
	
	public UiTreeCombo(ITreeComboModel<Code> itms) {
		super();

		blankItem = new TreeComboCodeItem<>("", null, (Code) null);
		addBlankItem = false;
		generatePopup(getPopup(), itms);
	}


	
	public UiTreeCombo(ITreeComboParam<Code> param) {
		super(param);
		
		blankItem = param.getBlankItem();
		addBlankItem = param.isAddBlankItem();

		generatePopup(getPopup(), param.getComboModel());
	}




	public final void setTree(ITreeComboItem<Code>[] itms) {
		setTree(arrayToModel(itms), false);
	}


	public final void setTree(ITreeComboModel<Code> model) {
		setTree(model, false);
	}


	public final void setTree(ITreeComboModel<Code> model, boolean setSize) {
		JPopupMenu popup = getPopup();
		//popup.removeAll();

		generatePopup(popup, model);
		
		if (setSize) {
			popup.setFont(getTextCompenent().getFont());
		}
	}


	private JMenu generatePopup(ITreeComboItem<Code> itm, JMenu menu, List<? extends ITreeComboItem<Code>> items) {

		menuMap.put(itm, menu);
		for (ITreeComboItem<Code> item : items) {
			List<? extends ITreeComboItem<Code>> children = item.getChildren();
			addItem(item);
			if (children == null) {
				menu.add(new ComboAction(item));
			} else {
				menu.add(generatePopup(item, new JMenu(item.toString()), children));
			}
		}

		return menu;
	}



//
//	private JPopupMenu generatePopup(JPopupMenu menu, ITreeComboItem<Code>[] itms) {
//		return generatePopup(menu, arrayToModel(itms));
//	}


	private ITreeComboModel<Code> arrayToModel(ITreeComboItem<Code>[] itms) {
		return itms == null ? null : new TreeComboModel<Code>(itms);
	}




	private JPopupMenu generatePopup(JPopupMenu menu, ITreeComboModel<Code> itms) {

		treeModel = itms;
		if (treeModel == null) {
			treeModel = new TreeComboModel<Code>(Collections.emptyList());
		}
		itemMap = new HashMap<Code, ITreeComboItem<Code>>();
		nameMap = new HashMap<String, ITreeComboItem<Code>>();
		if (addBlankItem) {
			menu.add(new ComboAction(blankItem));
		}
		for (ITreeComboItem<Code> item : treeModel.getChildren()) {
			if (item != null) {
				List<? extends ITreeComboItem<Code>> children = item.getChildren();
				addItem(item);
				if (children == null) {
					menu.add(new ComboAction(item));
				} else {
					menu.add(generatePopup(item, new JMenu(item.toString()), children));
				}
			}
		}

		super.setPopupHeight(SwingUtils.values().COMBO_TABLE_ROW_HEIGHT * Math.min(16, treeModel.getChildren().size() + 1));

		this.menu = menu;
		return menu;
	}

	private void addItem(ITreeComboItem<Code> item) {
		String displayString = item.toString();

		itemMap.put(item.getCode(), item);
		if (displayString != null && displayString != null) {
			nameMap.put(displayString.toLowerCase(), item);
		}
	}


	/**
	 * @see com.github.bmTas.recedui.standard.component.BaseCombo#highlightItem(javax.swing.JPopupMenu, boolean)
	 */
	@Override
	protected void highlightItem(JPopupMenu currentPopup, boolean visible) {
		if (visible) {
			searchChildren(new ArrayList<ITreeComboItem<Code>>(), treeModel.getChildren());
		}  else {
			unhighlightChildren(treeModel.getChildren());
		}
	}




	private boolean searchItem(ArrayList<ITreeComboItem<Code>> parents, ITreeComboItem<Code> item) {
		parents.add(item);

		boolean ret = searchChildren(parents, item.getChildren());

		parents.remove(item);

		return ret;

	}

	private boolean searchChildren(ArrayList<ITreeComboItem<Code>> parents, List<? extends ITreeComboItem<Code>> children) {

		for (int i = 0; i < children.size(); i++) {
			ITreeComboItem<Code> child = children.get(i);
			if (child != null) {
				JMenu m;
	
				if (child.equals(selectedItem)) {
					MenuElement[] me = new MenuElement[parents.size() * 2 + 2];
	
					me[0] = menu;
					for (int j = 0; j < parents.size(); j++) {
						m =  menuMap.get(parents.get(j));
						me[j*2+1] = m;
						me[j*2+2] = m.getPopupMenu();
					}
					me[me.length - 1] = menuItemMap.get(child);
	
					MenuSelectionManager.defaultManager().setSelectedPath(me);
	//				for (ITreeComboItem<Code> p : parents) {
	//					m = menuMap.get(p);
	//					if (m != null) {
	//						m.setSelected(true);
	//						m.setPopupMenuVisible(true);
	//					}
	//				}
	//
	//				JMenuItem mi = menuItemMap.get(child);
	//				if (mi != null) {
	//					mi.setArmed(true);
	//				}
	
					return true;
				} else if (child.getChildren() != null && child.getChildren().size() > 0) {
					if (searchItem(parents, child)) {
						return true;
					}
				}
			}
		}

		return false;
	}


	private void unhighlightChildren(List<? extends ITreeComboItem<Code>> menuItems) {

		for (int i = 0; i < menuItems.size(); i++) {
			ITreeComboItem<Code> child = menuItems.get(i);
			if (child != null) {
				List<? extends ITreeComboItem<Code>> children = child.getChildren();
				JMenu m;
	
				if (children != null && children.size() > 0) {
					unhighlightChildren(children);
					m = menuMap.get(child);
					if (m != null) {
						m.setSelected(false);
						m.setPopupMenuVisible(false);
					}
				} else {
					JMenuItem mi = menuItemMap.get(child);
					if (mi != null && mi.isArmed()) {
						mi.setArmed(false);
					}
				}
			}
		}

	}

	/**
	 * @return the selectedItem
	 */
	public ITreeComboItem<Code> getSelectedItem() {
		String t = super.getText();
		
		if (t == null) {
			
		} else if (selectedItem != null || ! t.equalsIgnoreCase(selectedItem.toString())) {
			ITreeComboItem<Code> item = nameMap.get(t.toLowerCase());
			if (item != null) {
				selectedItem = item;
			}
		}
		return selectedItem;
	}

	public void setBlankItem() {
		setSelectedItem(blankItem);
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(ITreeComboItem<Code> selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
			super.setTextInternal(selectedItem.toString());
		}
	}
	
	public void setOnlySelectedItem(ITreeComboItem<Code> selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
		}
	}

	public boolean updateIfCode(Object key) {
		ITreeComboItem<Code> item = itemMap.get(key);
		if (item == null) {
			return false;
		}
		setSelectedItem(item);
		return true;
	}

	public void setSelectedCode(Code key) {
		ITreeComboItem<Code> item = itemMap.get(key);
		if (item == null) {
			item = blankItem;
		}
		setSelectedItem(item);
	}


	public void setSelectedString(String s) {
		ITreeComboItem<Code> item = blankItem;
		
		if (s != null && s.length() > 0) {
			item = nameMap.get(s.toLowerCase());
			if (item == null) {
				setOnlySelectedItem(blankItem);
				this.setTextInternal(s);
				return;
			}
		}
		setSelectedItem(item);
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.ComboLikeObject#textFieldChanged()
	 */
	@Override
	protected void textFieldChanged() {
		
		String s = valueTxt.getText();
		if (s != null && s.length() > 0) {
			ITreeComboItem<Code> item = nameMap.get(s.toLowerCase());
			if (item != null) {
				setSelectedItem(item);
				return;
			}
		}

		super.textFieldChanged();
	}

	public final ITreeComboItem<Code> getFirstItem() {
		
		List<? extends ITreeComboItem<Code>> children;
		if (treeModel == null || (children = treeModel.getChildren()) == null || children.size() == 0) {
			
		} else {
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i) != null) {
					return children.get(i);
				}
			}
		} 
		
		return null;
	}
	
	
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.swing.table.IGetSetObj#getComponent()
//	 */
//	@Override
//	public JComponent getComponent() {
//		return this;
//	}
//	
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.swing.table.IGetSetObj#setObject(java.lang.Object)
//	 */
//	@Override
//	public void setObject(Object value) {
//		if (value == null) {
//			this.setSelectedItem(ITreeComboItem<Code>.BLANK_ITEM);
//		} else if (value instanceof ITreeComboItem<Code>) {
//			this.setSelectedItem((ITreeComboItem<Code>) value);
//		} else if (value instanceof Integer) {
//			this.setSelectedKey((Integer) value);
//		} else {
//			this.setSelectedItem(ITreeComboItem<Code>.BLANK_ITEM);
//		}
//	}
//	
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.swing.table.IGetSetObj#getObject()
//	 */
//	@Override
//	public Object getObject() {
//		return this.getSelectedItem();
//	}


	private class ComboAction extends JMenuItem implements ActionListener {
		private final ITreeComboItem<Code> itm;

		public ComboAction(ITreeComboItem<Code> itm) {
			super(itm.toString());
			this.itm = itm;
			menuItemMap.put(itm, this);
			super.addActionListener(this);
		}


		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			setSelectedItem(itm);
		}
	}

}
