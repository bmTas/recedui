package com.github.bmTas.recedui.styles;

import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.def.component.IUiTree;
import com.github.bmTas.recedui.def.table.IUiTable;

public class EmptyStyle implements IStyle, ITableStyle, ITreeStyle {
	
	public static final EmptyStyle NULL_STYLE = new EmptyStyle("Empty");
	private final String styleName;
	
	
	public EmptyStyle(String styleName) {
		super();
		this.styleName = styleName;
	}


	public String getStyleName() {
		return styleName;
	}


	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.styles.IStyle#register(net.sf.recordEditor.ui.def.component.IBmComponent)
	 */
	@Override
	public void register(IUiComponent component) {
		// TODO Auto-generated method stub
		
	}


//
//	@Override
//	public void registerSwing(JComponent component) {
//		
//	}


	@Override
	public void applyTree(IUiTree tree) {
		
	}


	@Override
	public void applyTable(IUiTable tbl) {
		
	}


	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.styles.IStyle#apply(net.sf.recordEditor.ui.styles.StyleAction, net.sf.recordEditor.ui.def.component.IBmComponent)
	 */
	@Override
	public void apply(StyleAction action, IUiComponent component) {
		
	}




//	/* (non-Javadoc)
//	 * @see net.sf.recordEditor.ui.styles.IStyle#is(net.sf.recordEditor.ui.styles.StyleOptions)
//	 */
//	@Override
//	public boolean is(StyleOptions option) {
//		return false;
//	}
	
	
}
