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
