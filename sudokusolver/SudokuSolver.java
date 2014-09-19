package sudokusolver;

public class SudokuSolver {
	private static final int[][] startingGrid = 
	   {
	   { 0, 0, 1, 5, 0, 0, 0, 4, 0 },
	   { 3, 0, 0, 0, 0, 0, 0, 8, 5 }, 
	   { 0, 0, 9, 0, 0, 2, 0, 0, 0 },
	   { 0, 0, 4, 2, 0, 0, 0, 0, 6 },
	   { 2, 0, 0, 8, 0, 9, 0, 0, 7 },
	   { 7, 0, 0, 0, 0, 3, 4, 0, 0 },
	   { 0, 0, 0, 7, 0, 0, 8, 0, 0 },
	   { 1, 3, 0, 0, 0, 0, 0, 0, 4 },
	   { 0, 2, 0, 0, 0, 5, 1, 0, 0 }
	   };
	
	public static void main(String[] args) {
		Grid grid = new Grid(startingGrid);
		OptionsGrid optionsGrid = new OptionsGrid(grid);
		while(optionsGrid.fillUniqueSolutions());
		grid.print();
	}
}
