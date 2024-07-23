package com.github.bmTas.recedui.styles;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;

import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.def.component.IUiTree;
import com.github.bmTas.recedui.def.table.IUiTable;
import com.github.bmTas.recedui.styles.attributes.IComponentAttribute;

public class StandardStyle implements IStyle, ITreeStyle, ITableStyle  {
	private static final IComponentAttribute.EmptyAttribute EMPTY_ATTR = new IComponentAttribute.EmptyAttribute();
	
	private final String styleName;
	
	//private boolean mouseOverChildren = false;
	private HashMap<StyleOptions, Object> options = new HashMap<StyleOptions, Object>();
	IComponentAttribute normalAttributes=EMPTY_ATTR, mouseOverAttributes, containerAttributes;
	
	Icon treeCollapsedIcon, treeExpandedIcon;
	
	Boolean showGrid;
	boolean interCellSpacing=true;
	Integer tableAutoResize;
	int minTblRowHeight = -1;
	
	
	
	
	public StandardStyle(String styleName) {
		super();
		this.styleName = styleName;
	}


	public String getStyleName() {
		return styleName;
	}


	@Override
	public void applyTree(IUiTree bmTree) {
		JTree tree = bmTree.getMainComponent();
		//normalAttributes.apply(tree);
		
		TreeUI ui = tree.getUI();
		if (ui instanceof BasicTreeUI) {
			BasicTreeUI treeui = (BasicTreeUI) ui;
			if (treeExpandedIcon != null) {
				treeui.setExpandedIcon(treeExpandedIcon); 
			}
			if (treeCollapsedIcon != null) {
				treeui.setCollapsedIcon(treeCollapsedIcon); 
			}
	   }

	}


	@Override
	public void applyTable(IUiTable tbl) {
		JTable table = tbl.getMainComponent();
		if (showGrid != null) {
			table.setShowGrid(showGrid);
		}
		if (! interCellSpacing) {
			table.setIntercellSpacing(new Dimension(0, 0));
		}
		
		if (tableAutoResize != null) {
			table.setAutoResizeMode(tableAutoResize);
		}
		
		if (minTblRowHeight > 0 && minTblRowHeight > table.getRowHeight()) {
			table.setRowHeight(minTblRowHeight);
		}
	}





//	/* (non-Javadoc)
//	 * @see net.sf.recordEditor.ui.styles.IStyle#registerChildren(net.sf.recordEditor.ui.def.component.IBmComponent[])
//	 */
//	@Override
//	public void registerChildren(IBmComponent... components) {
//		for (IBmComponent c : components) {
//			registerChildren(c.getStyleId(), c.getJComponent());
//		}
//	}
//
//
//	/* (non-Javadoc)
//	 * @see net.sf.recordEditor.ui.styles.IStyle#registerChildren(net.sf.recordEditor.ui.styles.IStyle, javax.swing.JComponent[])
//	 */
//	@Override
//	public void register(JComponen... components) {
//		
//		applyAttributes(normalAttributes, components);
//		
//		if (is(StyleOptions.mouseOver) && mouseOverAttributes != null) {
//			new MouseOver(normalAttributes, mouseOverAttributes, components);
//		}
//	}
//
//
//
//	/* (non-Javadoc)
//	 * @see net.sf.recordEditor.ui.styles.IStyle#registerChildren(net.sf.recordEditor.ui.styles.StyleOptions, net.sf.recordEditor.ui.def.component.IBmComponent[])
//	 */
//	@Override
//	public void registerChildren(StyleOptions opt, IBmComponent... components) {
//		switch (opt)
//		
//	}


//	@Override
//	public void registerSwing(JComponent component) {
//		normalAttributes.apply(component);
//		if (containerAttributes != null) {
//			containerAttributes.apply(component);
//		}
//	}

	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.styles.IStyle#register(net.sf.recordEditor.ui.def.component.IBmComponent)
	 */
	@Override
	public void register(IUiComponent component) {
		normalAttributes.apply(component.getMainComponent());
		if (containerAttributes != null) {
			containerAttributes.apply(component.getGuiContainer());
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.styles.IStyle#apply(net.sf.recordEditor.ui.styles.StyleAction, net.sf.recordEditor.ui.def.component.IBmComponent)
	 */
	@Override
	public void apply(StyleAction action, IUiComponent component) {
		switch (action) {
		case mouseOver: 
			if (mouseOverAttributes != null) {
				mouseOverAttributes.apply(component.getMainComponent());
			}
			break;
		case mouseExit:	normalAttributes.apply(component.getMainComponent());break;
		default:
			break;
		}
		
	}


	/**
	 * @param normalAttributes the normalAttributes to set
	 */
	public StandardStyle setNormalAttributes(IComponentAttribute normalAttributes) {
		this.normalAttributes = normalAttributes;
		
		return this;
	}

	/**
	 * @param normalAttributes the attributes for the container to set
	 */
	public StandardStyle setContainerAttributes(IComponentAttribute containerAttributes) {
		this.containerAttributes = containerAttributes;
		
		return this;
	}


	/**
	 * @param mouseOverAttributes the mouseOverAttributes to set
	 */
	public StandardStyle setMouseOverAttributes(IComponentAttribute mouseOverAttributes) {
		this.mouseOverAttributes = mouseOverAttributes;
		return this;
	}


	public StandardStyle addOptions(StyleOptions...options) {
		for (StyleOptions o : options) {
			this.options.put(o, o);
		}
		return this;
	}
	
	public StandardStyle setShowGrid(boolean showGrid, boolean interCellSpacing) {
		this.showGrid = showGrid;
		this.interCellSpacing = interCellSpacing;
		
		return this;
	}
	
	public StandardStyle setTableAutoResize(int tableAutoResize) {
		this.tableAutoResize = tableAutoResize;
		
		return this;
	}
	
	public StandardStyle setMinimumTblRowHeight(int height) {
		this.minTblRowHeight = height;

		return this;
	}

	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.styles.IStyle#is(net.sf.recordEditor.ui.styles.StyleOptions)
	 */
	public boolean is(StyleOptions option) {
		return options.containsKey(option);
	}

	public final StandardStyle setTreeCollapsedIcon(Icon treeCollapsedIcon) {
		this.treeCollapsedIcon = treeCollapsedIcon;
		return this;
	}


	public final StandardStyle setTreeExpandedIcon(Icon treeExpandedIcon) {
		this.treeExpandedIcon = treeExpandedIcon;
		return this;
	}
}
