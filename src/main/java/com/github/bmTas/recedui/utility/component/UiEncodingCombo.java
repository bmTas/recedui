package com.github.bmTas.recedui.utility.component;

import com.github.bmTas.recedui.charsets.EncodingCombo;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.text.IUiTextField;
import com.github.bmTas.recedui.standard.component.UiBaseTextCombo;
import com.github.bmTas.recedui.styles.IStyle;

public class UiEncodingCombo extends UiBaseTextCombo implements IUiTextField {

	private final EncodingCombo encodingCombo;
	public UiEncodingCombo(ISetVisible setVisible) {
		this(null, new EncodingCombo(setVisible));
	}
//	public UiEncodingCombo(IStyle style) {
//		this((style), new EncodingCombo());
//	}
//
//	public UiEncodingCombo(IStyle style, String[] encodings) {
//		this((style), new EncodingCombo(encodings));
//	}

	public UiEncodingCombo(IStyle style, EncodingCombo encodingCombo) {
		super(style/* == null ? StyleManager.styles().fontChooser() : style*/, encodingCombo);
		this.encodingCombo = encodingCombo;
	}


	
	/**
	 * Set the drop down combo to a list encodings (fonts)
	 * @param fonts
	 */
	public void setEncodingList(String[] encodings) {
		encodingCombo.setEncodingList(encodings);
	}
	
	/**
	 * Set the drop down combo to a list fonts
	 * @param encodings
	 */
	public void setEncodingList(String[][] encodings) {
		encodingCombo.setEncodingList(encodings);
	}
}
