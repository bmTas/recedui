package com.github.bmTas.recedui.common;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;

import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiCheckBox extends UiStdComponent<JCheckBox> {

	public UiCheckBox() {
		this(null, new JCheckBox());
	}

	public UiCheckBox(IStyle style, JCheckBox component) {
		super(style == null ? StyleManager.EMPTY_STYLE : style, component, component);
	}

	/**
	 * @return
	 * @see javax.swing.AbstractButton#isSelected()
	 */
	public boolean isSelected() {
		return component.isSelected();
	}

	/**
	 * @param selected  wether the check box value is selected
	 * @see javax.swing.AbstractButton#setSelected(boolean)
	 */
	public void setSelected(boolean selected) {
		component.setSelected(selected);
	}

	/**
	 * 
	 * @see javax.swing.AbstractButton#doClick()
	 */
	public void doClick() {
		component.doClick();
	}

	/**
	 * @param listner
	 * @see javax.swing.AbstractButton#addChangeListener(javax.swing.event.ChangeListener)
	 */
	public void addChangeListener(ChangeListener listner) {
		component.addChangeListener(listner);
	}

}
