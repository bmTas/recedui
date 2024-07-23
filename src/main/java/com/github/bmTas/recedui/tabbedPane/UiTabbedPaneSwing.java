package com.github.bmTas.recedui.tabbedPane;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiTabbedPaneSwing extends UiStdComponent<JTabbedPane> {

	public static UiTabbedPaneSwing newTabbedPane() {
		return new UiTabbedPaneSwing();	
	}
	public static UiTabbedPaneSwing newTabbedPaneCloseableTabs() {
		return newTabbedPaneCloseableTabs(
				new ITabCloseSwingComponent() {
					@Override public void tabClosed(int index, Component c) {}
		});	
	}
	public static UiTabbedPaneSwing newTabbedPaneCloseableTabs(ITabCloseSwingComponent closeListner) {
		UiTabbedPaneSwing ret = new UiTabbedPaneSwing();
		ret.addTabCloseListner(closeListner);
		return ret;
	}
	
	//JTabbedPane p;
	private ArrayList<ITabCloseSwingComponent> closeListners = new ArrayList<>();
	private TabCloseOption tabCloseOption = TabCloseOption.NO_CLOSE_OPTION;
	
	
	public UiTabbedPaneSwing() {
		super(StyleManager.EMPTY_STYLE, new JTabbedPane());
	}

	public UiTabbedPaneSwing(IStyle style, TabCloseOption tabCloseOption) {
		super(style, new JTabbedPane());
		this.tabCloseOption = tabCloseOption;

	}

	public void addTabCloseListner(ITabCloseSwingComponent tabCloseListner) {
		if (tabCloseOption == TabCloseOption.NO_CLOSE_OPTION) {
			tabCloseOption = TabCloseOption.CLOSE_ALL_EXCEPT_FIRST;
		}

		closeListners.add(tabCloseListner);
	}
	
	public void removeTabCloseListner(ITabCloseSwingComponent tabCloseListner) {
		closeListners.remove(tabCloseListner);
	}
	
	public void addTab(String title, Icon icon, Component component, String tip) {
		super.component.addTab(title, icon, component, tip);
		checkForCloseAction(title, component);
	}
	public void addTab(String title, Icon icon, Component component) {
		super.component.addTab(title, icon, component);
		checkForCloseAction(title, component);

	}
	public void addTab(String title, Component component) {
		super.component.addTab(title, component);
		checkForCloseAction(title, component);
	}
	
	private void checkForCloseAction(String title, Component tabDisplayComponent) {
		if (tabCloseOption != TabCloseOption.NO_CLOSE_OPTION) {
			int tabCount = super.component.getTabCount() - 1;
//			TabWithClosePnl closePnl = new TabWithClosePnl(title, tabCount > 0);
//			closePnl.addCloseAction(new TabCloseAdapter<Component>(super.component, tabDisplayComponent, closePnl, closeListners));
			TabCloseEnhancedPnl<Component> closePnl = new TabCloseEnhancedPnl<Component>(title, tabCount > 0,
					super.component, tabDisplayComponent, closeListners);
			
			super.component.setTabComponentAt(tabCount, closePnl);
		}
		
	}

	public void addChangeListener(ChangeListener l) {
		super.component.addChangeListener(l);
	}

	public void removeChangeListener(ChangeListener l) {
		super.component.removeChangeListener(l);
	}

	public void remove(Component component) {
		super.component.remove(component);
	}

	public void remove(int index) {
		super.component.remove(index);
	}

	public void removeAll() {
		super.component.removeAll();
	}	
	
	public interface ITabCloseSwingComponent extends ITabClose<Component>{ }
}
