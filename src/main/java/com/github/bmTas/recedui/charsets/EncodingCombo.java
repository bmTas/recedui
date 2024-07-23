package com.github.bmTas.recedui.charsets;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.github.bmTas.recedui.buttons.UiPanelButton;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.standard.component.BaseCombo;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

/**
 * This class implements a Font-Combo box that lists
 * common fonts in a combo-box and has a font search button
 * 
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EncodingCombo extends BaseCombo {
	
	private Frame parentFrame;
	
	/**
	 *  This class implements a Font-Combo box that lists
     * common fonts in a combo-box and has a font search button
	 */
	public EncodingCombo(ISetVisible setVisible) {
		this(CharsetMgr.getCommonCharsets(), createButton(), setVisible);
	}
	
	public EncodingCombo() {
		this(CharsetMgr.getCommonCharsets(), createButton(), null);
	}

	
//	/**
//	 *  This class implements a Font-Combo box that lists
//     * supplied fonts in a combo-box and has a font search button
//	 */
//	public EncodingCombo(String[] fonts) {
//		this(lookupDescription(fonts),
//				createButton(),
//				null);
//	}

	private static UiPanelButton createButton() {
		return new UiPanelButton(
				StyleManager.styles().fontChooser(), 
				StyleManager.styles().getFontChooserStr());
	}
	
	private final ISetVisible setVisible;
	private EncodingCombo(String[][] fonts, UiPanelButton btn, ISetVisible setVisible) {
		super("fontTxt", true, btn.getGuiContainer());
		
		this.setVisible = setVisible;
		super.setShowThePopup(setVisible);
		
		setEncodingList(fonts);
		JButton mainComponent = btn.getMainComponent();
		
		this.setPreferredSize(
				new Dimension(
						Math.max(this.getPreferredSize().width, SwingUtils.values().CHAR_FIELD_WIDTH *25), 
						this.getPreferredSize().height));
		mainComponent.addActionListener(new ActionListener() {	
			@Override public void actionPerformed(ActionEvent e) {
				showFontScreen();
			}
		});
	}
	
	/**
	 * Set the drop down combo to a list encodings (fonts)
	 * @param fonts
	 */
	public void setEncodingList(String[] encodings) {
		setEncodingList(lookupDescription(encodings));
	}
	
	private static String[][] lookupDescription(String[] fonts) {
		String[][] ret = new String[fonts.length][];
		
		for (int i =0; i < fonts.length; i++) {
			ret[i] = new String[2];
			ret[i][0] = fonts[i];
			ret[i][1] = CharsetMgr.getDescription(fonts[i]);
		}
		
		return ret;
	}
	
	/**
	 * Set the drop down combo to a list fonts
	 * @param encodings
	 */
	public void setEncodingList(String[][] encodings) {
		
		if (encodings != null) {
			JPopupMenu menu = new JPopupMenu();
			
			for (String[] s : encodings) {
				menu.add(new FontAction(s[0], s[1]));
			}
		
			super.setCurrentPopup(menu);
		}
	}
	protected int getPopupWidth(JPopupMenu currentPopup) {
		return Math.max(this.getWidth(), currentPopup.getPreferredSize().width);
	}

	private void showFontScreen() {
		CharsetSelection charsetSelection = new CharsetSelection(
				getParentFrame(),
				super.getText(),
				setVisible);
		super.setText(charsetSelection	.getCharset());
	}
	
	private Frame getParentFrame() {
		if (parentFrame == null) {
			Container parent = this.getFocusCycleRootAncestor();
			while (parent != null) {
				if (parent instanceof Frame) {
					parentFrame = (Frame) parent;
					break;
				}
				parent = this.getFocusCycleRootAncestor();
			}
		}
		return parentFrame;
	}
	
	private class FontAction extends JMenuItem implements ActionListener   {
		private final String fontName;
		private FontAction(String fontName, String description) {
			super(fontName + ":  \t" + description);
			this.fontName = fontName;
			this.addActionListener(this);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			EncodingCombo.this.setText(fontName);
		}
	}
}
