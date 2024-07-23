package com.github.bmTas.recedui.text;

import javax.swing.JTextArea;

import com.github.bmTas.recedui.styles.IStyle;

public class UiTextWindow extends UiTextAreaScrollPane {

	private final int bufferSize;
	private int len=0;
	private StringBuilder windowText;
	
	public UiTextWindow(IStyle style, int bufferSize) {
		this(style, new JTextArea(), bufferSize);
		// TODO Auto-generated constructor stub
	}

	public UiTextWindow(IStyle style, JTextArea field, int bufferSize) {
		super(style, field);
		this.bufferSize = bufferSize;
	}
	
	
	@Override
	public void append(String text) {
		len += text.length();
		if (windowText == null && len >= bufferSize) {
			windowText = new StringBuilder(bufferSize + 10);
		}
		
		if (windowText == null) {		
			super.component.append(text); 
			return;
		}
		
		if (len >= bufferSize) {
			windowText.delete(0, len-bufferSize);
		}
		windowText.append(text);
		super.component.setText(windowText.toString());
		len = windowText.length();
	}


}
