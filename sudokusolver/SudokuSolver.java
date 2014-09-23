package sudokusolver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.text.ParseException;

public class SudokuSolver extends JFrame {
	private JTextField mGrid[][];
	private boolean mSolved;

	public SudokuSolver() {
	   super("Sudoku Solver");
	   setSize(350, 350);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	   Container contentPanel = this.getContentPane();
	   contentPanel.setLayout(new BorderLayout());

	   mSolved = false;
	   createGrid();
	   createButtonPanel();

	   setVisible(true);
	}

	private void createGrid() {
	   JPanel gridPanel = new JPanel();
	   gridPanel.setLayout(new GridLayout(9, 9, 0, 0));
	   mGrid = new JTextField[9][];
	   for(int i = 0; i < 9; i++) {
	      mGrid[i] = new JTextField[9];
	      for(int j = 0; j < 9; j++) {
	         try {
	            mGrid[i][j] = new JFormattedTextField(new MaskFormatter("#"));
	         }
	         catch (ParseException ex) {
	            mGrid[i][j] = new JTextField(1);
	         }
	         mGrid[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
	         mGrid[i][j].setForeground(Color.BLACK);
	         gridPanel.add(mGrid[i][j]);
	      }
	   }
	   getContentPane().add(gridPanel, BorderLayout.CENTER);
	}

	private void createButtonPanel() {
      JPanel buttonPanel = new JPanel(new BorderLayout());

      final JButton solveButton = new JButton("Solve");
      solveButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
			if(mSolved) {
				clearGrid();
				solveButton.setText("Solve");
				mSolved = false;
			}
			else {
				solveGrid();
				solveButton.setText("Clear");
				mSolved = true;
			}
         }
      });
      buttonPanel.add(solveButton);

      getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private void solveGrid() {
	   int grid[][] = getInputGrid();
	   if(grid == null)
	      return;

	   final OptionsGrid solution = getSolution(grid);
	   if(solution == null)
	      return;

	   updateGridWithSolution(solution);
	}

	private int[][] getInputGrid() {
      int grid[][] = new int[9][9];
      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            String fieldText = mGrid[i][j].getText();
            if("".equals(fieldText) || " ".equals(fieldText)) {
               grid[i][j] = 0;
               mGrid[i][j].setForeground(Color.BLUE);
            } else {
               try {
                  int entry = Integer.parseInt(fieldText);
                  if(0 < entry && entry <= 9) {
                     grid[i][j] = entry;
                  } else {
                     JOptionPane.showMessageDialog(this, "Cell (" + i + ", " + j + ") contains an invalid value.");
                     return null;
                  }
               } catch(NumberFormatException ex) {
                  System.out.println("Warning: cell ("+i+", "+j+") contains non-int character '" + fieldText + "'; assuming 0.");
                  grid[i][j] = 0;
                  return null;
               }
            }
         }
      }
      return grid;
	}

	private OptionsGrid getSolution(int grid[][]) {
      OptionsGrid optionsGrid = new OptionsGrid(grid);

      SinglesScanner singlesScanner = new SinglesScanner(optionsGrid);

      while(singlesScanner.fillSingleSolutions());

      if(!optionsGrid.isSolved()) {
         Guesser guesser = new Guesser(optionsGrid);
         OptionsGrid solution = guesser.solve();
         if(solution != null) {
            return solution;
         } else {
            JOptionPane.showMessageDialog(this, "Solution could not be found.");
            return null;
         }
      } else {
         return optionsGrid;
      }
	}

	private void updateGridWithSolution(OptionsGrid solution) {
	   for(int i = 0; i < 9; i++) {
	      for(int j = 0; j < 9; j++) {
	         mGrid[i][j].setText(Integer.toString(solution.getValue(i, j)));
	      }
	   }
	}

	private void clearGrid() {
	   for(int i = 0; i < 9; i++)
	      for(int j = 0; j < 9; j++) {
	         mGrid[i][j].setText("");
	         mGrid[i][j].setForeground(Color.BLACK);
	      }
	}

	public static void main(String[] args) {
	   // Set look and feel for the GUI
	   try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	   }
	   catch (UnsupportedLookAndFeelException e) {
	      System.out.println("Unsupported look and feel.");
	      return;
	   }
	   catch (ClassNotFoundException e) {
	      System.out.println("Look and feel class not found.");
	      return;
	   }
	   catch (InstantiationException e) {
	      System.out.println("Instantiation failed while setting look and feel.");
	      return;
	   }
	   catch (IllegalAccessException e) {
	      System.out.println("Illegal access while setting look and feel.");
	      return;
	   }
	   SwingUtilities.invokeLater(new Runnable() {
	      @Override
	      public void run() {
	         new SudokuSolver();
	      }
	   });
	}
}
