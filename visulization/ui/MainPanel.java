package ui;


import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8920997326735022832L;

	public MainPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(new OptionsPan());

		add(Box.createRigidArea(new Dimension(2,2)));
		
		add(new PaintingPanel());

	}

}
