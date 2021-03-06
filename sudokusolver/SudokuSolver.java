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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.MaskFormatter;

public class SudokuSolver extends JFrame {
	private ContextMenu mContextInputMenu;
	private JFormattedTextField mGrid[][];
	private boolean mSolved;

	public SudokuSolver() {
	   super("Sudoku Solver");
	   setSize(350, 350);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	   Container contentPanel = this.getContentPane();
	   contentPanel.setLayout(new BorderLayout());

	   mSolved = false;
	   mContextInputMenu = new ContextMenu();
	   createGrid();
	   createButtonPanel();

		ImageIcon appIcon = new ImageIcon("src/images/Sudoku-Solver.png");
		setIconImage(appIcon.getImage());

	   setVisible(true);
	}

	private void createGrid() {
	   createGridFields();

	   JPanel gridPanel = new JPanel(new GridLayout(3, 3, 2, 2));
	   gridPanel.setBackground(Color.BLACK);
	   for(int i = 0; i < 3; i++) {
	      for(int j = 0; j < 3; j++) {
	         JPanel blockPanel = createBlockGrid(i, j);
	         gridPanel.add(blockPanel);
	      }
	   }

	   getContentPane().add(gridPanel, BorderLayout.CENTER);
	}

	private void createGridFields() {
	   mGrid = new JFormattedTextField[9][];
	   for(int i = 0; i < 9; i++) {
	      mGrid[i] = new JFormattedTextField[9];
	      for(int j = 0; j < 9; j++) {
            try {
               mGrid[i][j] = new JFormattedTextField(new MaskFormatter("#"));
            }
            catch (ParseException ex) {
					System.err.println("ParseException while creating grid.");
               System.exit(1);
            }
            mGrid[i][j].setHorizontalAlignment(JFormattedTextField.CENTER);
            mGrid[i][j].setForeground(Color.BLACK);
			   mGrid[i][j].addKeyListener(getGridKeyAdapter(i, j));
			   mGrid[i][j].addMouseListener(getGridMouseAdapter(mGrid[i][j]));
	      }
	   }
	}
	
	private KeyAdapter getGridKeyAdapter(final int row, final int col) {
		return new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
               if (row > 0)
                  mGrid[row - 1][col].requestFocus();
               break;
            case KeyEvent.VK_DOWN:
               if (row < mGrid.length - 1)
                  mGrid[row + 1][col].requestFocus();
               break;
            case KeyEvent.VK_LEFT:
               if (col > 0)
                  mGrid[row][col - 1].requestFocus();
               break;
            case KeyEvent.VK_RIGHT:
               if (col < mGrid[row].length - 1)
                  mGrid[row][col + 1].requestFocus();
               break;
            default:
               break;
            }
         }
      };
	}
	
	private MouseAdapter getGridMouseAdapter(final JFormattedTextField cell) {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Right click
				if(e.getButton() == MouseEvent.BUTTON3 && cell.isEditable()) {
					mContextInputMenu.displayContextMenu(cell, e.getPoint());
				}
			}
		};
	}

	private JPanel createBlockGrid(int blockX, int blockY) {
	   JPanel block = new JPanel(new GridLayout(3, 3, 0, 0));
	   for(int i = blockX*3; i < blockX*3 + 3; i++) {
	      for(int j = blockY*3; j < blockY*3 + 3; j++) {
	         block.add(mGrid[i][j]);
	      }
	   }
	   return block;
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
					mGrid[0][0].requestFocus();
				}
				else {
					if(solveGrid()) {
						solveButton.setText("Clear");
						mSolved = true;
					}
				}
         }
      });
      buttonPanel.add(solveButton);

      getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private boolean solveGrid() {
	   int grid[][] = getInputGrid();
	   if(grid == null)
	      return false;

	   final OptionsGrid solution = getSolution(grid);
	   if(solution == null)
	      return false;

	   updateGridWithSolution(solution);
	   
	   return true;
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
				mGrid[i][j].setEditable(false);
	      }
	   }
	}

	private void clearGrid() {
	   for(int i = 0; i < 9; i++)
	      for(int j = 0; j < 9; j++) {
				mGrid[i][j].setValue(null);
	         mGrid[i][j].setText("");
	         mGrid[i][j].setForeground(Color.BLACK);
				mGrid[i][j].setEditable(true);
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
