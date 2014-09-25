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

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPopupMenu;

public class ContextMenu extends JPopupMenu {
	private JFormattedTextField mSelectedTextField;

	public ContextMenu() {
		createWidgets();
	}

	private void createWidgets() {
		setLayout(new GridLayout(3,3));
		for(int i = 0; i < 9; i++) {
			final JButton button = new JButton((i+1)+"");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mSelectedTextField.setText(button.getText());
					closeContextMenu();
				}
			});
			add(button);
		}
	}

	private void closeContextMenu() {
		this.setVisible(false);
	}

	public void displayContextMenu(JFormattedTextField selectedField, Point mouseCoords) {
		mSelectedTextField = selectedField;
		show(mSelectedTextField, mouseCoords.x-(getWidth()/2), mouseCoords.y-(getHeight()/2));
	}
}
