package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import ui.MainPanel;
import ui.OptionsPan;

public class PlotActionListener implements ActionListener {

	private OptionsPan optionsPan;
	private MainPanel charPainter;

	private ChartCreator chartCreator;

	public PlotActionListener() {
		chartCreator = new ChartCreator();
	}

	public void setOptionsPan(OptionsPan optionsPan) {
		this.optionsPan = optionsPan;
	}

	public void setCharPainter(MainPanel charPainter) {
		this.charPainter = charPainter;
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		System.out.println(((JButton) action.getSource()).getText());
		System.out.println(optionsPan.getDataSet());

		JFreeChart chart = chartCreator.createChart(
				optionsPan.getNumClusters(), optionsPan.getDHFRuns(),
				optionsPan.getDHBRuns(), optionsPan.getDataSet(),
				optionsPan.getNumRuns());
		final ChartPanel panel = new ChartPanel(chart, true, true, true, false,
				true);

		panel.setPreferredSize(new java.awt.Dimension(500, 270));

		charPainter.showChart(panel);
	}

}
