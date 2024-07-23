package com.github.bmTas.recedui.dialog;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Purpose: provide a front end to JOptionPane
 * @author Bruce Martin
 *
 */

public class UiOptionPane {
	
	public static enum OptionType {
		DEFAULT_OPT(JOptionPane.DEFAULT_OPTION),
		YES_NO(JOptionPane.YES_NO_OPTION),
		YES_NO_CANCEL(JOptionPane.YES_NO_CANCEL_OPTION),
		OK_CANCEL(JOptionPane.OK_CANCEL_OPTION)
		;
		
		private final int option;

		private OptionType(int option) {
			this.option = option;
		}
	}
	
	public static enum MessageType {
		ERROR(JOptionPane.ERROR_MESSAGE),
		INFORMATION(JOptionPane.INFORMATION_MESSAGE),
		WARNING(JOptionPane.WARNING_MESSAGE),
		QUESTION(JOptionPane.QUESTION_MESSAGE),
		PLAIN(JOptionPane.PLAIN_MESSAGE)
		;
		
		private final int option;

		private MessageType(int option) {
			this.option = option;
		}
	}

	
	public static enum ResultType {
		CLOSED(JOptionPane.CLOSED_OPTION),
		YES_OK(JOptionPane.YES_OPTION),
		NO(JOptionPane.NO_OPTION),
		CANCEL(JOptionPane.CANCEL_OPTION)
		;
		
		private final int resultCode;

		private ResultType(int resultCode) {
			this.resultCode = resultCode;
		}
	}


	/**
	 * Show a Confirmation Dialog
	 * @param message message to be displayed
	 * @param optionType ResultType e.g. Yes/No
	 * @return Conformation Builder
	 */
	public static IConfirmBuilder newConfirmDialog(Object message, OptionType optionType) {
		return new ConfirmBuilder(message, optionType);
	}
	

	/**
	 * Show a Message Dialog
	 * @param message message to be displayed
	 * @return Message Builder
	 */
	public static IMessageBuilder newMessageDialog(Object message) {
		return new MessageBuilder(message);
	}

	
	public static class BaseBuilder<Builder extends BaseBuilder<?>> {
		Component parentComponent;
		final Object message;
		String title = UIManager.getString("OptionPane.titleText");
		MessageType messageType = MessageType.QUESTION;
        Icon icon;
        @SuppressWarnings("unchecked")
		Builder self = (Builder) this;
        
        
		
		public BaseBuilder(Object message) {
			super();
			this.message = message;
		}



		/**
		 * @param parentComponent the parentComponent to set
		 */
		public Builder setParentComponent(Component parentComponent) {
			this.parentComponent = parentComponent;
			return self;
		}



		/**
		 * @param title the title to set
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return self;
		}



		/**
		 * @param messageType the messageType to set
		 */
		public Builder setMessageType(MessageType messageType) {
			this.messageType = messageType;
			return self;
		}



		/**
		 * @param icon the icon to set
		 */
		public Builder setIcon(Icon icon) {
			this.icon = icon;
			return self;
		}


	}
	
	public static class ConfirmBuilder extends BaseBuilder<ConfirmBuilder> implements IConfirmBuilder {

		private final OptionType optionType;
//		String title = UIManager.getString("OptionPane.titleText");
//		MessageType messageType = MessageType.QUESTION;
//        Icon icon;
        
        
		
		public ConfirmBuilder(Object message, OptionType optionType) {
			super(message);
			this.optionType = optionType;
		}





		@Override
		public ResultType show() {
			int result = JOptionPane.showConfirmDialog(
					parentComponent, message, title, 
					optionType.option, messageType.option, icon);

			for (ResultType rt : ResultType.values()) {
				if (rt.resultCode == result) {
					return rt;
				}
			}
			return ResultType.CLOSED;
		}
	}

	public static class MessageBuilder extends BaseBuilder<MessageBuilder> implements IMessageBuilder {
		
		public MessageBuilder(Object message) {
			super(message);
		}

		public void show() {
			JOptionPane.showMessageDialog(parentComponent, message, title, messageType.option, icon);
		}
	}
}
