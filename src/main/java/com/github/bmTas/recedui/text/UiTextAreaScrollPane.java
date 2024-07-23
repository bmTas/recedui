package com.github.bmTas.recedui.text;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.common.IAppendText;
import com.github.bmTas.recedui.def.component.IGetJScrollPaneContainer;
import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

/**
 * This will serve as a base for JTextArea scrollepane
 * @author Bruce Martin
 *
 */
public class UiTextAreaScrollPane extends UiStdComponent2<JScrollPane,JTextArea> 
implements IUiTextField, IGetJScrollPaneContainer, IAppendText  {
	

	public UiTextAreaScrollPane(IStyle style) { this(style, new JTextArea()); }
	public UiTextAreaScrollPane(IStyle style, JTextArea field ) { 
		super(style == null ? StyleManager.styles().panelAndField() : style, new JScrollPane(field), field);
	}
	
//	private UiTextAreaScrollPane(IStyle style, JScrollPane scrollPane, JTextArea field ) { 
//		super(style == null ? StyleManager.styles().panelAndField() : style, scrollPane, field);
//		scrollPane.add(field);
//	}


	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.text.ITextComponent#getText()
	 */
	@Override
	public String getText() { return super.component.getText(); }
	
	
	@Override
	public void append(String t) { super.component.append(t); }

	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.text.ITextComponent#setText(java.lang.String)
	 */
	@Override
	public void setText(String t) { super.component.setText(t); }
	
	
}

