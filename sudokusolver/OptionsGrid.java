package sudokusolver;

public class OptionsGrid {
	private Grid mParentGrid;
	
	private boolean[][][] mOptions;
	
	public OptionsGrid(Grid grid) {
		mParentGrid = grid;
		mOptions = new boolean[9][9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				for(int k = 0; k < 9; k++) {
					mOptions[i][j][k] = false;
				}
			}
		}
	}
	
	public void findAllValidOptions() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(mParentGrid.getValue(i, j) != 0)
					continue;
				
				for(int k = 1; k <= 9; k++) {
					mOptions[i][j][k-1] = mParentGrid.checkValue(i, j, k);
				}
			}
		}
	}
}
