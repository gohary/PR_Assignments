package ui;

import java.awt.Container;
import java.awt.Font;

import javax.swing.BoxLayout;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import controller.PlotActionListener;

public class MainPanel extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8920997326735022832L;

	private ChartPanel currentChart;

	public MainPanel() {

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
	}

}
