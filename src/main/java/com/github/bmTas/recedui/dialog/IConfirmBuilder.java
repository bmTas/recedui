package com.github.bmTas.recedui.dialog;

import java.awt.Component;

import javax.swing.Icon;

import com.github.bmTas.recedui.dialog.UiOptionPane.MessageType;
import com.github.bmTas.recedui.dialog.UiOptionPane.ResultType;

public interface IConfirmBuilder {

	/**
	 * @param parentComponent the parentComponent to set
	 */
	IConfirmBuilder setParentComponent(Component parentComponent);

	/**
	 * @param title the title to set
	 */
	IConfirmBuilder setTitle(String title);

	/**
	 * @param messageType the messageType to set
	 */
	IConfirmBuilder setMessageType(MessageType messageType);

	/**
	 * @param icon the icon to set
	 */
	IConfirmBuilder setIcon(Icon icon);

	ResultType show();

}