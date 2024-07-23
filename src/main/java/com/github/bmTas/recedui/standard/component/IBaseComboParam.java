package com.github.bmTas.recedui.standard.component;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import com.github.bmTas.recedui.def.application.displays.IShowPopup;
import com.github.bmTas.recedui.styles.IStyle;

public interface IBaseComboParam {
	
	IStyle getStyle();

	JTextComponent getTxtComponent();

	String getFieldName();

	JPopupMenu getPopup();
	
	boolean isIncludeComboButton();

	JComponent[] getBtns();
	
	IShowPopup getShowPopup();
}