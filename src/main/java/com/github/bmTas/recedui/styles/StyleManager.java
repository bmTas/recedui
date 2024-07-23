package com.github.bmTas.recedui.styles;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.github.bmTas.recedui.common.ShapePainters;
import com.github.bmTas.recedui.icons.MacComboIcon;
import com.github.bmTas.recedui.icons.StdComboIcon;
import com.github.bmTas.recedui.icons.TriangleIcon;
import com.github.bmTas.recedui.styles.attributes.ComponentAttributes;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class StyleManager {

	private static final Color NAVY = new Color(0, 0, 80);
	private static StyleManager theStyles = new StyleManager();
	
	public static StyleManager styles() { return theStyles; }
	
	
	public static final EmptyStyle EMPTY_STYLE = new EmptyStyle("default"); 
	private final StandardStyle fileCrumbItem  = new StandardStyle("FileBreadcrumb");
	private final StandardStyle fileCrumbArrow = new StandardStyle("FileBreadcrumb");
	private final StandardStyle basicFileField = new StandardStyle("FileField");
	private final StandardStyle panelFieldStyle = new StandardStyle("PanelFieldStyle");
	private final StandardStyle breadCrumbFileField = new StandardStyle("BreadCrumbFileField");
	private final StandardStyle comboArrow = new StandardStyle("ComboArrow");
	private       StandardStyle comboArrow2 = comboArrow;//new StandardStyle("ComboArrow2");
	private final StandardStyle comboFav   = new StandardStyle("ComboFavorite");
	private final StandardStyle comboFc    = new StandardStyle("ComboFC");
	private final StandardStyle fontChooser    = new StandardStyle("FontChooser");
	private final StandardStyle toolbarBtnsFc  = new StandardStyle("ToolbarBtnsFC");

	private final StandardStyle panelAndFieldStyle = new StandardStyle("PanelAndFieldStyle");
	
	private String comboButtonString = "..";

	
	private final StandardStyle fileTreeStyle = new StandardStyle("FileTree");
	private final StandardStyle fcFileTableStyle = new StandardStyle("FileTable");

	StyleManager() {
		SwingUtils swingVals = SwingUtils.values();
		if (swingVals.IS_NIMBUS_LAF) {
			defineForNimbus();
		} else if (swingVals.IS_WINDOWS_LAF) {
			defineForWindows();
		} else if (swingVals.IS_MAC) {
			defineForMac();
		} else {
			defineForMetal();
		}
	}
	
	public IStyle fileBreadCrumbField() { return breadCrumbFileField; }
	
	public IStyle fileBreadCrumbItem()  { return fileCrumbItem; }
	public IStyle fileBreadCrumbArrow() { return fileCrumbArrow; }
	public IStyle basicFileField( ) { return basicFileField; }
	
	public IStyle panelField( )     { return panelFieldStyle; }
	public IStyle comboBox( )       { return panelFieldStyle; }
	public IStyle comboLike( )      { return panelFieldStyle; }
	public IStyle panelAndField( )  { return panelAndFieldStyle; }
	public IStyle comboArrow( )     { return comboArrow; }
	public IStyle comboArrow2( )    { return comboArrow2; }
	public IStyle comboFavorite( )  { return comboFav; }
	public IStyle comboFileChooser(){ return comboFc; }
	public IStyle fontChooser( ) 	{ return fontChooser; }
	public IStyle toolbarBtnsFileChooser( ) {return toolbarBtnsFc; }
	
	public String getFontChooserStr() { return comboButtonString; }
	
	public ITreeStyle folderTree( ) {return fileTreeStyle; }
	
	public ITableStyle fcFileTable( ) {return fcFileTableStyle; }
	
	private final ComponentAttributes comboFcAttr = new ComponentAttributes(null, null, BorderFactory.createMatteBorder(0, 1, 0, 0, NAVY));
	private final ComponentAttributes fontChAttr = new ComponentAttributes(null, Color.WHITE, BorderFactory.createMatteBorder(0, 1, 0, 0, NAVY));
	
	private void defineForNimbus() {
		System.out.println("***** Nimbus *****");
		setCommonAttributes(Boolean.FALSE, 
				stdField(), //new ComponentAttributes(null, Color.WHITE, BorderFactory.createRaisedSoftBevelBorder()), 
				Boolean.FALSE);
		
		fileTreeStyle.setTreeExpandedIcon(new TriangleIcon(ShapePainters.DOWN, Color.GRAY)); 
		fileTreeStyle.setTreeCollapsedIcon(new TriangleIcon(ShapePainters.RIGHT, Color.GRAY)); 
		int iconHeight = SwingUtils.values().stdIconHeight();
		comboArrow
			.setNormalAttributes(
					new ComponentAttributes(null, null, null) 
						.setContentAreaFilled(Boolean.FALSE)
						.setWidth(iconHeight)
						.setHeight(iconHeight)
						.setIcon(new StdComboIcon(iconHeight, iconHeight)))
			.setContainerAttributes(
					new ComponentAttributes(null, new Color(178, 198, 233), null) 
						.setContentAreaFilled(Boolean.FALSE)
						.setOpaque(true));
//		comboFav
//			.setNormalAttributes(new ComponentAttributes(null, Color.WHITE, null) 
//					.setContentAreaFilled(Boolean.FALSE));
		comboFc.setNormalAttributes(
				comboFcAttr
						.setContentAreaFilled(Boolean.FALSE)
						.setWidth((iconHeight * 7) / 8));
		fontChooser.setNormalAttributes(
				fontChAttr
						.setContentAreaFilled(Boolean.FALSE)
						.setWidth((iconHeight * 7) / 8));
	}
	
	
	private void defineForMetal() {
		System.out.println("***** Metal *****");
		
		int iconHeight = SwingUtils.values().stdIconHeight();


		setCommonAttributes(null, stdField(), null);
	
		fileTreeStyle.setTreeExpandedIcon(new TriangleIcon(ShapePainters.DOWN, Color.GRAY)); 
		fileTreeStyle.setTreeCollapsedIcon(new TriangleIcon(ShapePainters.RIGHT, Color.GRAY)); 
		comboFc.setNormalAttributes(comboFcAttr.setWidth((iconHeight * 7) / 8));
		fontChooser.setNormalAttributes(fontChAttr.setWidth((iconHeight * 7) / 8));
		
		comboArrow
				.setNormalAttributes(
						new ComponentAttributes(null, null, null) 
							.setWidth(iconHeight)
							.setHeight(iconHeight)
							.setIcon(new StdComboIcon(iconHeight, iconHeight)));

	}	
	
	private void defineForMac() {
		System.out.println("***** Mac *****");
		
		int iconHeight = SwingUtils.values().stdIconHeight();

		setCommonAttributes(null, null, null);
	
		fileTreeStyle.setTreeExpandedIcon(new TriangleIcon(ShapePainters.DOWN, Color.GRAY)); 
		fileTreeStyle.setTreeCollapsedIcon(new TriangleIcon(ShapePainters.RIGHT, Color.GRAY)); 
		comboFc.setNormalAttributes(comboFcAttr.setWidth((SwingUtils.values().stdIconHeight() * 7) / 8));
//		comboFc.setNormalAttributes(new ComponentAttributes(null, null, null)
//				.setWidth((SwingUtils.values().stdIconHeight() * 7) / 8));
		
		Color lightBlue      = new Color(124, 148, 255);
		
		fontChooser
				.setNormalAttributes(
						(new ComponentAttributes(Color.WHITE, lightBlue, BorderFactory.createMatteBorder(0, 1, 0, 0, NAVY)))
						//fontChAttr
							.setOpaque(true)
							.setWidth((SwingUtils.values().stdIconHeight() * 7) / 8))
//				.setContainerAttributes(
//						new ComponentAttributes(null, Color.BLUE, null)
//							.setOpaque(true))
				;
		
		comboButtonString = "*";
		
		MacComboIcon macComboIcon = new MacComboIcon(iconHeight, iconHeight);
		comboArrow
				.setNormalAttributes(
						new ComponentAttributes(Color.WHITE, null, null) 
							.setIcon(macComboIcon));
		comboArrow2 = new StandardStyle("ComboArrow2")
				.setNormalAttributes(
						new ComponentAttributes(Color.WHITE, null, null) 
							.setWidth(iconHeight)
							.setHeight(iconHeight)
							.setIcon(macComboIcon));

	}
	
	
	
	private void defineForWindows() {
		System.out.println("***** Windows *****");
		
		int iconHeight = SwingUtils.values().stdIconHeight();

		setCommonAttributes(Boolean.FALSE, stdField(), null);
		//"ComboBox.editorBorder"
		
		//UIManager.getBorder("TitledBorder.border")
		fileTreeStyle.setTreeExpandedIcon(new TriangleIcon(ShapePainters.D120, Color.GRAY)); 
		fileTreeStyle.setTreeCollapsedIcon(new TriangleIcon(ShapePainters.RIGHT, Color.GRAY, false)); 

		comboFc.setNormalAttributes(comboFcAttr);
		fontChooser.setNormalAttributes(fontChAttr);
		
		comboArrow
				.setNormalAttributes(
						new ComponentAttributes(null, null, null) 
							.setWidth(iconHeight)
							.setHeight(iconHeight)
							.setIcon(new StdComboIcon(iconHeight, iconHeight)));

	}
	
	private ComponentAttributes stdField() {
		return new ComponentAttributes(null, Color.WHITE, new LineBorder(NAVY, 1));
	}

	private void setCommonAttributes(Boolean contentAreaFilledFC, ComponentAttributes comboLikePnlAttr, Boolean contentAreaFilled) {
		Border emptyBorder = BorderFactory.createEmptyBorder();
		LineBorder navyLineBorder = new LineBorder(NAVY, 1);
		ComponentAttributes breadCrumbMouseOver = new ComponentAttributes(
				Color.BLACK, new Color(214, 240, 255), navyLineBorder);
		
//		if (comboLikePnlAttr == null) {
//			comboLikePnlAttr = new ComponentAttributes(null, Color.WHITE, navyLineBorder);
//		}
		
		
		fileCrumbItem
					.addOptions(StyleOptions.mouseOver)
					.setNormalAttributes(new ComponentAttributes(Color.BLACK, Color.WHITE, new LineBorder(Color.WHITE, 1))
							.setOpaque(true)
							.setContentAreaFilled(contentAreaFilledFC))
					.setMouseOverAttributes(breadCrumbMouseOver); 
		fileCrumbArrow
					.addOptions(StyleOptions.mouseOver)
					.setNormalAttributes(new ComponentAttributes(Color.DARK_GRAY, Color.WHITE, new LineBorder(Color.WHITE, 1)) 
							.setOpaque(true)
							.setContentAreaFilled(contentAreaFilledFC))
	
					.setMouseOverAttributes(breadCrumbMouseOver); 
		toolbarBtnsFc 
					.setNormalAttributes(new ComponentAttributes(null, null, emptyBorder) 
							.setContentAreaFilled(contentAreaFilled)
							.setWidth(SwingUtils.values().stdIconHeight() + 4));
		comboFav
					.setNormalAttributes(new ComponentAttributes(null, Color.WHITE, emptyBorder) 
							.setContentAreaFilled(contentAreaFilled));
		basicFileField.setNormalAttributes(
				new ComponentAttributes(null, null, emptyBorder));
		breadCrumbFileField.setNormalAttributes(new ComponentAttributes(null, Color.WHITE, emptyBorder));
		panelFieldStyle.setContainerAttributes( comboLikePnlAttr);
		panelAndFieldStyle
				.setContainerAttributes(comboLikePnlAttr)
				.setNormalAttributes(new ComponentAttributes(null, null, emptyBorder));

//		comboArrow.setNormalAttributes(new ComponentAttributes(
//				null, null, null, null, contentAreaFilled));
//		comboFc.setNormalAttributes(new ComponentAttributes(
//				null, null, null, null, contentAreaFilled));
		
		
		fcFileTableStyle
			.setNormalAttributes(new ComponentAttributes(null, Color.WHITE, null).setOpaque(false))
			.setMinimumTblRowHeight(SwingUtils.values().TABLE_ROW_HEIGHT + 4)
			.setTableAutoResize(JTable.AUTO_RESIZE_OFF)
			.setShowGrid(false, false);
	}

	
//	public static class MouseOver extends MouseAdapter {
//		final IBmComponent[] components;
//		public MouseOver(IBmComponent... components) {
//			
//			this.components = components;
//			for (IBmComponent c : components) {
//				c.getJComponent().addMouseListener(this);
//			}
//		}
//
//		/* (non-Javadoc)
//		 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
//		 */
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			for (IBmComponent c : components) {
//				c.getStyleId().apply(StyleAction.mouseOver, c);
//			}
//		}
//
//		/* (non-Javadoc)
//		 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
//		 */
//		@Override
//		public void mouseExited(MouseEvent e) {
//			for (IBmComponent c : components) {
//				c.getStyleId().apply(StyleAction.mouseExit, c);
//			}
//		}
//	}

}
 