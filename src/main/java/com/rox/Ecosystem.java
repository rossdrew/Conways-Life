package com.rox;

import java.util.Arrays;

/**
 * @author Ross W. Drew
 */
public class Ecosystem {
    private final boolean[][] populated;
    private final int[][] neighbours;

    private Ecosystem(final boolean[][] populated) {
        this.populated = populated;
        neighbours = takeCensus(populated);
    }

    private int[][] takeCensus(boolean[][] population){
        final int[][] neighbours = new int[population.length][population[0].length];

        for (int x=0; x<getLength(); x++){
            for (int y=0; y<getHeight(); y++){
                if (population[x][y]) {
                    //For each neighbour
                    for (int xOffset = -1; xOffset <= 1; xOffset++) {
                        int newX = x + xOffset;
                        for (int yOffset = -1; yOffset <= 1; yOffset++) {
                            int newY = y + yOffset;
                            if (withinRange(neighbours, newX, newY) && !(newX==x && newY==y)) {
                                neighbours[newX][newY]++;
                            }
                        }
                    }
                }
            }
        }

        return neighbours;
    }

    private boolean withinRange(int[][] neighbours, int x, int y) {
        return (x >= 0 && x < neighbours.length) && (y >= 0 && y < neighbours[0].length);
    }

    public static Ecosystem genesis(final boolean[][] populated){
        return new Ecosystem(populated);
    }

    public static Ecosystem evolution(final Ecosystem ecosystem){
        final boolean[][] newEcosystem = new boolean[ecosystem.getHeight()][ecosystem.getLength()];

        for (int x = 0; x < ecosystem.getLength(); x++){
            for (int y = 0; y< ecosystem.getHeight(); y++){
                if (ecosystem.getNeighbours(x, y) == 3)
                    newEcosystem[x][y] = true;

                else if (ecosystem.getNeighbours(x, y) == 2 && ecosystem.isPopulated(x,y))
                    newEcosystem[x][y]=true;

                else
                    newEcosystem[x][y]=false;
            }
        }

        return new Ecosystem(newEcosystem);
    }

    public int getHeight(){
        return populated.length;
    }

    public int getLength(){
        return populated[0].length;
    }

    public int getNeighbours(final int x,
                             final int y){
        return neighbours[x][y];
    }

    public boolean isPopulated(final int x,
                               final int y){
        return populated[x][y];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ecosystem ecosystem = (Ecosystem) o;

        if (this.getLength() != ecosystem.getLength())
            return false;
        if (this.getHeight() != ecosystem.getHeight())
            return false;

        for (int x=0; x<this.getLength(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                if (this.isPopulated(x, y) && !ecosystem.isPopulated(x, y) || !this.isPopulated(x, y) && ecosystem.isPopulated(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(populated);
        result = 31 * result;
        return result;
    }
}
