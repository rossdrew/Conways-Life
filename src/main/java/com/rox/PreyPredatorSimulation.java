package com.rox;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class PreyPredatorSimulation {
    /**
     * An entity which ages
     */
    private interface TemporalEntity {
        void incrementAge();
        boolean pastLifeSpan();
    }

    private class Shark implements TemporalEntity {
        private int age = 1;
        private int lifeSpan = 20;
        private int breedingAge = 3;

        public boolean ofBreedingAge(){
            return age >= 3;
        }

        public void incrementAge() {
            age++;
        }

        @Override
        public boolean pastLifeSpan() {
            return age>lifeSpan;
        }

        @Override
        public String toString() {
            return "S";
        }
    }

    private class Fish implements TemporalEntity  {
        private int age = 1;
        private int lifeSpan = 10;
        private int breedingAge = 2;

        public boolean ofBreedingAge(){
            return age >= 2;
        }

        public void incrementAge() {
            age++;
        }

        @Override
        public String toString() {
            return "F";
        }

        @Override
        public boolean pastLifeSpan() {
            return age>lifeSpan;
        }
    }

    private class Sea implements TemporalEntity  {
        public void incrementAge() { /* NOP */ }

        @Override
        public String toString() {
            return "~";
        }

        @Override
        public boolean pastLifeSpan() {
            return false;
        }
    }

    private final int biomeLength;
    private final int biomeBreadth;
    private final TemporalEntity[][] biome;
    private final Random rand = new Random();

    public PreyPredatorSimulation(int length, int breadth){
        this.biomeLength = length;
        this.biomeBreadth = breadth;

        int biomeLocales = length * breadth;
        final int fishCount = biomeLocales / 2;
        final int sharkCount = biomeLocales / 4;

        System.out.println(length + "x" + breadth + " (" + biomeLocales + ") " + "> Fishes: " + sharkCount + " Sharks:" + sharkCount);
        biome = generateBiome(length, breadth, fishCount, sharkCount);
        drawBiome();
    }

    /**
     * Draw the current biome to console
     */
    public void drawBiome() {
        //Draw sea
        for (int x=0; x<biomeLength; x++){
            for (int y=0; y<biomeBreadth; y++){
                System.out.print(biome[x][y] + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Run a life cycle of deaths, aging and reproductions
     */
    public void cycle(){
        for (int x=0; x<biomeLength; x++){
            for (int y=0; y<biomeBreadth; y++){
                final Set<TemporalEntity> neighbours = listNeighbours(x, y);
                final List<Shark> sharkNeighbours = neighbours.stream()
                        .filter(it -> it instanceof Shark)
                        .map(it -> (Shark) it)
                        .collect(Collectors.toList());
                final List<Fish> fishNeighbours = neighbours.stream()
                        .filter(it -> it instanceof Fish)
                        .map(it -> (Fish) it)
                        .collect(Collectors.toList());

                birth(x, y, sharkNeighbours, fishNeighbours);
                ageDeaths(x, y);
                fishDeaths(x, y, sharkNeighbours, fishNeighbours);
                sharkDeaths(x, y, sharkNeighbours, fishNeighbours);

                biome[x][y].incrementAge();
            }
        }
    }

    /**
     * Death if
     *  - >5 sharks || fish==8 -> DIE
     *  - 1/32 roll comes up 1
     */
    public void sharkDeaths(int x, int y, List<Shark> sharks, List<Fish> fishes) {
        if (biome[x][y] instanceof Shark) {
            if (sharks.size() > 5 && fishes.size() == 0){
                biome[x][y] = new Sea();
            }

            if (rand.nextInt((35 - 1) + 1) + 1 == 1){
                biome[x][y] = new Sea();
            }
        }
    }

    /**
     * Death if >5 sharks || fish==8
     */
    public void fishDeaths(int x, int y, List<Shark> sharks, List<Fish> fishes) {
        if (biome[x][y] instanceof Fish && (sharks.size() > 5 || fishes.size() == 8)) {
            biome[x][y] = new Sea();
        }
    }

    /**
     * Death if passed lifeSpan
     */
    public void ageDeaths(int x, int y) {
        if (!(biome[x][y] instanceof Sea) && biome[x][y].pastLifeSpan()){
            biome[x][y] = new Sea();
        }
    }

    /**
     * Create life if >= 4 nearby && >= 3 of them are of breeding age && < other species
     */
    public void birth(int x, int y, List<Shark> sharks, List<Fish> fishes) {
        if (biome[x][y] instanceof Sea) {
            if (fishes.size() > 3 && fishes.stream().filter(fish -> fish.ofBreedingAge()).count() > 2) {
                biome[x][y] = new Fish();
            }
            //These two will clash if rules aren't mathematically compatible...and I'm not trying to prove it
            if (sharks.size() > 3 && sharks.stream().filter(shark -> shark.ofBreedingAge()).count() > 2) {
                biome[x][y] = new Shark();
            }
        }
    }

    /**
     * Create a {@link Set} of neighbouring {@link TemporalEntity}s from adjacent tiles
     */
    private Set<TemporalEntity> listNeighbours(int x, int y){
        final Set<TemporalEntity> neighbours = new HashSet<TemporalEntity>();
        boolean leftEdge = x==0;
        boolean rightEdge = x==biomeLength-1;
        boolean topEdge = y==0;
        boolean bottomEdge = y==biomeLength-1;

        if (!leftEdge) {
            neighbours.add(biome[x-1][y]); //W
            if (!topEdge)
                neighbours.add(biome[x-1][y-1]); //NW
            if (!bottomEdge)
                neighbours.add(biome[x-1][y+1]); //SW
        }
        if (!rightEdge) {
            neighbours.add(biome[x+1][y]); //E
            if (!topEdge)
                neighbours.add(biome[x+1][y-1]); //NE
            if (!bottomEdge)
                neighbours.add(biome[x+1][y+1]); //SE
        }
        if (!topEdge)
            neighbours.add(biome[x][y-1]); //N
        if (!bottomEdge)
            neighbours.add(biome[x][y+1]); //S

        return neighbours;
    }

    /**
     * Randomly generate a two dimensional biome with the required number of fish and sharks
     */
    private TemporalEntity[][] generateBiome(int length, int breadth, int fishCount, int sharkCount) {
        final TemporalEntity[][] newBiome = new TemporalEntity[length][breadth];

        final Stack<Point> points = new Stack<>();
        for (int x : shuffle(length)){
            for (int y : shuffle(breadth)){
                points.add(new Point(x,y));
            }
        }
        Collections.shuffle(points);

        while(fishCount-- > 0){
            final Point p = points.pop();
            newBiome[p.x][p.y] = new Fish();
        }

        while(sharkCount-- > 0){
            final Point p = points.pop();
            newBiome[p.x][p.y] = new Shark();
        }

        points.forEach( (p) -> newBiome[p.x][p.y] = new Sea());

        return newBiome;
    }

    /**
     * Given a {@code size}, generate a list of randomly shuffled numbers from {@code 0}-{@code size}
     */
    private Stack<Integer> shuffle(int size){
        final ArrayList<Integer> listOfIndexes = new ArrayList<Integer>();
        for (int i=0; i<size; i++){
            listOfIndexes.add(i);
        }
        Collections.shuffle(listOfIndexes);
        final Stack<Integer> shuffledStack = new Stack<>();
        shuffledStack.addAll(listOfIndexes);
        return shuffledStack;
    }

    public static void main(String[] args){
        final PreyPredatorSimulation sim = new PreyPredatorSimulation(10, 10);
        sim.cycle();
        System.out.println("...STEP");
        sim.drawBiome();
    }
}
