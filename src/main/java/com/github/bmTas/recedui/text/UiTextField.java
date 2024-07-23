package com.github.bmTas.recedui.text;

import javax.swing.JTextField;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiTextField extends UiStdComponent<JTextField> implements IUiTextField  {
	
	public UiTextField() { 
		super(deriveStyle(null), new JTextField()); 
	}
	public UiTextField(IStyle style) { 
		super(deriveStyle(style), new JTextField()); 
	}
	public UiTextField(IStyle style, JTextField field ) { super(deriveStyle(style), field); }
	
	private static IStyle deriveStyle(IStyle style) {
		return style == null ? StyleManager.styles().panelField() : style;
	}

	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.text.ITextComponent#getText()
	 */
	@Override
	public String getText() { return super.component.getText(); }
	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.text.ITextComponent#setText(java.lang.String)
	 */
	@Override
	public void setText(String t) { super.component.setText(t); }
}

