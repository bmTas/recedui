package com.github.bmTas.recedui.grid;


import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.component.IUiPanel;
import com.github.bmTas.recedui.styles.IStyle;

public class UiGridLayoutPanel<Component extends JComponent> extends UiStdComponent<JPanel> implements IUiPanel {
	
	private IDataTable<Component> components;

	public UiGridLayoutPanel(IStyle style) {
		super(style, null);
	}
	

	public UiGridLayoutPanel(IStyle style, IDataTable<Component> components) {
		super(style, new JPanel(new GridLayout(components.getRowCount(), components.getRowCount(), 1, 1)));
		//super(style, new GridLayout(components.getRowCount(), components.getRowCount(), 1, 1));
		
		this.components = components;
		addComponents();
	}
	
	public void setComponenets(IDataTable<Component> components) {
		super.getMainComponent().setLayout(new GridLayout(components.getRowCount(), components.getRowCount(), 1, 1));
		
		this.components = components;
		
		getMainComponent().removeAll();
		addComponents();
	}
	
	private void addComponents() {
		JPanel pnl = getMainComponent();
		if (components != null) {
			for (int row = 0; row < components.getRowCount(); row++) {
				for (int col = 0; col < components.getColumnCount(); col++) {
					pnl.add(components.get(row, col));
				}		
			}
		}
	}


	/**
	 * @param row
	 * @param column
	 * @return requested component
	 * @see com.github.bmTas.recedui.grid.IDataTable#get(int, int)
	 */
	public Component getComponent(int row, int column) {
		return components.get(row, column);
	}
}
