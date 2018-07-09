package com.rox;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A JPanel overriding the paint method
 * to display a life grid form conwaysLife()
 */
public class LifeGrid extends JPanel{
	private ConwaysLife thisLife;
	private boolean popDisplayed;
	
	private BufferedImage imgBug;
	
	public LifeGrid(ConwaysLife newLife){
		thisLife = newLife;
		popDisplayed=false;
		this.setPreferredSize(new Dimension(thisLife.getLength()*16,thisLife.getWidth()*16));
		
        imgBug = null;
		try{
		//System.out.println("Getting bug image file...");
			File imgFile = new File("img/bug.bmp");
		//System.out.println("Creating Image...");
			imgBug = ImageIO.read(imgFile);
		}catch(Exception e){System.out.println("Error loading image");}	
	}
	
	public void popDisplayed(boolean newVal) {
	    popDisplayed=newVal;
	}

	public void paint(Graphics myG){
		Graphics2D myG2 = (Graphics2D)myG;
		
		myG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 16);
        myG2.setFont(font);
        
        for (int x=0; x<thisLife.getLength(); x++){
        	for (int y=0; y<thisLife.getWidth(); y++){
        	/*Neighbours*/
        		if (popDisplayed){
   					myG2.drawString(""+thisLife.getNeighbours(x,y), ((x+1)*16), ((y+1)*16));
        		}
        		
        	/*Life positions*/
        		else{
	        		if  (thisLife.isOccupied(x,y)){
	        			if (imgBug!=null)
        					myG2.drawImage(imgBug, ((x+1)*16), ((y+1)*16), null);
        				else
        					myG2.drawString("X", ((x+1)*16), ((y+1)*16));
	        		}
        		}        		
            }
        }
	}
}
