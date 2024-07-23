package com.github.bmTas.recedui.def.component;

public interface IFocusListner {
	   /**
     * Invoked when a component gains the keyboard focus.
     * @param e the event to be processed
     */
    public void focusGained(IFocusEvent e);

    /**
     * Invoked when a component loses the keyboard focus.
     * @param e the event to be processed
     */
    public void focusLost(IFocusEvent e);

}
