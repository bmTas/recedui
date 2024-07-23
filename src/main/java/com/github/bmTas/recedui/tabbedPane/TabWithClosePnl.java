package com.github.bmTas.recedui.tabbedPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIDefaults;

import com.github.bmTas.recedui.xEnvironment.SwingUtils;

@SuppressWarnings("serial")
public class TabWithClosePnl extends JPanel {
    private final JLabel  label    = new JLabel();
    private   final JButton closeBtn = new JButton("X");


    public TabWithClosePnl(String screenName, boolean addCloseBtn) {

        super.setBorder(BorderFactory.createEmptyBorder());
        label.setText(screenName);
        label.setOpaque(true);

        if (screenName != null && screenName.length() > 0) {
        	label.setName(screenName);
        }
        
        if (! addCloseBtn) {
        	add(label);
        } else {
            if (SwingUtils.values().IS_NIMBUS_LAF) {
            	UIDefaults def = new UIDefaults();
            	def.put("Button.contentMargins", new Insets(0,0,0,0));
            	closeBtn.putClientProperty("Nimbus.Overrides", def);

            	closeBtn.setFont(new Font("Monospaced", Font.PLAIN,  (SwingUtils.values().STANDARD_FONT_HEIGHT * 3) / 4));
            	closeBtn.setPreferredSize(new Dimension(SwingUtils.values().STANDARD_FONT_HEIGHT * 5 / 4,
            			SwingUtils.values().STANDARD_FONT_HEIGHT * 3 / 2));
            } else {
            	closeBtn.setFont(new Font("Monospaced", Font.PLAIN,  (SwingUtils.values().STANDARD_FONT_HEIGHT * 2) / 3));
            	closeBtn.setPreferredSize(new Dimension(SwingUtils.values().STANDARD_FONT_HEIGHT, SwingUtils.values().STANDARD_FONT_HEIGHT));
            }
        	closeBtn.setMargin(new Insets(0,0,0,0));
	        if (SwingUtils.values().IS_MAC) {
	        	closeBtn.setBorder(BorderFactory.createEmptyBorder());
		        add(closeBtn);
	      	
		        add(label);
	        } else {
	        	add(label);
			
		        add(closeBtn);        
	        }
        }
    }


    
    @Override
	public synchronized void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		//this.addMouseListener(l);
	}



	public final void addCloseAction(ActionListener closeAction) {
    	closeBtn.addActionListener( closeAction );
    }

    public final void removeCloseAction(ActionListener closeAction) {
    	closeBtn.removeActionListener( closeAction );
    }

    public final void setBackground(Color bg) {
        super.setBackground(bg);

        if (label != null) {
            label.setBackground(bg);
        }
    }

    public final void setTabname(String name) {

        label.setText(name);

        label.revalidate();

        this.revalidate();
    }
}
