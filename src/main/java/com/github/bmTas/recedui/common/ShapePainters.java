package com.github.bmTas.recedui.common;

import java.awt.Graphics;

public class ShapePainters {
	public static final int D0   = 0;
	public static final int D30  = 1;
	public static final int D90  = 2;
	public static final int D120 = 3;
	public static final int D180 = 4;
	public static final int D210 = 5;
	public static final int UP = D0;
	public static final int DOWN = D180;
	public static final int LEFT = 6;
	public static final int RIGHT = D90;

	

    public static void paintTriangle(Graphics g, int direction, int x, int y, int w, int h) {
    	int[][] points = calcPoints(direction, x, y, w, h);
		g.fillPolygon(points[0], points[1], 3);
    }
    
    public static void drawTriangle(Graphics g, int direction, int x, int y, int w, int h) {
       	int[][] points = calcPoints(direction, x, y, w, h);
   		g.drawPolygon(points[0], points[1], 3);
    }
    
    public static void paintMacCombo(Graphics g, int x, int y, int w, int h) {
    	
    	int topSpacing = h/5;
    	int sideSpacing =  w / 3;
    	 
    	int sep = h/4; //odd ? 2 : 2;
    	int height = h/2 -  topSpacing - sep/2;
    	int width = w - 2 * sideSpacing;
    	//boolean odd = (h & 1) > 0;


      	
      	//paintTriangle(g, UP, x, y, w, height);
      	int xStart = x+sideSpacing;
		int xMid = xStart+width/2;
      	int xEnd = xStart+width;
 
		int yEnd = y+topSpacing;
     	int yStart = yEnd+height ;
		int yBottomStart = yStart+sep;

//      	System.out.println("\t" + x + "\t" + y + " :\t"+ w + "\t" + h 
//      			+ "\t"+ height + " " + width + " "+ g.getColor());
//      	System.out.println("\t" + xStart + "\t" + xEnd + " " + xMid);
//      	System.out.println("\t" + yStart + "\t" + (yStart+ height) );
//      	System.out.println("\t" + yBottomStart + "\t" + (yBottomStart+ height) );

		g.drawLine(xStart, yStart, xMid, yEnd);
		g.drawLine(xMid, yEnd, xEnd, yStart);
		
		g.drawLine(xStart+1, yStart, xMid, yEnd+1);
		g.drawLine(xMid, yEnd+1, xEnd-1, yStart);
		
      	g.drawLine(xStart, yBottomStart, xMid, yBottomStart+height);
      	g.drawLine(xMid, yBottomStart+height, xEnd, yBottomStart);
      	
      	g.drawLine(xStart+1, yBottomStart, xMid, yBottomStart+height-1);
      	g.drawLine(xMid, yBottomStart+height-1, xEnd-1, yBottomStart);
     }
   
    private static int[][] calcPoints(int direction, int x, int y, int w, int h) {
    	int size = (Math.min(w, h) + 1) / 2;
		int wInsert = x + (w - size ) / 2;
		int hInsert = y + (h - size ) / 2;
		
    	int arrowHeight = size;//(int) Math.sqrt(size * size * 5 / 4);

    	int[] xPoints = {wInsert, wInsert + size, x + (w)/ 2};
		int[] yPoints = {hInsert, hInsert,        hInsert + arrowHeight};
		switch (direction) {
		case UP:
			yPoints[0] = hInsert + arrowHeight;
			yPoints[1] = hInsert + arrowHeight;
			yPoints[2] = hInsert;
			break;
		case RIGHT:
			yPoints[0] = hInsert ; 
			yPoints[1] = hInsert + size;	xPoints[1] = wInsert ;
			yPoints[2] = y + h / 2;			xPoints[2] = wInsert + 	arrowHeight;
			break;
		case LEFT:
			yPoints[0] = hInsert ; 			xPoints[0] = wInsert + 	arrowHeight;
			yPoints[1] = hInsert + size;	xPoints[1] = wInsert + 	arrowHeight;
			yPoints[2] = y + h / 2;			xPoints[2] = wInsert ;
			break;
		case D30:
			yPoints[0] = hInsert ; 			xPoints[0] = wInsert ;
			yPoints[1] = hInsert;			xPoints[1] = wInsert + size;
			yPoints[2] = hInsert + size;	xPoints[2] = wInsert + size ;
			break;
		case D120:
			yPoints[0] = hInsert; 			xPoints[0] = wInsert  + size;
			yPoints[1] = hInsert + size;	xPoints[1] = wInsert;
			yPoints[2] = hInsert + size;	xPoints[2] = wInsert + size ;
			break;
		case D210:
			yPoints[0] = hInsert ; 			xPoints[0] = wInsert ;
			yPoints[1] = hInsert + size;	xPoints[1] = wInsert ;
			yPoints[2] = hInsert + size;	xPoints[2] = wInsert + size ;
			break;
		}
		int[][] ret = {xPoints, yPoints};
		
		return ret;
    }

}
