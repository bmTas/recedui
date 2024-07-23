package com.github.bmTas.recedui.form;

import java.awt.Component;
import java.awt.Dimension;
import java.net.URI;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.form.help.HelpWindow;
import com.github.bmTas.recedui.form.help.IHelpDisplay;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

/**
 * This class holds builder's for basic forms
 *
 * <pre>
 *   FormBuilder.twoColumnForm("MyForm")  .form()
 *         .l(  "label 1", field1, CellPosition.LEFT )
 *         .l(  "label 2", field2  )     ,setGap(ScreenConstants.GAP2) 
 *         
 *         .l(  "label 3", field3  )
 *         .l(  "label 4", field4  )
 * </pre>
 * 
 * @author Bruce Martin
 *
 */
public class FormBuilder {
	public static final CellPosition LEFT = CellPosition.LEFT;
	public static final CellPosition LEFT_1_COL = CellPosition.LEFT_1_COL;
	public static final CellPosition LEFT_2_COL = CellPosition.LEFT_2_COL;
	public static final CellPosition RIGHT = CellPosition.RIGHT;

	public static final CellPosition FULL_WIDTH = CellPosition.FULL_WIDTH;
	public static final CellPosition FULL_HEIGHT = CellPosition.FULL_HEIGHT;
	
	private static IHelpDisplay NULL_SHOW_HELP = new IHelpDisplay() {
		
		@Override public void setHelpURL(URL helpUrl) {	}		
		@Override public void setHelpURI(URI helpURI) {	}
		@Override public void register(Component c) { }
	};

	//public static CellPosition createMultiRowCell(HorizontalPosition horizontalPosition, VerticalPosition verticalPosition
	/** 
	 * Build a 23 Column form
	 * <pre>
	 *   FormBuilder.twoColumnForm("MyForm")  .form()
	 *         .l(  "label 1", field1, CellPosition.LEFT )
	 *         .l(  "label 2", field2  )     ,setGap(ScreenConstants.GAP2) 
	 *         
	 *         .l(  "label 3", field3  )
	 * </pre>
	 * @param formName name of the form, used to set field names - useful for testing
	 * 
	 * @return Form attribute builder
	 */
	public static TwoColAttr twoColumnForm(String formName) {
		return new TwoColAttr(formName, null);
	}
	
	/**
	 * Build a 2 Column form
	 * <pre>
	 *   FormBuilder.twoColumnForm("MyForm", myPanel)  .form()
	 *         .l(  "label 1", field1, CellPosition.LEFT )
	 *         .l(  "label 2", field2  )     ,setGap(ScreenConstants.GAP2) 
	 *         
	 *         .l(  "label 3", field3  )
	 * </pre>
	 * @param formName name of the form, used to set field names - useful for testing
	 * 
	 * @return Form attribute builder
	 */
	public static TwoColAttr twoColumnForm(String formName, JPanel panel) {
		return new TwoColAttr(formName, panel);
	}
	
	public static ThreeColAttr threeColumnForm(String formName, JPanel panel) {
		return new ThreeColAttr(formName, panel);
	}


	/**
	 * Gather Attributes for a 2 column Form
	 *
	 */
	public static class TwoColAttr extends AttributeBuilder<TwoColAttr> {
		
		public TwoColAttr(String formName, JPanel panel) {
			super(formName, panel);
		}
		

		public TwoColAttr setColumnSizes(double left, double label, double gap, double field, double right) {
			colGaps = new double[] {left, label, gap, field, right};
			return this;
		}
		
		public TwoColumnFormBuilder form() {
			return new TwoColumnFormBuilder(
					formName, panel, 
					rowGaps, colGaps,
					labelPosition, fieldPosition, 
					fieldColumnCount);
		}
	}
	
	/**
	 * Gather Attributes for a 2 column Form
	 *
	 */
	public static class ThreeColAttr extends AttributeBuilder<ThreeColAttr> {
		
		public ThreeColAttr(String formName, JPanel panel) {
			super(formName, panel);
		}
		

		public ThreeColAttr setColumnSizes(double left, 
				double label, double gap, double field, double gap2, double field2,
				double right) {
			
			colGaps = new double[] {left, label, gap, field, gap2, field2, right};
			
			return this;
		}
		
		public ThreeColumnFormBuilder form() {
			return new ThreeColumnFormBuilder(
					formName, panel, 
					rowGaps, colGaps,
					labelPosition, fieldPosition, lastPosition,
					fieldColumnCount,
					helpMgr);
		}
	}

	public static class AttributeBuilder<Bldr extends AttributeBuilder<Bldr>> {
		final String formName; 
		final JPanel panel;

		double[] rowGaps,  colGaps;
		CellPosition labelPosition=CellPosition.DEFAULT_LABEL_POSITION, 
					 fieldPosition=CellPosition.DEFAULT_FIELD_POSITION,
					 lastPosition=CellPosition.FULL_WIDTH;
		int fieldColumnCount=1;
		
		IHelpDisplay helpMgr = NULL_SHOW_HELP;
		
		@SuppressWarnings("unchecked")
		Bldr self = (Bldr) this;
		
		public AttributeBuilder(String formName, JPanel panel) {
			super();
			this.formName = formName;
			this.panel = panel;
		}
		
		public final Bldr setRowSizes(double top, double row, double gap, double bottom) {
			rowGaps = new double[] {top, row, gap, bottom};
			return self;
		}
		
		/**
		 * This will set the row sizes to anything you like but you need to know what you are doing!!!
		 * @param rowsizes
		 * @return
		 */
		public Bldr setColumnSizesToAnything(double... columnGaps) {
			colGaps = columnGaps;
			return self;
		}
	
		public Bldr setColumnSizes(double left, double label, double gap, double field, double right) {
			colGaps = new double[] {left, label, gap, field, right};
			return self;
		}
		
		
		public final Bldr setLabelPosition(CellPosition labelPosition) {
			this.labelPosition = labelPosition;
			return self;
		}

		public final Bldr setFieldPosition(CellPosition defaultFieldPosition) {
			this.fieldPosition = defaultFieldPosition;
			return self;
		}

		public Bldr setFieldCoumnCount(int columnCount) {
			fieldColumnCount = columnCount;
			return self;
		}

		public Bldr setShowHelp(boolean showHelp) {
			helpMgr = showHelp ? new HelpWindow(null) : NULL_SHOW_HELP;
			return self;
		}

		/**
		 * @param lastPosition the lastPosition to set
		 */
		public final Bldr setLastPosition(CellPosition lastPosition) {
			this.lastPosition = lastPosition;
			return self;
		}

		public Bldr setShowHelp(final URL url) {
			helpMgr = new HelpWindow(url);
			return self;
		}
	}

	public static void setToCommonWidth(int charWidthAdj, int minNumberOfChars, IUiComponent... uiComponents) {
		
		int width = 0;
		for (int i = 0; i < uiComponents.length; i++) {
			width = Math.max(width, uiComponents[i].getGuiContainer().getPreferredSize().width);
		}
		
		if (width > 0) {
			width = calculateWidth(width, charWidthAdj, minNumberOfChars);
			for (IUiComponent c : uiComponents) {
				c.getGuiContainer().setPreferredSize(new Dimension(width, c.getGuiContainer().getPreferredSize().height));
			}
		}
	}
	/**
	 * Set a series of fields to a common width
	 * @param charWidthAdj a character width adjustment
	 * @param minNumberOfChars minium number of characters to allow for
	 * @param components list of all components
	 */
	public static void setToCommonWidth(int charWidthAdj, int minNumberOfChars, JComponent... components) {
		int width = 0;
		for (JComponent c : components) {
			width = Math.max(width, c.getPreferredSize().width);
		}
		
		if (width > 0) {
			width = calculateWidth(width, charWidthAdj, minNumberOfChars);
			for (JComponent c : components) {
				c.setPreferredSize(new Dimension(width, c.getPreferredSize().height));
			}
		}
	}

	private static int calculateWidth(int width, int charWidthAdj, int minNumberOfChars) {
		width = Math.max(
				width + SwingUtils.values().CHAR_FIELD_WIDTH * charWidthAdj, 
				SwingUtils.values().CHAR_FIELD_WIDTH * minNumberOfChars);
		return width;
	}

}
