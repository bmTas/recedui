package com.github.bmTas.recedui.common;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiStdComponent<Component extends JComponent> implements IUiComponent {

	private final IStyle style;
	protected final Component component;
	protected final JComponent container;
	
	private JLabel fieldsLabel;
	
	public UiStdComponent(IStyle style, Component component) {
		this(style, component, component);
	}
	
	public UiStdComponent(IStyle style, JComponent container, Component component) {
		super();
		this.style = style == null ? StyleManager.EMPTY_STYLE : style;
		this.component = component;
		this.container = container;
		
		if (style != null) {
			style.register(this);
		}
	}

	@Override
	public JComponent getGuiContainer() {
		return container;
	}

	@Override
	public Component getMainComponent() {
		return component;
	}

	public void setBorder(Border border) {
		container.setBorder(border);
	}

	public Border getBorder() {
		return container.getBorder();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		container.setBounds(x, y, width, height);
	}
	
	@Override
	public void setBounds(Rectangle r) {
		container.setBounds(r);
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		container.setPreferredSize(preferredSize);
	}

	@Override
	public Dimension getPreferredSize() {
		return container.getPreferredSize();
	}


	@Override
	public int getX() {
		return container.getX();
	}

	@Override
	public int getY() {
		return container.getY();
	}

	@Override
	public IStyle getStyleId() {
		return style;
	}

	@Override
	public JLabel getFieldsLabel() {
		return fieldsLabel;
	}

	@Override
	public void setFieldsLabel(JLabel label) {
		this.fieldsLabel = label;
	}

	@Override
	public void setToolTipText(String text) {
		component.setToolTipText(text);
		if (container != component) {
			container.setToolTipText(text);
		}
//		if (fieldsLabel != null) {
//			fieldsLabel.setToolTipText(text);
//		}
	}
	
	@Override
	public void setVisible(boolean b) {
		container.setVisible(b);
		if (fieldsLabel != null) {
			fieldsLabel.setVisible(b);
		}
	}
}
