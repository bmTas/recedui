package com.github.bmTas.recedui.standard.component;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import com.github.bmTas.recedui.def.application.displays.IShowPopup;
import com.github.bmTas.recedui.styles.IStyle;

public class BaseComboParam<Param extends IBaseComboParamBldr> implements IBaseComboParam, IBaseComboParamBldr {

	private IStyle styleP;
	private JTextComponent txtComponent; 
	private String fieldName;
	private JPopupMenu popup;
	private JComponent[] btns;
	private boolean includeComboButton;

	@SuppressWarnings("unchecked")
	private Param self = (Param) this;
	private IShowPopup showPopup;

	

	@Override
	public boolean isIncludeComboButton() {
		return includeComboButton;
	}
	/**
	 * @param isIncludeComboButton the isIncludeComboButton to set
	 */
	@Override
	public Param setIncludeComboButton(boolean isIncludeComboButton) {
		this.includeComboButton = isIncludeComboButton;
		return self;
	}

	@Override
	public IStyle getStyle() {
		return styleP;
	}
	

	@Override
	public Param setStyle(IStyle styleP) {
		this.styleP = styleP;
		return self;
	}
	@Override
	public JTextComponent getTxtComponent() {
		return txtComponent;
	}
	@Override
	public Param setTxtComponent(JTextComponent txtComponent) {
		this.txtComponent = txtComponent;
		return self;
	}
	@Override
	public String getFieldName() {
		return fieldName;
	}
	@Override
	public Param setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return self;
	}
	@Override
	public JPopupMenu getPopup() {
		return popup;
	}
	@Override
	public Param setPopup(JPopupMenu popup) {
		this.popup = popup;
		return self;
	}
	@Override
	public JComponent[] getBtns() {
		return btns;
	}
	
	
	@Override
	public Param setButton(JComponent btn) {
		this.btns = new JComponent[] { btn };
		return self;
	}


	@Override
	public Param setButtons(JComponent[] btns) {
		this.btns = btns;
		return self;
	}

	/**
	 * @return the showPopup
	 */
	@Override
	public IShowPopup getShowPopup() {
		return showPopup;
	}
	
	/**
	 * @param showPopup the showPopup to set
	 */
	public Param setShowPopup(IShowPopup showPopup) {
		this.showPopup = showPopup;
		return self;
	}
	
	@Override
	public IBaseComboParam asParam() {
		return this;
	}
	
}
