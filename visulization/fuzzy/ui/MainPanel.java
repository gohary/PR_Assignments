package fuzzy.ui;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import fuzzy.controller.PlotActionListener;

public class MainPanel extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8920997326735022832L;

	private JScrollPane areaScrollPane;
	private JFrame frame;

	public MainPanel(JFrame frame) {
		this.frame = frame;
		PlotActionListener controller = new PlotActionListener();
		controller.setCharPainter(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new OptionsPan(controller));
	}

	public void showResults(JTable table) {

		if (areaScrollPane != null) {
			remove(areaScrollPane);
		}
		JScrollPane areaScrollPane2 = new JScrollPane(table);
		add(areaScrollPane2);
		areaScrollPane = areaScrollPane2;
		getParent().repaint();

		frame.repaint();

	}

}
