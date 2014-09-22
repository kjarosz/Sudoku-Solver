package sudokusolver;

import java.util.LinkedList;

public class Guesser {
   private class Frame {
      OptionsGrid mBaseGrid;
      int row;
      int column;
      int value;
   }
   
   private LinkedList<Frame> mGuessStack;
   
   public Guesser(OptionsGrid source) {
      mGuessStack = new LinkedList<>();
      
      // Find the first square to guess at and dew it.
      findBlock(source);
   }
   
   private void findBlock(OptionsGrid grid) {
      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            if(grid.getValue(i, j) != 0)
               continue;
            
            for(int k = 0; k < 9; k++) {
               if(grid.checkEntry(i, j, k+1)) {
                  Frame frame = new Frame();
                  frame.mBaseGrid = new OptionsGrid(grid);
                  frame.row = i;
                  frame.column = j;
                  frame.value = k+1;
                  mGuessStack.push(frame);
               }
            }
            return;
         }
      }
   }
   
   public OptionsGrid solve() {
      while(!mGuessStack.isEmpty()) {
         Frame frame = mGuessStack.pop();
         OptionsGrid grid = frame.mBaseGrid;
         grid.setValue(frame.row, frame.column, frame.value);
         
         SinglesScanner singlesScanner = new SinglesScanner(grid);
         while(singlesScanner.fillSingleSolutions());
         
         if(grid.isSolved())
            return grid;
         
         if(grid.isContradictory())
            continue;
         
         findBlock(grid);
      }
      return null;
   }
}
