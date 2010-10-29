package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import Jama.Matrix;
import data.Pattern;
import data.PointReader;


public class Visualizer extends ApplicationFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private XYPlot plot;
	
	public Visualizer(String title) {
		super(title);
	}
	
	public void visualize(Map<String, List<Pattern>> clusteredDataSet, boolean annotate){

		XYSeriesCollection seriesCollection = new XYSeriesCollection();
		HashMap<String, XYSeries> clusterSerieses = new HashMap<String, XYSeries>();
		
		Iterator<String> clusterItr = clusteredDataSet.keySet().iterator();
		while(clusterItr.hasNext()){
			String clusterLabel = clusterItr.next();
			XYSeries series = new XYSeries(clusterLabel);
			List<Pattern> classPatterns = clusteredDataSet.get(clusterLabel);
			Iterator<Pattern> classPatternItr = classPatterns.iterator();
			while(classPatternItr.hasNext()){
				Pattern classPattern = classPatternItr.next();
				Matrix patternFeatures = classPattern.getFeatureVector();
				double x = patternFeatures.get(0, 0);
				double y = patternFeatures.get(0, 1);
				series.add(x, y);
			}
			clusterSerieses.put(clusterLabel, series);
		}

		clusterItr = clusterSerieses.keySet().iterator();
		while(clusterItr.hasNext()){
			String classLabel = clusterItr.next();
			seriesCollection.addSeries(clusterSerieses.get(classLabel));
		}
		
        XYDataset dataset = seriesCollection;
        JFreeChart chart = createChart(dataset);
        ChartPanel panel = new ChartPanel(chart, true);
        setContentPane(panel);
        
        pack();
		RefineryUtilities.centerFrameOnScreen(this);
		setVisible(true); 
	}
	
	
	
	
    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot(
            "Points Scatter",
            "X", "Y", dataset,
            PlotOrientation.VERTICAL,
            true,  // legend
            false,  // tooltips
            false  // urls
        );
        plot = chart.getXYPlot();
        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotHeight(4);
        renderer.setDotWidth(4);
        plot.setRenderer(renderer);
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(true);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(true);
        return chart;
    }
	
	
	public static void main(String[] args) {
		Visualizer v = new Visualizer("Test");
		PointReader reader = new PointReader();
		HashMap<String, List<Pattern>> dataSet = reader.read("dat/rs1_afb"); 
		v.visualize(dataSet, false);
	}
	
}
