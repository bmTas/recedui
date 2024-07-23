package com.github.bmTas.recedui.standard.component;

import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiBaseTextCombo extends UiStdComponent2<BaseCombo, JTextComponent> implements IUiTextField {

	
	public static IBaseComboParamBldr newParamBldr() { return new BaseComboParam<BaseComboParam<IBaseComboParamBldr>>(); }

	
	
	public UiBaseTextCombo(IBaseComboParam param) {
		this(param.getStyle(), new BaseCombo(param));
	}
	public UiBaseTextCombo(IStyle style, BaseCombo combo) {
		super(getStyle(style), combo, combo.valueTxt);
	}
	

	protected static IStyle getStyle(IStyle style) {
		return style == null ? StyleManager.styles().comboLike() : style;
	}



	@Override
	public void setText(String s) {
		super.container.setText(s);
	}
	
	@Override
	public String getText() {
		return super.container.getText();
	}
	

	public final void addTextChangeListner(ChangeListener cl) {
		super.container.addTextChangeListner(cl);
	}
}
