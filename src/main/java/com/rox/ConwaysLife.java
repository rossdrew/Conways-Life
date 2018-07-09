package com.rox;

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
	private int ecosystemLength, ecosystemWidth;
	private boolean[][] currentEcosystem, futureEcosystem;
	private int[][] population;
	
	public ConwaysLife(int maxX, int maxY)
	{
		ecosystemLength = maxX;
		ecosystemWidth  = maxY;
		
		currentEcosystem    = new boolean[ecosystemLength][ecosystemWidth];
		futureEcosystem     = new boolean[ecosystemLength][ecosystemWidth];
		population          = new int	 [ecosystemLength][ecosystemWidth];
		
		clearEcosystem();
		randomizeEcosystem();
	}

	public int getLength() 	 {return ecosystemLength;}							  //getLength()
	public int getWidth() 	 {return ecosystemWidth;}	 						  //getHeight()
	public boolean isOccupied(int x, int y) {return currentEcosystem[x][y];} //isOccupied()
	public int getNeighbours (int x, int y) {return population[x][y];}//getNeighbours()

	public void nextGeneration(){
		for (int x = 0; x< ecosystemLength; x++){
			for (int y = 0; y< ecosystemWidth; y++){
				if (population[x][y]==3)
					futureEcosystem[x][y]=true;
				
				else if (population[x][y]==2 && currentEcosystem[x][y])
					futureEcosystem[x][y]=true;
				
				else
					futureEcosystem[x][y]=false;
			}
		}

		clearEcosystem();

		for (int x = 0; x< ecosystemLength; x++){
			for (int y = 0; y< ecosystemWidth; y++){
				setLocation(x,y, futureEcosystem[x][y]);
			}
		}

	}

	private void informOfChange(int atX, int atY, boolean isAdd){
		if (!validLocation(atX,atY)) return;/*ERROR TRAP*/
		
		if (isAdd && population[atX][atY] < 8)
			population[atX][atY]++;
		else if (!isAdd && population[atX][atY] > 0)
			population[atX][atY]--;
	}

	public void setLocation(int atX, int atY, boolean value){
		if (validLocation(atX,atY))					/*ERROR TRAP: Valid Location*/
			if (currentEcosystem[atX][atY]!=value)		/*ERROR TRAP: No change required*/
				currentEcosystem[atX][atY] =value;
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
			setLocation(oGenerator.nextInt(ecosystemLength),oGenerator.nextInt(ecosystemWidth),true);
		}
	}

	private void clearEcosystem(){
		for (int x = 0; x< ecosystemLength; x++){
			for (int y = 0; y< ecosystemLength; y++){
				setLocation(x, y, false);
		}	}
	}

	private boolean validLocation(int x, int y){
		if (x< ecosystemLength && x>=0 &&
		    y< ecosystemWidth && y>=0)
			return true;
		else
			return false;
	}
}
