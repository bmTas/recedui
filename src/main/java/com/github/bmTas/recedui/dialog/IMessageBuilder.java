package com.github.bmTas.recedui.dialog;

import java.awt.Component;

import javax.swing.Icon;

import com.github.bmTas.recedui.dialog.UiOptionPane.MessageType;

public interface IMessageBuilder {

	/**
	 * @param parentComponent the parentComponent to set
	 */
	IMessageBuilder setParentComponent(Component parentComponent);

	/**
	 * @param title the title to set
	 */
	IMessageBuilder setTitle(String title);

	/**
	 * @param messageType the messageType to set, Possible values include<ul>
	 * <li>MessageType.ERROR
	 * <li>MessageType.INFORMATION
	 * <li>MessageType.WARNING
	 * <li>MessageType.QUESTION
	 * <li>MessageType.PLAIN
	 * </UL>
	 */
	IMessageBuilder setMessageType(MessageType messageType);

	/**
	 * @param icon the icon to set
	 */
	IMessageBuilder setIcon(Icon icon);

	/**
	 * Show the message
	 */
	void show();

}