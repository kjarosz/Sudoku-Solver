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

public class SinglesScanner {
   private OptionsGrid mOptionsGrid;

   public SinglesScanner(OptionsGrid optionsGrid) {
      mOptionsGrid = optionsGrid;
   }

   /*
    * This function finds all cells in which there can be only one
    * entry and fills them in.
    */
   public boolean fillSingleSolutions() {
      return     fillSingleSolutionsInSingleCells()
            ||   fillSingleSolutionsInBlocks()
            ||   fillSingleSolutionsInRows()
            ||   fillSingleSolutionsInColumns();
   }

   /*
    * Goes through each cell one by one and if and if any
    * of the cells offers only one valid entry, the algorithm
    * pencils it in. If any cell has been filled, the function
    * returns true. Otherwise, false.
    */
   private boolean fillSingleSolutionsInSingleCells() {
      boolean cellFilled = false;
      for(int i = 0; i < 9; i++) {
         nextCell:
         for(int j = 0; j < 9; j++) {
            int entry = -1;
            for(int k = 0; k < 9; k++) {
               if(mOptionsGrid.checkEntry(i, j, k+1)) {
                  if(entry < 0) {
                     entry = k;
                  } else {
                     continue nextCell;
                  }
               }
            }
            if(entry >= 0) {
               mOptionsGrid.setValue(i, j, entry+1);
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
   private boolean fillSingleSolutionsInBlocks() {
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
               if(mOptionsGrid.checkEntry(gridY*3 + i, gridX*3 + j, k + 1)) {
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
            mOptionsGrid.setValue(entryRow, entryColumn, k+1);
            entryFilled = true;
         }
      }
      return entryFilled;
   }

   /*
    * Same as the functions above.
    */
   private boolean fillSingleSolutionsInRows() {
      boolean entryFilled = false;
      nextEntry:
      for(int k = 0; k < 9; k++) {
         for(int i = 0; i < 9; i++) {
            int entryColumn = -1;
            for(int j = 0; j < 9; j++) {
               if(mOptionsGrid.checkEntry(i, j, k+1)) {
                  if(entryColumn < 0)
                     entryColumn = j;
                  else
                     continue nextEntry;
               }
            }
            if(entryColumn >= 0) {
               mOptionsGrid.setValue(i, entryColumn, k+1);
               entryFilled = true;
            }
         }
      }
      return entryFilled;
   }

   /*
    * Same as the functions above.
    */
   private boolean fillSingleSolutionsInColumns() {
      boolean entryFilled = false;
      nextEntry:
      for(int k = 0; k < 9; k++) {
         for(int i = 0; i < 9; i++) {
            int entryRow = -1;
            for(int j = 0; j < 9; j++) {
               if(mOptionsGrid.checkEntry(j, i, k+1)) {
                  if(entryRow < 0)
                     entryRow = j;
                  else
                     continue nextEntry;
               }
            }
            if(entryRow >= 0) {
               mOptionsGrid.setValue(entryRow, i, k+1);
               entryFilled = true;
            }
         }
      }
      return entryFilled;
   }
}
