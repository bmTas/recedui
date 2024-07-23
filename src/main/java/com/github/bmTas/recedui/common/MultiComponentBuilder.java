package com.github.bmTas.recedui.common;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JComponent;

import com.github.bmTas.recedui.form.FormHelper;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;


public class MultiComponentBuilder {
	private final ArrayList<JComponent> items = new ArrayList<JComponent>(4);
	
	private final JComponent parent;

	public MultiComponentBuilder(JComponent parent) {
		super();
		this.parent = parent;
	}

	public MultiComponentBuilder(JComponent parent, JComponent mainComponent) {
		super();
		this.parent = parent;
		items.add(mainComponent);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(JComponent e) {
		return items.add(e);
	}
	
	public void build() {
			       
        switch (items.size()) {
        case 1: 
        	parent.setLayout(new BorderLayout());
        	parent.add ( items.get(0), BorderLayout.CENTER ); 
        	break;
        case 2: 
           	parent.setLayout(new BorderLayout());
        	parent.add ( items.get(0), BorderLayout.CENTER );   
        	parent.add ( items.get(1), BorderLayout.LINE_END );   
        	break;
        default:
        	double[][] gridSize = new double[][] {{1},  {1}};
        	double[] columns = new double[items.size()];
        	double[] rows = {FormHelper.FILL};
   	    
        	TableLayout tLayout = new TableLayout(gridSize);
        	parent.setLayout(tLayout);
        	
        	//columns[0] = 10;
        	columns[0] = TableLayout.FILL;
        	parent.add(items.get(0), new TableLayoutConstraints(0, 0, 0, 0, TableLayout.FULL, TableLayout.CENTER));
	        for (int i = 1; i < items.size(); i++) {
	           	//columns[i * 2] = 10;
	        	columns[i ] = TableLayout.PREFERRED;
	        	parent.add(items.get(i), new TableLayoutConstraints(i, 0, i, 0, TableLayout.FULL, TableLayout.CENTER));
	        }
	        tLayout.setColumn(columns);
	        tLayout.setRow(rows);
	        
//	        parent.addComponentListener(new Reseizer(parent, items));
			
			 //            TableLayout tblLayout = new TableLayout();
//            double[] columns = new double[items.size()];
//            double[] rows = {TableLayout.PREFERRED};
//            
//            columns[0] = TableLayout.FILL;
//            for (int i = 1; i < columns.length; i++) {
//            	columns[i] = TableLayout.PREFERRED;
//            }
//           
//            tblLayout.setColumn(columns);
//            tblLayout.setRow(rows);
//
//            parent.setLayout(tblLayout);
//        	 
//            for (int i = 0; i < items.size(); i++) {
//            	parent.add(items.get(i), new TableLayoutConstraints(i, 0));
//            }
       }
        
	}
	
//	public static class Reseizer extends ComponentAdapter {
//		final ArrayList<JComponent> items;
//		final JComponent parent;
//		Reseizer(JComponent parent, ArrayList<JComponent> items) {
//			this.items = items;
//			this.parent = parent;
//		}
//		
//		@Override
//		public void componentResized(ComponentEvent e) {
//			
//			int height = parent.getHeight();
//			for (JComponent itm : items) {
//				itm.setSize(new Dimension(itm.getWidth(), height));
//			}
//			parent.revalidate();
//			parent.repaint();
//		}
//		
//		
//	}
}
