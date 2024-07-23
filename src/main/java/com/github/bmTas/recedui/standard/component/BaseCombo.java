package com.github.bmTas.recedui.standard.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

import com.github.bmTas.recedui.buttons.UiPanelButton;
import com.github.bmTas.recedui.def.application.displays.IShowPopup;
import com.github.bmTas.recedui.def.common.IGetSetText;
import com.github.bmTas.recedui.def.component.IUiButton;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

@SuppressWarnings("serial")
public class BaseCombo extends BasicPanel implements IGetSetText {
	
	public static IBaseComboParamBldr newParamBldr() { return new BaseComboParam<BaseComboParam<IBaseComboParamBldr>>(); }
	
	private final static IShowPopup SHOW_THE_POPUP = new IShowPopup() {
		@Override public void show(Component invoker, JPopupMenu popup, int x, int y) {
			popup.show(invoker, x, y);
		}
	};

//	private static final int FIELD_WIDTH = 20;

	protected final JTextComponent valueTxt;
	
	private TextAndButtons fieldAndBtns;

	private final IUiButton showPopupBtn;// = new ArrowButton(StyleManager.styles().comboArrow(), ShapePainters.DOWN);
	//protected final JButton showListBtn = new ArrowButton(ArrowButton.SOUTH);

	private final JComponent[] buttons;

	private int popupHeight = -1;
	//protected boolean visible = false;
	
	private boolean notifyOfAllUpdates = false;
	private long popupBecameInvisibleAt = 0;
	
	private JPopupMenu currentPopup = null;
	private PopupMenuListener popupListner = new PopupMenuListener() {

		@Override public void popupMenuWillBecomeVisible(PopupMenuEvent e) { }

		@Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			popupBecameInvisibleAt = System.currentTimeMillis();
		}

		@Override public void popupMenuCanceled(PopupMenuEvent e) {
			highlightItem(currentPopup, false);
		}
	};
	
	private List<ChangeListener> chgListners = new ArrayList<ChangeListener>(5);

	private String lastVal = null;
	
	private IShowPopup showThePopup = SHOW_THE_POPUP;

	public BaseCombo() {
		this(StyleManager.styles().panelAndField(), null, null, new JPopupMenu(), true, (JButton[]) null);
	}

	public BaseCombo(String txtFldName, boolean addComboBtn, JComponent... btns) {
		this(StyleManager.styles().panelAndField(), null, txtFldName, new JPopupMenu(), addComboBtn, btns);
	}


	/**
	 * Forms the base of various `Combo` classes.<br/> 
	 * usage:<pre>
	 *    new BaseCombo(BaseCombo.newParamBldr()
	 *                               .setStyle(...)
	 *                               .setTxtComponent(...)
	 *                           
	 * </pre>
	 * @param param 
	 */
	public BaseCombo(IBaseComboParam param) {
		this(param.getStyle(), param.getTxtComponent(), param.getFieldName(), param.getPopup(),
				param.isIncludeComboButton(),
				param.getBtns());
		setShowThePopup(param.getShowPopup());
	}


	private BaseCombo(IStyle styleP, JTextComponent txtComponent, String fieldName,
			JPopupMenu popup, boolean addComboBtn, JComponent... btns) {
		super(styleP == null ? StyleManager.styles().panelAndField() : styleP, new BorderLayout());

		popup = popup == null ? new JPopupMenu() : popup;
		
		fieldAndBtns = new TextAndButtons(txtComponent)
							.setPanel(this)
							.setButtons(btns);
		valueTxt = fieldAndBtns.valueTxt;	
		buttons = btns;
		
		setCurrentPopup(popup);
		
		if (fieldName != null) {
			valueTxt.setName(fieldName);
		}
		
		super.style.register(this);

		if (addComboBtn) {
			//listBtn = new ArrowButton(StyleManager.styles().comboArrow(), ShapePainters.DOWN);
			//int height=SwingUtils.values().stdIconHeight();

			IStyle comboArrowStyle = buttons == null || buttons.length == 0 
					? StyleManager.styles().comboArrow()
					: StyleManager.styles().comboArrow2();
			showPopupBtn = new UiPanelButton(comboArrowStyle);
			//listBtn.getMainComponent().setPreferredSize(new Dimension(height, height));
			fieldAndBtns.addUiButton(showPopupBtn);
			fieldAndBtns.layout();
			
			showPopupBtn.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
			    	showPopup();
				}
			});
		} else {
			showPopupBtn = null;
			fieldAndBtns.layout();			
		}
		

		valueTxt.addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				textFieldChanged();
			}
		});
	}
	
	@Override
	public JTextComponent getMainComponent() {
		return valueTxt;
	}


	/**
	 * set the enabled status of the listButton
	 * @param isEnabled wether the listBtn is enabled or not
	 */
	protected void setListButtonEnabled(boolean isEnabled) {
		if (showPopupBtn != null) {
			showPopupBtn.setEnabled(isEnabled);
		}
	}

	protected final void setTextFieldWidth(int width) {
		Dimension d = valueTxt.getPreferredSize();
		valueTxt.setPreferredSize(new Dimension(Math.max(d.width, width), d.height));
	}


	protected int getPopupWidth(JPopupMenu currentPopup) {
		return this.getWidth();
	}

	/**
	 * Default Action performed when the popup button is button is clicked
	 */
	protected void showPopup() {
		JPopupMenu nm = getPopup();

        long timeDiff = System.currentTimeMillis() - popupBecameInvisibleAt;
        setCurrentPopup(nm);

 		if (currentPopup.isVisible() || (timeDiff > 0 && timeDiff < 650) ) {
        	currentPopup.setVisible(false);
        	highlightItem(currentPopup, false);
        } else {
         	if (currentPopup != null) {
        		int xCoord = 0;
        		int yCoord = valueTxt.getHeight();

        		//System.out.println("Popup Height 1 " + currentPopup.getPreferredSize().height + " " + popupHeight);
        		int popupWidth = getPopupWidth(currentPopup);
				if (currentPopup.getWidth() < popupWidth) {
        			currentPopup.setPopupSize(new Dimension(
        					popupWidth,
        					Math.max(currentPopup.getPreferredSize().height, popupHeight)));
        		}
        		//System.out.println("Popup Height 2 " + currentPopup.getPreferredSize().height + " " + popupHeight);
				showThePopup.show(valueTxt, currentPopup, xCoord, yCoord);
        		//currentPopup.show(valueTxt, xCoord, yCoord);

        		highlightItem(currentPopup, true);
         	}
	    }
	}

	protected void hidePopup() {
		currentPopup.setVisible(false);
	}

	protected void highlightItem(JPopupMenu currentPopup, boolean visible) {

	}

//	protected static JButton[] addToArray(JButton b, JButton[] btns) {
//		JButton[] ret = btns;
//		if (btns == null || btns.length == 0) {
//			ret = new JButton[1];
//		} else if (btns.length > 1 || btns[0] != null){
//			ret = new JButton[btns.length + 1];
//			System.arraycopy(btns, 0, ret, 1, btns.length);
//		}
//
//		ret[0] = b;
//
//		return ret;
//	}

//	/**
//	 * @return the currentPopup
//	 */
//	public JPopupMenu getCurrentPopup() {
//		return currentPopup;
//	}

	/**
	 * @param newPopup the currentPopup to set
	 */
	protected void setCurrentPopup(JPopupMenu newPopup) {

    	if ( this.currentPopup != newPopup) {
    		if (this.currentPopup != null) {
    			this.currentPopup.removePopupMenuListener(popupListner);
    		}
    		newPopup.addPopupMenuListener(popupListner);
    		this.currentPopup = newPopup;
     	}
	}
	
	public void setEditable(boolean editable) {
		fieldAndBtns.setEditable(editable);
	}

	protected JPopupMenu getPopup() {
		return currentPopup;
	}



	/**
	 * @return the showThePopup
	 */
	protected IShowPopup getShowThePopup() {
		return showThePopup;
	}

	/**
	 * @param showThePopup the showThePopup to set
	 */
	public void setShowThePopup(IShowPopup showThePopup) {
		this.showThePopup = showThePopup == null ? SHOW_THE_POPUP : showThePopup;
	}

	/**
	 * @param t
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	public void setText(String t) {
		setTextSilently(t);
		fireValueChangeListner(null);
	}

	/**
	 * @param t
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	protected final void setTextInternal(String t) {
		valueTxt.setText(t);
		fireValueChangeListner(null);
	}

	public void setTextSilently(String t) {
		valueTxt.setText(t);
	}


	protected final void setTextSilentlyInternal(String t) {
		valueTxt.setText(t);
	}

	/**
	 * @return
	 * @see javax.swing.text.JTextComponent#getText()
	 */
	public String getText() {
		return valueTxt.getText();
	}

	public JTextComponent getTextCompenent() {
		return valueTxt;
	}



	/**
	 * @param popupHeight the popupHeight to set
	 */
	protected void setPopupHeight(int popupHeight) {
		if (popupHeight > 0) {
			this.popupHeight = popupHeight;

			currentPopup.setPopupSize(new Dimension(
						getPopupWidth(currentPopup),
						popupHeight));
		}
	}

	public final void addTextChangeListner(ChangeListener cl) {
		chgListners.add(cl);
	}

	public final void removeTextChangeListner(ChangeListener cl) {
		chgListners.remove(cl);
	}

	/**
	 * @param name
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	public void setName(String name) {
		fieldAndBtns.setName(name);

		super.setName(name);
	}

	/**
	 * @return the showPopupBtn
	 */
	public IUiButton getShowPopupBtn() {
		return showPopupBtn;
	}

	public List<JComponent> getAditionalButtons() {
		return Arrays.asList(buttons);
	}

	protected void textFieldChanged() {
		fireValueChangeListner(null);
	}

	protected void fireValueChangeListner(ChangeEvent e) {

		String textVal = valueTxt.getText();
		if ((lastVal == null || notifyOfAllUpdates || ! lastVal.equals(textVal))) {
			try {
				for (int i = 0; i < chgListners.size(); i++) {
					chgListners.get(i).stateChanged(e);
				}
				lastVal = textVal;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void setToolTipText(String text) {
		super.setToolTipText(text);
		if (valueTxt != null) {
			valueTxt.setToolTipText(text);
		}
	}
	
	
	protected static JButton ensureButtonExists(JButton b) {
		return b== null ? new JButton() : b;
	}
	
//
//	/**
//	 * @param notifyOfAllUpdates the notifyOfAllUpdates to set
//	 */
//	public final void setNotifyOfAllUpdates(boolean notifyOfAllUpdates) {
//		this.notifyOfAllUpdates = notifyOfAllUpdates;
//	}
}
