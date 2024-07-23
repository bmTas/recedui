package com.github.bmTas.recedui.standard.component;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.styles.IStyle;

@SuppressWarnings("serial")
public abstract class BasicPanel extends JPanel implements IUiComponent{

	public final IStyle style;
	
	private JLabel fieldLabel;
	
	public BasicPanel(IStyle style, LayoutManager layout) {
		super(layout == null ? new FlowLayout() : layout, true);
		
		this.style = style;
	}

	@Override
	public final IStyle getStyleId() {
		return style;
	}

	@Override
	public final JComponent getGuiContainer() {
		return this;
	}

	@Override
	public final JLabel getFieldsLabel() {
		return fieldLabel;
	}

	@Override
	public final void setFieldsLabel(JLabel label) {
		this.fieldLabel = label;
	}

//	@Override
//	public JComponent getMainComponent() {
//		return null;
//	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		
		if (fieldLabel != null) {
			fieldLabel.setVisible(aFlag);
		}
	}
}
