package com.rox;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ross W. Drew
 */
public class EcosystemTest {
    private boolean[][] population;
    private Ecosystem ecosystem;

    @Before
    public void setup(){
        population = new boolean[3][3];
        ecosystem = Ecosystem.genesis(population);
    }

    @Test
    public void testGenesis(){
        population = new boolean[3][3];
        ecosystem = Ecosystem.genesis(population);

        assertNotNull(ecosystem);
        assertEquals(3, ecosystem.getHeight());
        assertEquals(3, ecosystem.getLength());
        assertFalse(ecosystem.isPopulated(1,2));
        assertEquals(0, ecosystem.getNeighbours(1,1));
    }

    @Test
    public void testAridity() {
        population = new boolean[3][3];
        ecosystem = Ecosystem.genesis(population);

        final Ecosystem evolvedEcosystem = Ecosystem.evolution(ecosystem);

        assertNotNull(evolvedEcosystem);
        assertEquals(3, evolvedEcosystem.getHeight());
        assertEquals(3, evolvedEcosystem.getLength());

        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++) {
                assertFalse(evolvedEcosystem.isPopulated(x, y));
                assertEquals(0, evolvedEcosystem.getNeighbours(x, y));
            }
        }
    }

    /**
     *  - - -    - - -
     *  - + - -> - - -
     *  - - -    - - -
     */
    @Test
    public void testLonelyDeath() {
        population = new boolean[3][3];
        population[1][1] = true;
        ecosystem = Ecosystem.genesis(population);

        final Ecosystem evolvedEcosystem = Ecosystem.evolution(ecosystem);

        assertNotNull(evolvedEcosystem);
        assertEquals(3, evolvedEcosystem.getHeight());
        assertEquals(3, evolvedEcosystem.getLength());

        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++) {
                assertFalse(evolvedEcosystem.isPopulated(x, y));
                assertEquals(0, evolvedEcosystem.getNeighbours(x, y));
            }
        }
    }

    /**
     *  - - -    - - -
     *  - + + -> - - -
     *  - - -    - - -
     */
    @Test
    public void testDeath() {
        population = new boolean[3][3];
        population[1][1] = true;
        population[1][2] = true;
        ecosystem = Ecosystem.genesis(population);

        final Ecosystem evolvedEcosystem = Ecosystem.evolution(ecosystem);

        assertNotNull(evolvedEcosystem);
        assertEquals(3, evolvedEcosystem.getHeight());
        assertEquals(3, evolvedEcosystem.getLength());

        assertNotEquals(ecosystem, evolvedEcosystem);

        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++) {
                assertFalse(evolvedEcosystem.isPopulated(x, y));
                assertEquals(0, evolvedEcosystem.getNeighbours(x, y));
            }
        }
    }

    /**
     *  - + +    - + +
     *  - + + -> - + +
     *  - - -    - - -
     */
    @Test
    public void testStability() {
        population = new boolean[3][3];
        population[1][1] = true;
        population[1][2] = true;
        population[0][1] = true;
        population[0][2] = true;
        ecosystem = Ecosystem.genesis(population);

        final Ecosystem evolvedEcosystem = Ecosystem.evolution(ecosystem);

        assertNotNull(evolvedEcosystem);
        assertEquals(3, evolvedEcosystem.getHeight());
        assertEquals(3, evolvedEcosystem.getLength());

        assertEquals(ecosystem, evolvedEcosystem);
    }

    /**
     *  - + -    - + +
     *  - + + -> - + +
     *  - - -    - - -
     */
    @Test
    public void testBirth() {
        population = new boolean[3][3];
        population[1][1] = true;
        population[1][2] = true;
        population[0][1] = true;
        ecosystem = Ecosystem.genesis(population);

        final Ecosystem evolvedEcosystem = Ecosystem.evolution(ecosystem);

        assertNotNull(evolvedEcosystem);
        assertEquals(3, evolvedEcosystem.getHeight());
        assertEquals(3, evolvedEcosystem.getLength());

        assertTrue(evolvedEcosystem.isPopulated(0, 1));
        assertEquals(Ecosystem.genesis(new boolean[][]{{false, true, true},
                                                       {false, true, true},
                                                       {false, false, false}}), evolvedEcosystem);
    }
}
