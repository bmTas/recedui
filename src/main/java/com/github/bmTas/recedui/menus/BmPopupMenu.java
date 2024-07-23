/**
 * 
 */
package com.github.bmTas.recedui.menus;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.github.bmTas.recedui.def.menu.IUiPopupMenu;
import com.github.bmTas.recedui.styles.IStyle;

/**
 * @author bruce
 *
 */
@SuppressWarnings("serial")
public class BmPopupMenu extends JPopupMenu implements IUiPopupMenu {

	private final JComponent parent;
	private final IStyle style;
	private final int xAdj, width, popupHeight;
	
	private JLabel fieldsLabel;


	public BmPopupMenu(JComponent parent, IStyle style, String label, int xAdj, int width, int popupHeight) {
		super(label);
		
		this.parent = parent;
		this.style = style;
		this.xAdj = xAdj;
		this.width = width;
		this.popupHeight = popupHeight;
	}

	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.component.IBmComponent#getStyleId()
	 */
	@Override
	public IStyle getStyleId() {
		return style;
	}

	@Override
	public JComponent getGuiContainer() {
		return this;
	}

	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.component.IGetComponent#getJComponent()
	 */
	@Override
	public JPopupMenu getMainComponent() {
		return this;
	}


	@Override
	public JLabel getFieldsLabel() {
		return fieldsLabel;
	}

	@Override
	public void setFieldsLabel(JLabel label) {
		this.fieldsLabel = label;
	}

	public void showPopup() {
		if (! this.isVisible()) {
    		int xCoord = xAdj;
    		int yCoord = parent.getHeight();

     		int w = width > 0 ? width : parent.getWidth();
    		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    		int maxHeight = screenSize.height - parent.getLocationOnScreen().y - yCoord;
    		Dimension prefSize = this.getPreferredSize();
    		
//    		System.out.print("++++ " + screenSize.height + " - " + parent.getLocationOnScreen().y + " " + this.getHeight());
    		
			if (prefSize.width < w || prefSize.height > maxHeight) {
    			this.setPreferredSize(new Dimension(
    					Math.max(prefSize.width, w),
    					Math.min(maxHeight, Math.max(prefSize.height, popupHeight ))));
    		}
	   		this.show(parent, xCoord, yCoord);
//			System.out.println("   ++++ " +  this.getHeight());
    		//highlightItem(true);
		} 
	}
}
