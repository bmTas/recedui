package com.github.bmTas.recedui.def.menu;

import javax.swing.Action;
import javax.swing.JMenuItem;

import com.github.bmTas.recedui.def.component.IUiComponent;

/**
 * Forms a common bas class for JMenu and JPopupMenu
 * 
 * @author Bruce Martin
 *
 */
public interface IUiStdMenu extends IUiComponent {

    public JMenuItem add(JMenuItem menuItem);

    /**
     * Creates a new menu item with the specified text and appends
     * it to the end of this menu.
     *
     * @param s the string for the menu item to be added
     */
    public JMenuItem add(String s);

    /**
     * Appends a new menu item to the end of the menu which
     * dispatches the specified <code>Action</code> object.
     *
     * @param a the <code>Action</code> to add to the menu
     * @return the new menu item
     * @see Action
     */
    public JMenuItem add(Action a);
    
    public void addSeparator();
    

    public String getLabel();

	
	public void remove(int pos);
	
	public void removeAll();

}
