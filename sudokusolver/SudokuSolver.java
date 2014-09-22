package sudokusolver;

public class SudokuSolver {
	private static final int[][] startingGrid = 
	   {
	   { 0, 1, 0, 0, 0, 9, 4, 0, 2 },
	   { 4, 0, 0, 0, 3, 0, 9, 0, 0 },
	   { 0, 9, 7, 1, 0, 0, 0, 5, 0 },
	   { 0, 6, 1, 8, 0, 0, 0, 0, 0 },
	   { 0, 0, 0, 0, 7, 0, 0, 0, 0 },
	   { 0, 0, 0, 0, 0, 4, 1, 2, 0 },
	   { 0, 5, 0, 0, 0, 3, 6, 4, 0 },
	   { 0, 0, 4, 0, 1, 0, 0, 0, 5 },
	   { 6, 0, 3, 4, 0, 0, 0, 1, 0 }
	   };
	
	public static void main(String[] args) {
		OptionsGrid optionsGrid = new OptionsGrid(startingGrid);
		
		SinglesScanner singlesScanner = new SinglesScanner(optionsGrid);
		
		while(singlesScanner.fillSingleSolutions());
		
		if(!optionsGrid.isSolved()) {
		   Guesser guesser = new Guesser(optionsGrid);
		   OptionsGrid solution = guesser.solve();
		   if(solution != null)
		      solution.print();
		   else
		      System.out.println("Solution could not be found.");
		} else {
	      optionsGrid.print();
		}
	}
}
