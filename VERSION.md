 # 1.0.0
 - All basic functionality. Grid can be setup with random life through the constructor then their locations queried via `isOccupied()` or set via `setLocation()` then moved on to the next evolution with a call to `nextGeneration()` which will use the neighbour count to generate a temporary grid and then overwrite the current on with it.
 - Very basic, allows display of bug or X for life and neighbour display count.
 - UI: start play, stop play, increment step, display neighbour count/life positions, click to place/remove life and right click to place glider structure
