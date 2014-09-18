package sudokusolver;

public class SudokuSolver {
	private static final int[][] startingGrid = 
		{
		{ 0, 0, 0, 3, 0, 0, 0, 4, 0},
		{ 4, 0, 0, 0, 1, 0, 3, 5, 0},
		{ 3, 9, 5, 0, 0, 4, 0, 6, 0},
		{ 5, 6, 3, 0, 0, 1, 4, 0, 8},
		{ 0, 4, 7, 0, 0, 0, 5, 0, 0},
		{ 2, 8, 0, 4, 0, 0, 6, 0, 3},
		{ 0, 5, 0, 2, 0, 0, 1, 8, 4},
		{ 0, 3, 4, 1, 8, 0, 0, 0, 5},
		{ 8, 1, 2, 5, 4, 9, 7, 3, 6}
		};
	
	public static void main(String[] args) {
		Grid grid = new Grid(startingGrid);
		OptionsGrid options = new OptionsGrid(grid);
		options.findAllValidOptions();
		System.out.println("Done.");
		
	}

}
