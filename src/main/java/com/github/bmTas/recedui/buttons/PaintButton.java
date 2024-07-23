package com.github.bmTas.recedui.buttons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.bmTas.recedui.common.ShapePainters;
import com.github.bmTas.recedui.def.component.IUiButton;
import com.github.bmTas.recedui.icons.MacComboIcon;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class PaintButton extends JButton  {

	
	public PaintButton() {
		int iconHeight = SwingUtils.values().stdIconHeight() ;
		this.setPreferredSize(new Dimension(iconHeight , iconHeight));
		
		Color lightBlue      = new Color(124, 148, 255);
		
		super.setForeground(Color.WHITE);
		super.setBackground(lightBlue);
		super.setOpaque(true);
		super.setBorderPainted(false);
		//super.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
		//super.setPreferredSize(new Dimension(iconHeight+2, iconHeight+2));
	}

	public void paint(Graphics g) {
    	super.paint(g);
    	Dimension sz = getSize();
    	
    	System.out.print("paint " + super.getBackground() );
    	ShapePainters.paintMacCombo(g, 0, 0, sz.width, sz.height);
    }

	public static void main(String[] args)  throws IOException {
		JFrame tf = new JFrame();
		PaintButton pb1 = new PaintButton();
		int iconHeight = SwingUtils.values().stdIconHeight() * 1;
		JButton pb2 = new JButton(new MacComboIcon(iconHeight, iconHeight));
//		Color lightBlue      = new Color(124, 148, 255);
//		pb2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLUE));
		pb2.setForeground(Color.WHITE);
//		pb2.setBackground(Color.RED);
		
//		pb2.setOpaque(true);
//		pb2.setBorderPainted(false);
//		pb2.setIcon(new MacComboIcon(iconHeight, iconHeight));
		//pb2.repaint();
		//pb2.setContentAreaFilled(true);
		//pb2.setBorderPainted(false);
		//pb2.setFocusPainted(false);
		
	
		JPanel p = new JPanel(new FlowLayout());
		p.add(new JTextField("123456   "));
		p.add(pb2);
		p.add(new UiPanelButton(StyleManager.styles().comboArrow()).getGuiContainer());
		JComboBox<String> comp = new JComboBox<String>(new String[] {"1", "2", "3"});
		p.add(comp);
		

		
		tf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		tf.getContentPane().add(p);
		tf.pack();
		tf.setVisible(true);
	}
}
