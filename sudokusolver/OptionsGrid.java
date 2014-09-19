package sudokusolver;

public class OptionsGrid {
	private Grid mParentGrid;
	
	private boolean[][][] mValidEntries;
	
	public OptionsGrid(Grid grid) {
		mParentGrid = grid;
		mValidEntries = new boolean[9][9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				for(int k = 0; k < 9; k++) {
					mValidEntries[i][j][k] = false;
				}
			}
		}
		
		findAllValidOptions();
	}
	
	public OptionsGrid(OptionsGrid source) {
	   mParentGrid = new Grid(source.mParentGrid);
	   mValidEntries = new boolean[9][9][9];
	   for(int i = 0; i < 9; i++) {
	      for(int j = 0; j < 9; j++) {
	         for(int k = 0; k < 9; k++) {
	            mValidEntries[i][j][k] = source.mValidEntries[i][j][k];
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
				if(mParentGrid.getValue(i, j) != 0)
				   continue;
				
				for(int k = 0; k < 9; k++) {
					mValidEntries[i][j][k] = mParentGrid.checkValue(i, j, k+1);
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
	public boolean setValue(int row, int column, int value) {
	   if(checkEntry(row, column, value)) {
	      mParentGrid.setValue(row, column, value);
	      correctValidEntries(row, column, value);
	      return true;
	   } else {
	      return false;
	   }
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
	 * This function finds all cells in which there can be only one
	 * entry and fills them in.
	 */
	public boolean fillUniqueSolutions() {
	   return     fillUniqueSolutionsInSingleCells()
	         ||   fillUniqueSolutionsInBlocks()  
	         ||   fillUniqueSolutionsInRows() 
	         ||   fillUniqueSolutionsInColumns();
	}
	
	/*
	 * Goes through each cell one by one and if and if any
	 * of the cells offers only one valid entry, the algorithm
	 * pencils it in. If any cell has been filled, the function
	 * returns true. Otherwise, false.	
	 */
	private boolean fillUniqueSolutionsInSingleCells() {
      boolean cellFilled = false;
      for(int i = 0; i < 9; i++) {
         nextCell:
         for(int j = 0; j < 9; j++) {
            int entry = -1;
            for(int k = 0; k < 9; k++) {
               if(mValidEntries[i][j][k]) {
                  if(entry < 0) {
                     entry = k;
                  } else {
                     continue nextCell;
                  }
               }
            }
            if(entry >= 0) {
               setValue(i, j, entry+1);
               cellFilled = true;
            }
         }
      }
      return cellFilled;
	}
	
	/*
	 * This function takes entries 1-9 for each 3x3 block
	 * and checks if any of the entries have a unique place
	 * in which they belong (they can only fit in one cell)
	 * and then pencils them in. If any cell has been filled,
	 * the function returns true, otherwise false.
	 */
	private boolean fillUniqueSolutionsInBlocks() {
	   boolean cellFilled = false;
	   for(int i = 0; i < 3; i++) {
	      for(int j = 0; j < 3; j++) {
	         cellFilled |= fillUniqueSolutionsInBlock(i, j);
	      }
	   }
	   return cellFilled;
	}
	
	/*
	 * This function checks for unique solutions in one 3x3 block.
	 */
	private boolean fillUniqueSolutionsInBlock(int gridY, int gridX) {
	   boolean entryFilled = false;
	   nextEntry:
	   for(int k = 0; k < 9; k++) {
	      int entryRow = -1, entryColumn = -1;
	      for(int i = 0; i < 3; i++) {
	         for(int j = 0; j < 3; j++) {
	            if(mValidEntries[gridY*3 + i][gridX*3 + j][k]) {
	               if(entryRow < 0) {
	                  entryRow = gridY*3 + i;
	                  entryColumn = gridX*3 + j;
	               } else {
	                  continue nextEntry;
	               }
	            }
	         }
	      }
	      if(entryRow >= 0 && entryColumn >= 0) {
	         setValue(entryRow, entryColumn, k+1);
	         entryFilled = true;
	      }
	   }
	   return entryFilled;
	}
	
	/*
	 * Same as the functions above.
	 */
	private boolean fillUniqueSolutionsInRows() {
	   boolean entryFilled = false;
	   nextEntry:
      for(int k = 0; k < 9; k++) {
         for(int i = 0; i < 9; i++) {
            int entryColumn = -1;
            for(int j = 0; j < 9; j++) {
               if(mValidEntries[i][j][k]) {
                  if(entryColumn < 0)
                     entryColumn = j;
                  else
                     continue nextEntry;
               }
            }
            if(entryColumn >= 0) {
               setValue(i, entryColumn, k+1);
               entryFilled = true;
            }
         }
      }
	   return entryFilled;
	}
   
   /*
    * Same as the functions above.
    */
   private boolean fillUniqueSolutionsInColumns() {
      boolean entryFilled = false;
      nextEntry:
      for(int k = 0; k < 9; k++) {
         for(int i = 0; i < 9; i++) {
            int entryRow = -1;
            for(int j = 0; j < 9; j++) {
               if(mValidEntries[j][k][k]) {
                  if(entryRow < 0)
                     entryRow = j;
                  else
                     continue nextEntry;
               }
            }
            if(entryRow >= 0) {
               setValue(entryRow, i, k+1);
               entryFilled = true;
            }
         }
      }
      return entryFilled;
   }
   
   public boolean compare(boolean[][][] options) {
      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            for(int k = 0; k < 9; k++) {
               if(options[i][j][k] != mValidEntries[i][j][k]) {
                  System.out.println("(" + i + ", " + j + ", " + k + ")");
                  return false;
               }
            }
         }
      }
      return true;
   }
	
	public void printCellOptions(int row, int column) {
	   for(int i = 0; i < 9; i++) {
	      if(mValidEntries[row][column][i]) {
	         System.out.print((i+1) + " ");
	      }
	   }
	   System.out.println();
	}
}
