package com.github.bmTas.recedui.styles;

import com.github.bmTas.recedui.def.component.IUiComponent;

public interface IStyle {
	public String getStyleName();
	
	public void register(IUiComponent component);
//	
//	public void registerChildren(StyleOptions opt, IBmComponent... components);
	
	public void apply(StyleAction action, IUiComponent component);
	
//	public void registerSwing(JComponent component);
//	
//	boolean is(StyleOptions option);
}
