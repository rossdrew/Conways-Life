package rox;

import java.util.Random;


/*
 *    An Implementation of 
 *  John Conways Game of Life
 *   	 Ross W. Drew
 *    thek1ng13@hotmail.com
 */

public class ConwaysLife
{
	/**
	 *  An object implementation of John Conways 'Game of Life'
	 * keeping track of life positions and neighbour counts for 
	 * use on the next iteration which is calculated on command 
	 * and each position can be queried for life or neighbour count.
	 * 
	 * v1.
	 *  All basic functionality. Grid can be setup with random 
	 * life through the constructor then their locations queried 
	 * via isOccupied() or set via setLocation() then moved on to 
	 * the next evolution with a call to nextGeneration() which 
	 * will use the neighbour count to generate a temporary grid 
	 * and then overwrite the current on with it. 
	 */
	
	private int 		m_length, m_width;
	private boolean[][] m_myEcosystem, m_nextGeneration;
	private int[][] 	m_neighbourCnt;	
	
	public ConwaysLife(int maxX, int maxY)
	{
		m_length = maxX;
		m_width	 = maxY;
		
		m_myEcosystem  		= new boolean[m_length][m_width];
		m_nextGeneration  	= new boolean[m_length][m_width];
		m_neighbourCnt 		= new int	 [m_length][m_width];
		
		clearEcosystem();
		randomizeEcosystem();
	}//conwaysLife()
	
//Queries
	public int getLength() 	 {return m_length;}							  //getLength()
	public int getWidth() 	 {return m_width;}	 						  //getWidth()
	public boolean isOccupied(int x, int y) {return m_myEcosystem[x][y];} //isOccupied()
	public int getNeighbours (int x, int y) {return m_neighbourCnt[x][y];}//getNeighbours()
	
//Generate next generation ecosystem
	public void nextGeneration()
	{
	//Calculate next generation
		for (int x=0; x<m_length; x++)
		{
			for (int y=0; y<m_width; y++)
			{
				if (m_neighbourCnt[x][y]==3)
					m_nextGeneration[x][y]=true;
				
				else if (m_neighbourCnt[x][y]==2 && m_myEcosystem[x][y])
					m_nextGeneration[x][y]=true;
				
				else
					m_nextGeneration[x][y]=false;
		}	}//for x+y
		
	//Clear board for next generation
		clearEcosystem();
		
	//Apply next generation to ecosystem
		for (int x=0; x<m_length; x++)
		{
			for (int y=0; y<m_width; y++)
			{
				setLocation(x,y, m_nextGeneration[x][y]);
		}	}//for x+y
	}//nextGeneration()
	
//Inform location of neighbour change
	private void informOfChange(int atX, int atY, boolean isAdd)
	{
		if (!validLocation(atX,atY)) return;/*ERROR TRAP*/
		
		if (isAdd && m_neighbourCnt[atX][atY] < 8)
			m_neighbourCnt[atX][atY]++;
		else if (!isAdd && m_neighbourCnt[atX][atY] > 0) 
			m_neighbourCnt[atX][atY]--;
	}//informOfChange()
	
//Create/Destroy lifeform at x,y, informing all neighbours
	public void setLocation(int atX, int atY, boolean value)
	{
		if (validLocation(atX,atY))					/*ERROR TRAP: Valid Location*/
			if (m_myEcosystem[atX][atY]!=value)		/*ERROR TRAP: No change required*/
				m_myEcosystem[atX][atY] =value;
			else
				return;
		else 
			return;
		
	//Inform neighbours
		for (int x=(-1); x<=1; x++)
		{
			for (int y=(-1); y<=1; y++)
			{
				if (x!=0 || y!=0)
				{
					informOfChange(atX+x,atY+y,value);
				}
		}	}//for x+y
	}//setLocation()
	
//Randomise ecosystem
	private void randomizeEcosystem()
	{
		Random oGenerator = new Random();
		
		for (int o=0; o<90; o++)
		{
			setLocation(oGenerator.nextInt(m_length),oGenerator.nextInt(m_width),true);
		}//for (int o=0; o<90; o++)
	}//randomizeEcosystem()
	
//Clear ecosystem
	private void clearEcosystem()
	{
		for (int x=0; x<m_length; x++)
		{
			for (int y=0; y<m_length; y++)
			{
				setLocation(x, y, false);
		}	}//for x+y
	}//clearEcosystem()
	
//Test if co-ordinates are valid
	private boolean validLocation(int x, int y)
	{
		if (x<m_length && x>=0 && 
		    y<m_width  && y>=0)
			return true;
		else
			return false;
	}//validLocation()
}//conwaysLife
