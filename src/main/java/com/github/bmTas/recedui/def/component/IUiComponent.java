package com.github.bmTas.recedui.def.component;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.github.bmTas.recedui.styles.IStyle;

public interface IUiComponent extends IGetContainer {
	public IStyle getStyleId();

	public JComponent getMainComponent();

	
	/**
	 * Get the fields label
	 * 
	 * @return fields label
	 */
	public JLabel getFieldsLabel();
	public void setFieldsLabel(JLabel label);
	public void setVisible(boolean b);
	public void setToolTipText(String text);
	public void setBounds(int x, int y, int width, int height);
	public void setBounds(Rectangle r);
	public void setPreferredSize(Dimension preferredSize);
	public Dimension getPreferredSize();
	int getX();
	int getY();

}
