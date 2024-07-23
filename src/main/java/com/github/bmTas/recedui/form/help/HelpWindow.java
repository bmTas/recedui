/*
 * @Author Bruce Martin
 * Created on 11/08/2005
 *
 * Purpose: A Panel with built in Help screens
 */
package com.github.bmTas.recedui.form.help;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.github.bmTas.recedui.xEnvironment.SwingUtils;


/**
 * This class will display a Help Screen when the user hits the
 * PF1 key.
 *
 * @author Bruce Martin
 *
 */
public class HelpWindow extends KeyAdapter implements HyperlinkListener, IHelpDisplay {

    public static final JFrame HELP_FRAME = new JFrame("Help");

    private static JEditorPane helpDtls = new JEditorPane();
    private static boolean toInit = true;

    private URL helpURL = null;
    private URI helpURI = null;



    /**
     * define a help window adapter
     *
     * @param url Help URL
     */
    public HelpWindow(final URL url) {
        super();

        helpURL = url;
    }

    /* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.form.help.IShowHelp#register(java.awt.Component)
	 */
    @Override
	public void register(Component c) {
    	if (c instanceof JPanel) {
    		registerComponent((JPanel)c, -1);
    	} else {
    		registerOneComponent(c);
    	}
    }
    
    private void registerComponent(JComponent component, int depth) {
        int i;
        Component c;
        registerOneComponent(component);

        for (i = 0; i < component.getComponentCount(); i++) {
        	c = component.getComponent(i);
        	//registerOneComponent(c);

        	if (depth < 4 && c instanceof JComponent) {
        		registerComponent((JComponent) c, depth + 1);
        	} else {
        		registerOneComponent(c);
        	}
        }
    }
    
    private final void registerOneComponent(Component component) {
    	component.addKeyListener(this);
    	//componentList.add(component);
    }



    /**
     * @see java.awt.event.KeyAdapter#keyReleased
     */
    public final void keyReleased(KeyEvent event) {
         if (event.getKeyCode() == KeyEvent.VK_F1) {
        	 showHelp();
        }
    }


    /**
     * Shows the help screen
     *
     */
    public final void showHelp() {

    	if (helpURI == null) {
    		showURL(helpURL);
    	} else {
			SwingUtils.showInBrowser(helpURI);
    	}
        
     }


    /**
     * Shows the help screen
     *
     * @param url url to display
     */
    public final void showURL(URL url) {

        if (url != null) {
            if (toInit) {
                helpDtls.setEditable(false);
                HELP_FRAME.getContentPane().add(new JScrollPane(helpDtls));
                helpDtls.addHyperlinkListener(this);
                toInit = false;
            }

            try {
                helpDtls.setPage(url);
                HELP_FRAME.setVisible(true);
            } catch (Exception e) {
                System.out.println("Error Loading Help Screen "
                        + url + ": " + e.getMessage());
            }
        }
    }

    /* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.form.help.IShowHelp#setHelpURL(java.net.URL)
	 */
    @Override
	public final void setHelpURL(URL helpUrl) {
        this.helpURL = helpUrl;
    }


    /* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.form.help.IShowHelp#setHelpURI(java.net.URI)
	 */
	@Override
	public final void setHelpURI(URI helpURI) {
		this.helpURI = helpURI;
	}


	/**
     * @see javax.swing.event.HyperlinkListher#hyperlinkUpdate
     */
    public final void hyperlinkUpdate(HyperlinkEvent event) {

        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
             showURL(event.getURL());
        }
    }
}
