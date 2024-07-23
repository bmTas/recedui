package com.github.bmTas.recedui.text;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.text.IUiTextFieldPanel;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

/**
 * This will serve as a base for combo like text fields
 * @author Bruce Martin
 *
 */
public class UiTextPanel extends UiStdComponent2<JPanel,JTextField> implements IUiTextFieldPanel  {
	

	public UiTextPanel(IStyle style) { this(style, new JPanel(), new JTextField()); }
	public UiTextPanel(IStyle style, JTextField field ) { this(style, new JPanel(), field); }
	
	private UiTextPanel(IStyle style, JPanel panel, JTextField field ) { 
		super(style == null ? StyleManager.styles().panelAndField() : style, panel, field);
	}

	public final void setPanel(JPanel panel) {
		super.container = panel;
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

