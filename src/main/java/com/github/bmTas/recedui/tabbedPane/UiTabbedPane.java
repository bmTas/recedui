package com.github.bmTas.recedui.tabbedPane;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.component.IGetContainer;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiTabbedPane<Pane extends IGetContainer> extends UiStdComponent<JTabbedPane> {


	private final ArrayList<ITabClose<Pane>> closeListners = new ArrayList<>();
	private final ArrayList<ITabChange<Pane>> tabListners = new ArrayList<>();
	private final ArrayList<Pane> panes = new ArrayList<>();
	private TabCloseOption tabCloseOption;
	
	private ChangeListener tabListner = new ChangeListener() {
		@Override public void stateChanged(ChangeEvent e) {
			int index = getSelectedIndex();
			Pane tabDisplayPnl = getSelectedTabDisplay();
			if (tabDisplayPnl != null) {
				for (ITabChange<Pane> listner : tabListners) {
					listner.tabSelected(index, tabDisplayPnl);
				}
			}
		}
	};

	
	
	public UiTabbedPane() {
		this(StyleManager.EMPTY_STYLE, TabCloseOption.NO_CLOSE_OPTION);
	}

	public UiTabbedPane(IStyle style, TabCloseOption tabCloseOption) {
		super(style, new JTabbedPane());
		this.tabCloseOption = tabCloseOption;
		this.getMainComponent().addChangeListener(tabListner);
	}
	

	public void addTabCloseListner(ITabClose<Pane> tabCloseListner) {
		if (tabCloseOption == TabCloseOption.NO_CLOSE_OPTION) {
			tabCloseOption = TabCloseOption.CLOSE_ALL_EXCEPT_FIRST;
		}
		
		closeListners.add(tabCloseListner);
	}
	
	public void removeTabCloseListner(ITabClose<Pane> tabCloseListner) {
		closeListners.remove(tabCloseListner);
	}

	
	public void addTabChangeListner(ITabChange<Pane> tabChanged) {
		tabListners.add(tabChanged);
	}
	
	public void removeTabChangeListner(ITabChange<Pane> tabChanged) {
		tabListners.remove(tabChanged);
	}

	
	public UiTabbedPane<Pane> addTab(String title, Icon icon, Pane component, String tip) {
		super.component.addTab(title, icon, component.getGuiContainer(), tip);
		checkForCloseAction(title, component);
		return this;
	}

	public UiTabbedPane<Pane> addTab(String title, Pane component) {
		super.component.addTab(title, component.getGuiContainer());
		checkForCloseAction(title, component);
		return this;
	}
	
	private void checkForCloseAction(String title, Pane tabDisplayComponent) {
		panes.add(tabDisplayComponent);
		
		if (tabCloseOption != TabCloseOption.NO_CLOSE_OPTION) {
			int tabCount = getTabCount() - 1;
//			TabWithClosePnl closePnl = new TabWithClosePnl(title, tabCount > 0);
//			closePnl.addCloseAction(new TabCloseAdapter<Pane>(super.component, tabDisplayComponent, closePnl, closeListners));
			
			TabCloseEnhancedPnl<Pane> closePnl = new TabCloseEnhancedPnl<Pane>(
					title, 
					tabCount > 0 || tabCloseOption == TabCloseOption.CLOSE_ALL,
					super.component, tabDisplayComponent, closeListners);
		
			
			super.component.setTabComponentAt(tabCount, closePnl);
		}
	}
	
	public Pane getSelectedTabDisplay() {
		int index = super.component.getSelectedIndex(); 
		return index < 0 ? null : getTabDisplay(index);
	}
	
	public int getSelectedIndex() {
		return super.component.getSelectedIndex(); 
	}
	@SuppressWarnings("unchecked")
	public Pane getTabDisplay(int index) {

		int panesIndex = getListIndexFromPanel(index, super.component.getComponentAt(index));
		if (panesIndex >= 0) {
			return panes.get(panesIndex);
		}
		Component c = super.component.getTabComponentAt(index); 
		if (c instanceof TabCloseEnhancedPnl) {
			return ((TabCloseEnhancedPnl<Pane>) c).getTabDisplayPnl();
		}
		//getTabCount();
		return null;
	}

	public int getTabCount() {
		return super.component.getTabCount();
	}

	public void setSelectedIndex(int index) {
		super.component.setSelectedIndex(index);
	}
	

//	public void addChangeListener(ChangeListener l) {
//		super.component.addChangeListener(l);
//	}
//
//	public void removeChangeListener(ChangeListener l) {
//		super.component.removeChangeListener(l);
//	}

	public void remove(IGetContainer component) {
		panes.remove(component);
		super.component.remove(component.getGuiContainer());
	}

	public void remove(int index) {
		if (index >= 0 && index < super.component.getTabCount()) {
			int panesIndex = getListIndexFromPanel(index, super.component.getComponentAt(index));
			if (panesIndex >= 0)  {
				panes.remove(panesIndex);
			}
			super.component.remove(index);
		}
	}
	
	

	public void removeAll() {
		panes.clear();
		super.component.removeAll();
	}
	

	public int getComponentCount() {
		return super.component.getComponentCount();
	}
	
	private int getListIndexFromPanel(int possibleIndex, Component panel) {
		if (possibleIndex >= 0 && possibleIndex < panes.size() 
		&& panes.get(possibleIndex).getGuiContainer() == panel) {
			return possibleIndex;
		}
		
		for (int i = 0; i < panes.size(); i++) {
			if (panes.get(i).getGuiContainer() == panel) {
				return i;
			}
		}
		return -1;
	}
}
