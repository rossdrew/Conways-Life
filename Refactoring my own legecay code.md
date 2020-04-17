## Refactoring my Own Legacy Code

#### Directory layout

There was none.  It was a flat directory structure and I first separated out UI elements from logic.

#### Coupling Between Logic & UI

The main UI `TestWindow` directly used the class `ConwaysLife` so changing implementation means changing that file, 
sometimes we want to be able to version and in this case I want to keep the original implementation to look back on.

So I abstracted away the Life interface to `PopulationCentricEcosystem` and made my current `ConwaysLife` into a more 
description implementation of the interface, `ConwaysLifeInArrays`.  This means I can create new implementations and 
switch them in and out without having to touch most of the UI code, just the injection line which I could abstract away 
later.  A step towards dependency injection giving me code that's modular, as well as easier to maintain and test.
  
