package com.github.bmTas.recedui.def.table;

import java.awt.event.MouseEvent;

public interface IUiTableMouseListner {


	/**
	 * {@link java.awt.event.MouseListener#mouseClicked(MouseEvent e)}
	 */
    public void mouseClicked(UiTableMouseEvent e);

	/**
	 * {@link java.awt.event.mouseReleased#mousePressed(MouseEvent e)}
	 */
    public void mousePressed(UiTableMouseEvent e);

	/**
	 * {@link java.awt.event.MouseListener#mouseReleased(MouseEvent e)}
	 */
    public void mouseReleased(UiTableMouseEvent e);

	/**
	 * {@link java.awt.event.MouseListener#mouseEntered(MouseEvent e)}
	 */
    public void mouseEntered(UiTableMouseEvent e);

	/**
	 * {@link java.awt.event.MouseListener#mouseExited(MouseEvent e)}
	 */
    public void mouseExited(UiTableMouseEvent e);
}
