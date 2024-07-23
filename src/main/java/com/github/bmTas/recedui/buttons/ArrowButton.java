package com.github.bmTas.recedui.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.github.bmTas.recedui.common.ShapePainters;
import com.github.bmTas.recedui.def.component.IUiButton;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class ArrowButton extends JButton implements IUiButton {
	private int direction = ShapePainters.UP;
	private final IStyle style;
	private JLabel label;
	private JPanel pnl = new JPanel(new BorderLayout());
	
    public ArrowButton(IStyle style, int direction) {
		super();
		this.direction = direction;
		this.style = style == null ? StyleManager.EMPTY_STYLE : style;
		int iconHeight = SwingUtils.values().stdIconHeight();
		this.setPreferredSize(new Dimension(iconHeight , iconHeight));
		
		pnl.add(this, BorderLayout.CENTER);
		
		this.style.register(this);
	}



	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.component.IBmComponent#getStyleId()
	 */
	@Override
	public IStyle getStyleId() {
		return style;
	}


	@Override
	public JLabel getFieldsLabel() {
		return label;
	}

	@Override
	public void setFieldsLabel(JLabel label) {
		this.label = label;
	}


	@Override
	public JComponent getGuiContainer() {
		return pnl;
	}



	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.def.component.IBmButton#getJComponent()
	 */
	@Override
	public JButton getMainComponent() {
		return this;
	}


	@Override
	public void paint(Graphics g) {
    	super.paint(g);
    	Dimension sz = getSize();
    	
    	ShapePainters.paintTriangle(g, direction, 0, 0, sz.width, sz.height);
    }



	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}



	/**
	 * @param direction the direction to set
	 */
	public void setDirection(int direction) {
		if (this.direction != direction) {
			this.direction = direction;
			//System.out.print("Revalidate arrow !!  " + direction + " " + (direction == ShapePainters.RIGHT));
			revalidate();
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					revalidate();
					repaint();
				}
			});
		}
	}
}
