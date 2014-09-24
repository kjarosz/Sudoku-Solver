/*
Copyright (C) 2014  Kamil Jarosz and Christopher Kyle Horton

This file is part of Sudoku-Solver.

Sudoku-Solver is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package sudokusolver;

public class OptionsGrid extends Grid {
	private boolean[][][] mValidEntries;

   public OptionsGrid() {
      mValidEntries = new boolean[9][9][9];
      clearEntries();
   }

	public OptionsGrid(int[][] startingGrid) {
		super(startingGrid);
		mValidEntries = new boolean[9][9][9];
		clearEntries();
		findAllValidOptions();
	}

	public OptionsGrid(OptionsGrid source) {
	   super(source);
	   mValidEntries = new boolean[9][9][9];
	   for(int i = 0; i < 9; i++) {
	      for(int j = 0; j < 9; j++) {
	         for(int k = 0; k < 9; k++) {
	            mValidEntries[i][j][k] = source.mValidEntries[i][j][k];
	         }
	      }
	   }
	}

	private void clearEntries() {
      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            for(int k = 0; k < 9; k++) {
               mValidEntries[i][j][k] = false;
            }
         }
      }
	}

	/*
	 * This function will enlist all valid options
	 * for the sudoku grid. Filled out cells will be
	 * skipped and for empty cells, the algorithm
	 * will check if any number makes the grid invalid.
	 */
	private void findAllValidOptions() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(super.getValue(i, j) != 0)
				   continue;

				for(int k = 0; k < 9; k++) {
					mValidEntries[i][j][k] = checkValue(i, j, k+1);
				}
			}
		}
	}

	/*
	 * Check if the value is a valid entry for the cell.
	 */
	public boolean checkEntry(int row, int column, int value) {
	   return mValidEntries[row][column][value-1];
	}

	/*
	 * Check if the value is a valid entry, if so, set the
	 * value and correct the valid entry listing to account
	 * for the new value.
	 */
	public void setValue(int row, int column, int value) {
	   if(!checkEntry(row, column, value))
	      return;

      super.setValue(row, column, value);
      correctValidEntries(row, column, value);

	}

	private void correctValidEntries(int row, int column, int value) {
	   correctValidEntriesInCell(row, column);
      correctValidEntriesInGrid((int)Math.floor(row/3), (int)Math.floor(column/3), value);
      correctValidEntriesInRow(row, value);
      correctValidEntriesInColumn(column, value);
	}

	private void correctValidEntriesInCell(int row, int column) {
	   for(int i = 0; i < 9; i++) {
	      mValidEntries[row][column][i] = false;
	   }
	}

	private void correctValidEntriesInGrid(int blockY,  int blockX, int value) {
	   for(int i = blockY*3; i < blockY*3 + 3; i++) {
	      for(int j = blockX*3; j < blockX*3 + 3; j++) {
	         mValidEntries[i][j][value-1] = false;
	      }
	   }
	}

	private void correctValidEntriesInRow(int row, int value) {
	   for(int i = 0; i < 9; i++) {
	      mValidEntries[row][i][value-1] = false;
	   }
	}

	private void correctValidEntriesInColumn(int column, int value) {
	   for(int i = 0; i < 9; i++) {
	      mValidEntries[i][column][value-1] = false;
	   }
	}

	/*
	 * This function will check if the grid is still solvable
	 */
	public boolean isContradictory() {
	   for(int i = 0; i < 9; i++) {
	      nextEntry:
	      for(int j = 0; j < 9; j++) {
	         if(getValue(i, j) != 0)
	            continue;

	         for(int k = 0; k < 9; k++) {
	            if(mValidEntries[i][j][k])
	               continue nextEntry;
	         }

	         return true;
	      }
	   }
	   return false;
	}
}
