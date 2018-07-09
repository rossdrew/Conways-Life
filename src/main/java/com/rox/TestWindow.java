package com.rox;

import java.awt.BorderLayout;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 *  This is the UI for conwaysLife() Implementing a JPanel called lifeGrid()
 */
 public class TestWindow extends JFrame implements ActionListener, MouseListener
{
	public static TestWindow winInst;
	
	private ConwaysLife myLife;
	
	private LifeGrid myGrid;
	private JPanel  pCtrl;
	private JButton bPlay,bStep,bPop;
	private boolean isPlaying, popDisplayed;
	private Timer evoTimer;
	private ImageIcon icSkip,icPlay,icPaws,icLoc,icSum;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem helpAbout = new JMenuItem("About");
	
	private static String aboutInformation = "<HTML><BODY><small>John Conways</small><br> 'Game of Life' v1.0.0 <BR><BR>by Ross W. Drew <BR><TT>thek1ng13@hotmail.com</TT>";

	public TestWindow(int initLength, int initWidth){
		super("\"John Conways Game of Life\" by Ross Drew");
		
		helpAbout.addActionListener(this);
		 helpMenu.add(helpAbout);
		  menuBar.add(helpMenu);
		   setJMenuBar(menuBar);
		
		myLife = new ConwaysLife(initLength,initWidth);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setLayout(new BorderLayout());
		
		initControlBox();
		myGrid = new LifeGrid(myLife);
		  add(myGrid,BorderLayout.CENTER);
		  myGrid.addMouseListener(this);
		 
		pack();
	}

	private void initControlBox(){
		pCtrl = new JPanel();
				
		icSkip = new ImageIcon("img/skip.png","Skip");
		icPlay = new ImageIcon("img/play.png","Play");
		icPaws = new ImageIcon("img/pause.png","Play");
		icLoc  = new ImageIcon("img/loc.png","Play");
		icSum  = new ImageIcon("img/sum.png","Play");
		
		bPlay = new JButton(icPlay);
		 isPlaying=false;
		 bPlay.addActionListener(this);
		 bPlay.setFocusable(false);
		bStep = new JButton(icSkip);
		 bStep.addActionListener(this);
		 bStep.setFocusable(false);
		bPop  = new JButton(icSum);
		 bPop.addActionListener(this);
		 bPop.setFocusable(false);
		
	    evoTimer = new Timer(250, this);
	     evoTimer.addActionListener(this);
		
		pCtrl.add(bPlay);
		pCtrl.add(bStep);
		pCtrl.add(bPop);
		
		add(pCtrl,BorderLayout.SOUTH);			//Add to form
	}

	public void actionPerformed(ActionEvent evnt){
		Object src = evnt.getSource();
		
	    /*STEP*/
		if (src==bStep || src==evoTimer){
			myLife.nextGeneration();
		}
		
        /*PLAY*/
		else if (src==bPlay){
			if (isPlaying){
				evoTimer.stop();
				bPlay.setIcon(icPlay);
			}else{
				evoTimer.start();
				bPlay.setIcon(icPaws);
			}
			isPlaying=!isPlaying;
		}
		
		/*POP/LOC*/
		else if (src==bPop){
			if (popDisplayed){
				bPop.setIcon(icSum);
			}else{
				bPop.setIcon(icLoc);
			}
			popDisplayed=!popDisplayed;
			myGrid.popDisplayed(popDisplayed);
		}
		
        /* ABOUT MENU */
		else if(src==helpAbout){
		   JOptionPane.showMessageDialog(this, aboutInformation);
		}
		
		invalidate();
		repaint();
	}
	
	public void mousePressed(MouseEvent e)  {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) 	{}
	public void mouseExited(MouseEvent e) 	{}
	public void mouseClicked(MouseEvent e) 	
	{
		int newX,newY;
		
		newX = (e.getX()/16)-1; 
		newY = (e.getY()/16)-1;
		
		if (e.getButton()==e.BUTTON1){
			//this.setTitle("Loc: "+e.getX()+","+e.getY());
			myLife.setLocation(newX,newY,!myLife.isOccupied(newX,newY));
		}
		else if (e.getButton()==e.BUTTON3 || e.getButton()==e.BUTTON2){
		//Glider
			myLife.setLocation(newX-1,newY-1,false);
			myLife.setLocation(newX-1,newY,false);
			myLife.setLocation(newX-1,newY+1,true);
			
			myLife.setLocation(newX,newY-1,true);
			myLife.setLocation(newX,newY,false);
			myLife.setLocation(newX,newY+1,true);
			
			myLife.setLocation(newX+1,newY-1,false);
			myLife.setLocation(newX+1,newY,true);
			myLife.setLocation(newX+1,newY+1,true);
			
		}
		paintAll(getGraphics());
	}

    /* P R O G R A M      E N T R Y       P O I N T */
	public static void main(String[] args){
		winInst = new TestWindow(20,20);
		winInst.setVisible(true);
	}
}
