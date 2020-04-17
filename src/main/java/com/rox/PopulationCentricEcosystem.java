package com.rox;

/**
 *  A 2-Dimensional ecosystem in which each space can be toggled, neighbours
 *  can be counted and generations can be stepped.
 */
public interface PopulationCentricEcosystem {
    /** Length of the ecosystem */
    int getLength();

    /** Width of the ecosystem */
    int getWidth();

    /** Number of neighbours this location has */
    int getNeighbours(int x, int y);

    /** Is this location populated */
    boolean isOccupied(int x, int y);

    /** Move the ecosytem forward one generation */
    void nextGeneration();

    /** Set the given location to populated or not */
    void setLocation(int x, int y, boolean populated);
}
