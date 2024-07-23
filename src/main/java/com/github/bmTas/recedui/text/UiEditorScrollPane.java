package com.github.bmTas.recedui.text;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import com.github.bmTas.recedui.common.UiStdComponent2;
import com.github.bmTas.recedui.def.component.IGetJScrollPaneContainer;
import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

/**
 * This will serve as a base for JTextArea scrollepane
 * @author Bruce Martin
 *
 */
public class UiEditorScrollPane extends UiStdComponent2<JScrollPane,JEditorPane> 
implements IUiTextField, IGetJScrollPaneContainer  {
	
	public static UiEditorScrollPane newUiHtmlPane(IStyle style) {
		UiEditorScrollPane htmlPane = new UiEditorScrollPane(style);
		
		htmlPane.component.setContentType("text/html");
		htmlPane.component.setEditable(false);
		
		return htmlPane;
	}

	public UiEditorScrollPane(IStyle style) { this(style, new JEditorPane()); }
	
	public UiEditorScrollPane(IStyle style, JEditorPane field ) { 
		super(style == null ? StyleManager.styles().panelAndField() : style, new JScrollPane(field), field);
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

