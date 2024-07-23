package com.github.bmTas.recedui.xEnvironment;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.github.bmTas.recedui.def.common.IHasKey;
import com.github.bmTas.recedui.def.component.IUiComponent;





public class SwingUtils {

	private static final Rectangle DEFAULT_USABLE_BOUNDS = new Rectangle(200, 200);
	private static SwingUtils values;
	
	public static void resetValues() {
		values = null;	
	}
	
	public static SwingUtils values() {
		if (values == null) {
			values = new SwingUtils();
		}
		return values;
	}
	
//	public static enum laf {
//		windowsLaf,
//		nimbusLaf,
//		metalLaf,
//		otherLaf
//	};
//	
	private final Color ALTERNATE_ROW_COLOR ;
	private final int HALF_TABLE_ROW_HEIGHT;
	public final int STANDARD_FONT_HEIGHT, STANDARD_FONT_WIDTH;
	public final int TABLE_ROW_HEIGHT;
	public final Font DEFAULT_TABLE_FONT;
	
	public final int COMBO_TABLE_ROW_HEIGHT ;
	public final int TABLE_BUTTON_WIDTH ;
	public final int ONE_CHAR_TABLE_CELL_WIDTH ;
	public final int NORMAL_FIELD_HEIGHT;
	public final int CHECK_BOX_HEIGHT,  CHECK_BOX_WIDTH;
	public final int BUTTON_HEIGHT;// = getButtonHeight();
	public final Font MONO_SPACED_FONT;
	public final int TIP_HEIGHT;
	public final int CHAR_FIELD_WIDTH, CHAR_FIELD_HEIGHT;
	
	public final boolean IS_NIMBUS_LAF; 
	public final boolean IS_WINDOWS_LAF, IS_METAL, IS_MAC; 
	
	private  Font lastMonoSpacedFont;
	
	//private final static int[] ICON_SIZES = {16, 22, 32, 48, 64, 96}; 
	private int iconHeight = 16,
			largeIconHeight = 32,
			toolbarIconHeight = 22;

	private Dimension screenSize;// = Toolkit.getDefaultToolkit().getScreenSize();
	private Rectangle maximumWindowBounds;
	
	SwingUtils() {
		String[] r = {"Aapj"};
		JTextField fld = new JTextField(r[0]);
		JCheckBox chk = new JCheckBox();
		JTable jTable = new JTable();
		JButton btn = new JButton("Aa");
		String s = "ABCDEFGHIJKLMNOPQRST abcdefghijklmnopqrst@#?&\"";
		JTextField jTextField = new JTextField(s);
		JComboBox<String> jComboBox = new JComboBox<String>(r);
		
		boolean createFrame = true;
		JFrame frame = null;
		try {
			GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			createFrame = (gd != null && gd.length > 1);
			if (! createFrame) {
				Dimension minimumSize = btn.getMinimumSize();
				createFrame = minimumSize != null && minimumSize.height <= 0;
			}
		} catch (Exception e) {
		}
		
		if (createFrame) {
			frame = new JFrame();
			JPanel pnl = new JPanel();
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			pnl.add(fld);
			pnl.add(chk);
			pnl.add(jTable);
			pnl.add(btn);
			pnl.add(jTextField);
			pnl.add(jComboBox);
			frame.getContentPane().add(pnl);
			
			frame.pack();
			frame.setVisible(true);
			frame.setVisible(false);
		}
		
		Dimension dim = getSize(fld);
		double d = dim == null ? 19 : dim.getHeight();
		
		dim = getSize(btn);
		BUTTON_HEIGHT = dim == null || dim.height <= 0 ? 20 :  dim.height;
//		int sub = 2;
//		if (Common.LOOKS_INDEX == 0 || Common.LOOKS_INDEX == 2) {
//			sub = 4;
//		}
		Color c = null;
		try { c = UIManager.getColor("Table.alternateRowColor"); } catch (Exception e) {}
		ALTERNATE_ROW_COLOR = c;
		
		TABLE_ROW_HEIGHT = getDefault(jTable.getRowHeight(), 16);
		HALF_TABLE_ROW_HEIGHT = TABLE_ROW_HEIGHT / 2;
		DEFAULT_TABLE_FONT = jTable.getFont();
		
		STANDARD_FONT_HEIGHT = getDefault(fld.getFont().getSize(), 12);
		STANDARD_FONT_WIDTH  = STANDARD_FONT_HEIGHT * 3 / 4;
		Insets insets = jComboBox.getInsets();
		dim =  getSize(jComboBox);
		COMBO_TABLE_ROW_HEIGHT = getDefault(
				dim == null
					? 20
					: (int) (dim.getHeight()) - insets.top - insets.bottom, 20);
		System.out.println("!@!@!@!  " + (dim == null) + " " + (dim == null ? 0 : dim.height) + " " + insets.top + " // " + COMBO_TABLE_ROW_HEIGHT);
		dim = getSize(chk);
		
		if (dim == null) {
			CHECK_BOX_HEIGHT = 20;
			CHECK_BOX_WIDTH = 12;
		} else {
			CHECK_BOX_HEIGHT = chk.getMinimumSize().height;
			CHECK_BOX_WIDTH = chk.getMinimumSize().width;
		}

		if (d <= 0) {
			d = 19;
		}
		NORMAL_FIELD_HEIGHT = (int) d;
//		System.out.println(">>> " + UIManager.getSystemLookAndFeelClassName() + " " + JFrame.isDefaultLookAndFeelDecorated()
//				+ " " + BUTTON_HEIGHT
//				+ " " + ((new JButton("Aa")).getMinimumSize().height)
//				);


		MONO_SPACED_FONT = new Font("Monospaced", Font.PLAIN,  STANDARD_FONT_HEIGHT);
		lastMonoSpacedFont = MONO_SPACED_FONT;

		int tblCellWidth = TABLE_ROW_HEIGHT;
		LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
		
		String lafName = lookAndFeel.getName();
		lafName = lafName == null ? "" : lafName;
		System.out.println("++++++++++  " + lafName);
		IS_WINDOWS_LAF = lafName.toLowerCase().contains("windows");
		IS_NIMBUS_LAF =  "Nimbus".equals(lafName);
		IS_METAL = "Metal".equals(lafName);
		IS_MAC = lafName.toLowerCase().startsWith("mac ");
        if (lookAndFeel != null && IS_NIMBUS_LAF) {
        	tblCellWidth = COMBO_TABLE_ROW_HEIGHT;
        }

		ONE_CHAR_TABLE_CELL_WIDTH = tblCellWidth;
		TABLE_BUTTON_WIDTH = ONE_CHAR_TABLE_CELL_WIDTH / 3;

		TIP_HEIGHT = STANDARD_FONT_HEIGHT * 11;


		CHAR_FIELD_WIDTH  = jTextField.getPreferredSize().width / s.length();
		CHAR_FIELD_HEIGHT = jTextField.getPreferredSize().height;

		System.out.println("### '''''''''''''''''''''''''''''''");
		System.out.println("###            Font Height " + STANDARD_FONT_HEIGHT);
		System.out.println("###           Field Height " + NORMAL_FIELD_HEIGHT);
		System.out.println("###        Checkbox Height " + CHECK_BOX_HEIGHT);
		System.out.println("###       Table Row Height " + TABLE_ROW_HEIGHT);
		System.out.println("### Combo Table Row Height " + COMBO_TABLE_ROW_HEIGHT);
		System.out.println("###      1 char Cell width " + CHAR_FIELD_WIDTH);
		System.out.println("### ...............................");
		
//		JTextField jt = new JTextField();
		
		//System.out.println("~~~ " + jt.getPreferredSize().width + " " + jt.getMinimumSize().width);
		
		if (createFrame) {
			frame.dispose();
			frame = null;
		}
	}
	



	
	private boolean printError = true;
	private Dimension getSize(JComponent c ) {
		try {
			Dimension minimumSize = c.getMinimumSize();
			if (minimumSize != null) {
				return minimumSize;
			}
		} catch (Exception e) {
			if (printError) {
				e.printStackTrace();
				printError = false;
			}
		}
		return c.getPreferredSize();
	}

	
	public int stdIconHeight() {
		return iconHeight;
	}
	
	public int largeIconHeight() {
		return largeIconHeight;
	}
	
	public int toolbarIconHeight() {
		return toolbarIconHeight;
	}


	public void setTableCellColors(Component component, JTable table, int row, boolean isSelected) {

		if (component instanceof IUiComponent) {
			component = ((IUiComponent) component).getMainComponent();
		}
		setTableCellBackGround(component, table, row, isSelected);
		if (isSelected) {
			if (! (component instanceof JComboBox)) {
				component.setForeground(table.getSelectionForeground());
			}
		} else {
			component.setForeground(table.getForeground());
		}

	}


	public void setTableCellBackGround(Component component, JTable table, int row, boolean isSelected) {

		//System.out.println(" --> " +table.getBackground() + " " + table.getSelectionBackground()) ;

		Color background;
		
		if (component instanceof JComponent) {
			((JComponent) component).setOpaque(true);
		}

		if (isSelected) {
			background = table.getSelectionBackground();
		} else {
			background = table.getBackground();
			if (IS_NIMBUS_LAF) {
			   	background = Color.WHITE;
			    if ( row % 2 != 0 && values.ALTERNATE_ROW_COLOR != null) {
			    	background = values.ALTERNATE_ROW_COLOR;
			    }
			}
		}
		component.setBackground(background);
	}
	
//    public void updateFontIcon(int iconSize) {
//    	int i;
//    	for (i = 0; i < ICON_SIZES.length-3 && iconSize > ICON_SIZES[i]; i++) { }
//    	
//    	if (i > 0) {
//    		updateFontIcon(ICON_SIZES[i] / ICON_SIZES[0], ICON_SIZES[i++], ICON_SIZES[i++], ICON_SIZES[i]);
//    	}
//	}
//	
//    public void updateFontIcon(double fontScaling, int iconSize1, int iconSize2, int iconSize3) {
//    	
//    	//HashSet<FontUIResource> used = new HashSet<FontUIResource>(200);
//       	HashSet<Font> usedFont = new HashSet<Font>(200);
//       	System.out.println(" ----- part 1 -----");
//    	updateDefaullts(fontScaling, usedFont, UIManager.getDefaults());
//       	System.out.println(" ----- part 2 -----");
//       	updateDefaullts(fontScaling, usedFont, UIManager.getLookAndFeelDefaults());
//   	
//    	iconHeight = iconSize1;
//    	toolbarIconHeight = iconSize2;
//    	largeIconHeight = iconSize3;
//    }
//
//	private void updateDefaullts(double fontScaling, HashSet<Font> usedFont, UIDefaults uidef) {
//		for (Entry<Object,Object> e : uidef.entrySet()) {
//    		Object val = e.getValue();
//    		Font font = null;
//    		Object key = e.getKey();
//			if (val== null || usedFont.contains(val)) {
//    			
//    		} else if (val instanceof FontUIResource ) {
// 				font = updateFont(val, fontScaling, usedFont);
//				System.out.println(" ** " + key + " " + font.getSize());
//    		} else if (val instanceof Font) {
//				font = updateFont(val, fontScaling, usedFont);
//    			System.out.println(" -- " + key + " " + font.getSize());
//    		} else if (key != null && key.toString().toLowerCase().indexOf("font") >= 0) {
//	   			font = updateFont(UIManager.get(key), fontScaling, usedFont);
//    	    } 
//    		if (font != null) {
//    			UIManager.put(key, font);
//    		}
//    	}
//	}
	
	public Font updateFont(Object val, double fontScaling,  HashSet<Font> usedFont) {
		if (val== null || usedFont.contains(val)) {
			
		} else if (val instanceof FontUIResource ) {
			FontUIResource fui = (FontUIResource)val;
			FontUIResource nfr = new FontUIResource(fui.getName(), fui.getStyle(), (int) (fui.getSize() * fontScaling + 0.4));
			usedFont.add(nfr);
			return nfr;
		} else if (val instanceof Font) {
			Font f = ((Font) val);
			f = f.deriveFont((float) (f.getSize2D() * fontScaling));
			usedFont.add(f);
			return f;
	    }
		return null;
	}



	private static int getDefault(int val, int defaultVal) {
		int ret = val;
		if (ret <= 0) {
			ret = defaultVal;
		}
		return ret;
	}

	public static int calculateTableHeight(int records, int maxHeight) {
		return Math.min(maxHeight, (2*records + 3) * values.TABLE_ROW_HEIGHT / 2 + values.HALF_TABLE_ROW_HEIGHT);
	}


	public static int calculateComboTableHeight(int records, int maxHeight) {
		return Math.min(
				maxHeight,
				(records + 1) * values.COMBO_TABLE_ROW_HEIGHT + values.HALF_TABLE_ROW_HEIGHT);
	}

	/**
	 * Get standard sized font
	 * @return standard sized font
	 */
	public static Font getMonoSpacedFont() {
		return values.MONO_SPACED_FONT;
	}



	/**
	 * Get standard sized font
	 * @return standard sized font
	 */
	public static Font getMonoSpacedFont(int height) {

		if (height == values.STANDARD_FONT_HEIGHT) {
			return values.MONO_SPACED_FONT;
		}
		Font f = values.lastMonoSpacedFont;
		if (f.getSize() == height) {
			return f;
		}
		f =  new Font("Monospaced", Font.PLAIN,  height);
		values.lastMonoSpacedFont = f;
		return f;
	}


	public static void addKeyListnerToContainer(Container c, KeyAdapter keyListner) {
		
		Component[] clist = c.getComponents();
		
		for (Component cc : clist) {
			if (cc instanceof Container) {
				addKeyListnerToContainer((Container) cc, keyListner);
			} else {
				cc.addKeyListener(keyListner);
			}
		}
		c.addKeyListener(keyListner);
	}


	public static void setCombo(JComboBox combo, Object value) {

		combo.setSelectedItem(value);

		if (value != null && ! value.equals(combo.getSelectedItem())) {
			Object o;
			for (int i = 0; i < combo.getItemCount(); i++) {
				o = combo.getItemAt(i);
				if (o != null
				&& (   o.equals(value)
					|| (   o instanceof IHasKey
						&& ((IHasKey) o).getKey().equals(value)))) {
					combo.setSelectedIndex(i);
					break;
				}
			}
		}

	}


	public static JPanel getPanelWith(JComponent c) {
		JPanel p = new JPanel();
		p.add(c);
		return p;
	}

	/**
	 * 
	 * @return full screen dimensions
	 */
	public Dimension getFullScreenSize() {
		Dimension ret = screenSize;
		if (screenSize == null) {
			try {
				screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				ret = screenSize;
			} catch (HeadlessException e) {
				e.printStackTrace();
				
				try {
					GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
					if (gd.length > 0) {
						Window fullScreenWindow = gd[0].getFullScreenWindow();
						ret = fullScreenWindow.getBounds().getSize();
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		if (ret == null) {
			throw new RuntimeException("Re~SwingUtils: Can not retrieve screen size");
		}
		
		return ret;
	}
	
	/**
	 * @return standard usable screen bounds (screen size excluding standard OS inserts)
	 */
	public Rectangle getUsableScreenBounds() {
		
		if (maximumWindowBounds == null) {
			try {
				maximumWindowBounds= GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			} catch (HeadlessException e) {
				try {
					GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
					if (gd.length > 0) {
						Window fullScreenWindow = gd[0].getFullScreenWindow();
						maximumWindowBounds = fullScreenWindow.getBounds();
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				}
			}
		}
		return maximumWindowBounds == null ? DEFAULT_USABLE_BOUNDS : maximumWindowBounds;
	}

	public static void clickOpenBtn(JFileChooser fileChooser, boolean doEnter) {
		String s = System.getProperty("java.version").substring(0, 4);
		try {
			if (s.startsWith("1.5.") || s.startsWith("1.6.")) {
				if (doEnter) {
					fileChooser.getActionForKeyStroke(
							KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
								.actionPerformed(null);
				}
			} else {
				JButton btn = fileChooser.getUI().getDefaultButton(fileChooser);
				if (btn != null) {
					btn.doClick();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
	public static void showInBrowser(URI uri) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

                desktop.browse(uri);
            } catch ( Exception ex ) {
                System.err.println( "Error showing Web page" + ex.getMessage() );
            }
		}
	}

	   /**
     * Adjusts the column widths based on the component widths
     * @param table table to fix
     * @param minColumns minumum number of columns in table (
     */
    public static void calcColumnWidths(JTable table, int minColumns) {
    	calcColumnWidths(table, minColumns, -1, 0);
    }

    public static void calcColumnWidths(JTable table, int minColumns, int maxColWidth) {
    	calcColumnWidths(table, minColumns, maxColWidth, 0);
    }

	private static final int TABLE_WINDOW_SIZE_TO_CHECK = 200;
	private static final int MINIMUM_MAX_COLUMN_WIDTH   = 100;
	private static final int DEFAULT_ROOM_AROUND_SCREEN = 34;

    public static void calcColumnWidths(JTable table, int minColumns, int maxColWidth, int fieldWidthAddition) {
    	if (maxColWidth < 0) {
    		int screenWidth = table.getVisibleRect().width;
        	maxColWidth = Math.max(screenWidth * 2 / 3, MINIMUM_MAX_COLUMN_WIDTH);
     	}
        JTableHeader header = table.getTableHeader();

        TableCellRenderer defaultHeaderRenderer = null;

        if (header != null) {
            defaultHeaderRenderer = header.getDefaultRenderer();
        }

        TableColumnModel columns = table.getColumnModel();
        TableModel data = table.getModel();

        int margin = Math.min(4, columns.getColumnMargin()); // only JDK1.3
        Rectangle visbleRect = table.getVisibleRect();
        int screenWidth = visbleRect.width;
        int screenStart = visbleRect.y;
//        int screenheight = visbleRect.height;

        if (screenWidth == 0) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            screenWidth = dim.width - DEFAULT_ROOM_AROUND_SCREEN;
//            screenheight = dim.height - DEFAULT_ROOM_AROUND_SCREEN;
        }
        int firstRow = Math.max(0, screenStart - TABLE_WINDOW_SIZE_TO_CHECK);
        int rowCount = Math.min(data.getRowCount(), 2 * TABLE_WINDOW_SIZE_TO_CHECK);
        
        //System.out.println("First Row: " + firstRow + "\t\tRow Count: " + rowCount);
        //System.out.println(screenStart + " " + screenheight);
        //int totalWidth = 0;
        //System.out.println("Column Widths ==> " + visbleRect.width + " " + maxColWidth);

        //System.out.println();
        TableColumn column;
        for (int i = columns.getColumnCount() - 1; i >= 0; --i) {
            column = columns.getColumn(i);

            int width = -1;

            TableCellRenderer h = column.getHeaderRenderer();

            if (h == null) {
                h = defaultHeaderRenderer;
            }

            if (h != null) {
                Component c = h.getTableCellRendererComponent(table, column
                        .getHeaderValue(), false, false, -1, i);

                width = c.getPreferredSize().width;
            }

            width = getColumnWidth(table, i, firstRow, rowCount, width, fieldWidthAddition);
//            for (int row = rowCount - 1; row >= firstRow; --row) {
//                TableCellRenderer r = table.getCellRenderer(row, i);
//
//                try {
//	               Component c = r.getTableCellRendererComponent(
//	                		table,
//	                		data.getValueAt(row, columnIndex), false, false, row, i);
//                	width = Math.max(width, c.getPreferredSize().width);
//                } catch (Exception e) {
//                	System.out.println("Error Row,col= " + row + ", " + columnIndex);
//				}
//            }

            if (width >= 0) {
                //System.out.println("### " + columns.getColumnCount() + " " + i + " Width=" + width);
                if (columns.getColumnCount() == minColumns) {
                    column.setPreferredWidth(width + margin); // <1.3: without margin
                } else {
                    column.setPreferredWidth(Math.min(width + margin, maxColWidth)); // <1.3: without margin
                }

                //column.setPreferredWidth(width + margin);
//                if (width > maxColWidth) {
//                    System.out.println("~~ " + width + " " + maxColWidth);
//                }
            }

            //totalWidth += column.getPreferredWidth();
        }
    }

    public static int getColumnWidth(JTable table, int idx, int firstRow, int rowCount, int defaultWidth) {
    	return getColumnWidth(table, idx, firstRow, rowCount, defaultWidth, 0);
    }

    public static int getColumnWidth(JTable table, int idx, int firstRow, int rowCount, int defaultWidth, int incBy) {
   	int width= defaultWidth;
    	int columnIndex = table.getColumnModel().getColumn(idx).getModelIndex();
    	TableModel data = table.getModel();

//    	System.out.println();
//    	System.out.println(" ~~~ idx = " + idx);
//    	System.out.println();
        for (int row = rowCount - 1; row >= firstRow; --row) {
            TableCellRenderer r = table.getCellRenderer(row, idx);

            try {
            	Component c = r.getTableCellRendererComponent(
                		table,
                		data.getValueAt(row, columnIndex), false, false, row, idx);
                //System.out.print("  >" + width + " " + c.getPreferredSize().width);
            	width = Math.max(width, c.getPreferredSize().width + incBy);
            } catch (Exception e) {
            	System.out.println("Error Row,col= " + row + ", " + columnIndex);
			}
        }
        return width;
   }
    

    public static Window getParent(Component c) {
    	if (c == null) { return null; }
    	
    	do {
	    	if (c instanceof Frame || c instanceof Dialog) {
	    		return (Window) c;
	    	}
	    	c = c.getParent();
    	} while (c != null);
    	return null;
    	
    }
}
