Sudoku-Solver
=============

Solves sudoku puzzles. Input values in the correct grids, press solve, and tada.

To fix whitespacing, set your editor's tab width to 3.

## Setup ##

On Linux, you can execute the following commands in your home folder to get everything set up and run it:
```
mkdir SudokuSolver
cd SudokuSolver/
mkdir bin
git clone https://github.com/kjarosz/Sudoku-Solver.git src
javac -d bin -sourcepath src src/sudokusolver/*.java
java -cp bin sudokusolver.SudokuSolver
```

Alternatively, create a new Java project in Eclipse and set up a `sudokusolver` package to import all of the .java files into, like shown below:

![Setup screenshot in Eclipse](http://i.imgur.com/GqmOBnU.png)

Then just hit the Run button to run.
