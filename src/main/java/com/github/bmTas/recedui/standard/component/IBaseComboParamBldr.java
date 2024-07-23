package com.github.bmTas.recedui.standard.component;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import com.github.bmTas.recedui.styles.IStyle;

public interface IBaseComboParamBldr {

	IBaseComboParamBldr setStyle(IStyle style);

	IBaseComboParamBldr setTxtComponent(JTextComponent txtComponent);

	IBaseComboParamBldr setFieldName(String fieldName);

	IBaseComboParamBldr setPopup(JPopupMenu popup);

	IBaseComboParamBldr setButton(JComponent btns);

	IBaseComboParamBldr setButtons(JComponent[] btns);
	
	/**
	 * @param isIncludeComboButton the isIncludeComboButton to set
	 */
	IBaseComboParamBldr setIncludeComboButton(boolean isIncludeComboButton);

	IBaseComboParam asParam();

}