package ui;


import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

import controller.PlotActionListener;

public class MainPanel extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8920997326735022832L;

	private ChartPanel currentChart;
	private JFrame frame;

	public MainPanel(JFrame frame) {
		this.frame = frame;
		PlotActionListener controller = new PlotActionListener();
		controller.setCharPainter(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new OptionsPan(controller));
	}

	public void showChart(ChartPanel chartPan) {

		if (currentChart != null) {
			remove(currentChart);
		}
		add(chartPan);
		currentChart = chartPan;
		getParent().repaint();

		frame.repaint();

	}

}
