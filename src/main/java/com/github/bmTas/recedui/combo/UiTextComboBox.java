package com.github.bmTas.recedui.combo;

import java.util.List;

import com.github.bmTas.recedui.def.table.ITableCellEditAndRender;
import com.github.bmTas.recedui.standard.component.UiBaseTextCombo;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.text.TextRender;

/**
 * TextField with Combo selection. It behaves a lot like a Combo but the user can enter anything in the TextField
 * 
 * @author Bruce Martin
 *
 */
public class UiTextComboBox extends UiBaseTextCombo {
	/**
	 * Get a table cell edit/render
	 * @return requested editor/render
	 */
	public static ITableCellEditAndRender newRender() {
		return new TextRender(new UiTextComboBox(null));
	}
	
	
	
	private final TextCombo textCombo;

	
	public UiTextComboBox(IStyle style) {
		this(style, new TextCombo());
	}
	
	/**
	 * Text Combo box where the user can enter anything
	 * @param style Component Style
	 * @param items items to be displayed
	 */
	public UiTextComboBox(IStyle style, List<ITextComboItem> items) {
		this(style, new TextCombo(items));
	}

	private UiTextComboBox(IStyle style, TextCombo textCombo) {
		super(style, textCombo);
		this.textCombo = textCombo;
	}

	/**
	 * Set the list of drop downs to be displayed
	 * @param items items to be displayed
	 */
	public <Dropdowns extends ITextComboItem> void setDropDownList(ITextComboItem... items) {
		textCombo.setDropDownList(items);
	}

	/**
	 * Set the list of drop downs to be displayed
	 * @param items items to be displayed
	 */
	public void setDropDownList(List<? extends ITextComboItem> items) {
		textCombo.setDropDownList(items);
	}

	
}
