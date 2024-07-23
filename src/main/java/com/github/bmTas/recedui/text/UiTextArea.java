package com.github.bmTas.recedui.text;

import javax.swing.JTextArea;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.common.IAppendText;
import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiTextArea extends UiStdComponent<JTextArea> implements IUiTextField, IAppendText  {
	public UiTextArea() { super(deriveStyle(null), new JTextArea()); }
	public UiTextArea(IStyle style) { super(deriveStyle(style), new JTextArea()); }
	public UiTextArea(IStyle style, JTextArea field ) { super(deriveStyle(style), field); }
	
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
	
	@Override
	public void append(String t) { super.component.append(t); }

//	public void addText(String t) {
//		JTextArea jTextArea = super.component;
//		jTextArea.setText(t);
//	}
}

