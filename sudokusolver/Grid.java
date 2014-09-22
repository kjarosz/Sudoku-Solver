package sudokusolver;

public class Grid {

	private int[][] mGrid;
	
	public Grid() {
		mGrid = new int[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				mGrid[i][j] = 0;
			}
		}
	}
	
	public Grid(int[][] startingGrid) {
		mGrid = new int[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				mGrid[i][j] = startingGrid[i][j];
			}
		}
	}
	
	public Grid(Grid source) {
	   mGrid = new int[9][9];
	   for(int i = 0; i < 9; i++) {
	      for(int j = 0; j < 9; j++) {
	         mGrid[i][j] = source.mGrid[i][j];
	      }
	   }
	}
	
	public int getValue(int row, int column) {
		return mGrid[row][column];
	}
	
	public void setValue(int row, int column, int value) {
		mGrid[row][column] = value;
	}
	
	public boolean checkValue(int row, int column, int value) {
		int oldValue = mGrid[row][column];
		mGrid[row][column] = value;
		boolean result = isValid();
		mGrid[row][column] = oldValue;
		return result;
	}
	
	public boolean isSolved() {
	   if(!isValid())
	      return false;
	   
	   for(int i = 0; i < 9; i++) {
	      for(int j = 0; j < 9; j++) {
	         if(mGrid[i][j] == 0) {
	            return false;
	         }
	      }
	   }
	   
	   return true;
	}
	   
	public boolean isValid() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(!is3x3Valid(i, j)) {
					return false;
				}
			}
		}
		
		for(int i = 0; i < 9; i++) {
			if(!isRowValid(i) || !isColumnValid(i)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean is3x3Valid(int row, int column) {
		for(int i = row*3; i < row*3+3; i++) {
			for(int j = column*3; j < column*3+3; j++) {
				if(mGrid[i][j] == 0)
					continue;
				
				for(int k = row*3; k < row*3+3; k++) {
					for(int l = column*3; l < column*3+3; l++) {
						if(k == i && l == j)
							continue;
						
						if(mGrid[i][j] == mGrid[k][l])
							return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean isRowValid(int row) {
		for(int i = 0; i < 8; i++) {
			if(mGrid[row][i] == 0)
				continue;
			
			for(int j = i+1; j < 9; j++) {
				if(mGrid[row][i] == mGrid[row][j])
					return false;
			}
		}
		return true;
	}
	
	private boolean isColumnValid(int column) {
		for(int i = 0; i < 8; i++) {
			if(mGrid[i][column] == 0)
				continue;
			
			for(int j = i+1; j < 9; j++) {
				if(mGrid[i][column] == mGrid[j][column])
					return false;
			}
		}
		return true;
	}
	
	public void print() {
	   for(int i = 0; i < 9; i++) {
	      for(int j = 0; j < 9; j++) {
	         System.out.print(mGrid[i][j] + " ");
	      }
	      System.out.println();
	   }
	}
}
