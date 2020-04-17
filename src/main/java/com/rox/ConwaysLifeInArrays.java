package com.rox;

import java.util.Random;


/**
 *  <h3>John Conways Game of Life</h3>
 *
 *  An array absed implementation of John Conways '<i>Game of Life</i>'
 *  using a {@link PopulationCentricEcosystem}
 */
public class ConwaysLifeInArrays implements PopulationCentricEcosystem {
	private int ecosystemLength, ecosystemWidth;
	private boolean[][] currentEcosystem, futureEcosystem;
	private int[][] population;
	
	public ConwaysLifeInArrays(int maxX, int maxY){
		ecosystemLength = maxX;
		ecosystemWidth  = maxY;
		
		currentEcosystem    = new boolean[ecosystemLength][ecosystemWidth];
		futureEcosystem     = new boolean[ecosystemLength][ecosystemWidth];
		population          = new int	 [ecosystemLength][ecosystemWidth];
		
		clearEcosystem();
		randomizeEcosystem();
	}

	public int getLength() 	 {return ecosystemLength;}
	public int getWidth() 	 {return ecosystemWidth;}
	public boolean isOccupied(int x, int y) {return currentEcosystem[x][y];}
	public int getNeighbours (int x, int y) {return population[x][y];}

	public void nextGeneration(){
		for (int x = 0; x < ecosystemLength; x++){
			for (int y = 0; y < ecosystemWidth; y++){
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
