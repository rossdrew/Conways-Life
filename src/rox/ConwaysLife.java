package rox;

import java.util.Random;


/**
 *  An Implementation of John Conways Game of Life
 *
 *  An object implementation of John Conways 'Game of Life'
 *  keeping track of life positions and neighbour counts for
 *  use on the next iteration which is calculated on command
 *  and each position can be queried for life or neighbour count.
 *
 *  @author Ross W. Drew
 */
public class ConwaysLife
{
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
	}

	public int getLength() 	 {return m_length;}							  //getLength()
	public int getWidth() 	 {return m_width;}	 						  //getWidth()
	public boolean isOccupied(int x, int y) {return m_myEcosystem[x][y];} //isOccupied()
	public int getNeighbours (int x, int y) {return m_neighbourCnt[x][y];}//getNeighbours()

	public void nextGeneration(){
		for (int x=0; x<m_length; x++){
			for (int y=0; y<m_width; y++){
				if (m_neighbourCnt[x][y]==3)
					m_nextGeneration[x][y]=true;
				
				else if (m_neighbourCnt[x][y]==2 && m_myEcosystem[x][y])
					m_nextGeneration[x][y]=true;
				
				else
					m_nextGeneration[x][y]=false;
			}
		}

		clearEcosystem();

		for (int x=0; x<m_length; x++){
			for (int y=0; y<m_width; y++){
				setLocation(x,y, m_nextGeneration[x][y]);
			}
		}

	}

	private void informOfChange(int atX, int atY, boolean isAdd){
		if (!validLocation(atX,atY)) return;/*ERROR TRAP*/
		
		if (isAdd && m_neighbourCnt[atX][atY] < 8)
			m_neighbourCnt[atX][atY]++;
		else if (!isAdd && m_neighbourCnt[atX][atY] > 0) 
			m_neighbourCnt[atX][atY]--;
	}

	public void setLocation(int atX, int atY, boolean value){
		if (validLocation(atX,atY))					/*ERROR TRAP: Valid Location*/
			if (m_myEcosystem[atX][atY]!=value)		/*ERROR TRAP: No change required*/
				m_myEcosystem[atX][atY] =value;
			else
				return;
		else 
			return;

		//Inform neighbours
		for (int x=(-1); x<=1; x++){
			for (int y=(-1); y<=1; y++){
				if (x!=0 || y!=0){
					informOfChange(atX+x,atY+y,value);
				}
			}
		}
	}

	private void randomizeEcosystem(){
		Random oGenerator = new Random();
		
		for (int o=0; o<90; o++){
			setLocation(oGenerator.nextInt(m_length),oGenerator.nextInt(m_width),true);
		}
	}

	private void clearEcosystem(){
		for (int x=0; x<m_length; x++){
			for (int y=0; y<m_length; y++){
				setLocation(x, y, false);
		}	}
	}

	private boolean validLocation(int x, int y){
		if (x<m_length && x>=0 && 
		    y<m_width  && y>=0)
			return true;
		else
			return false;
	}
}
