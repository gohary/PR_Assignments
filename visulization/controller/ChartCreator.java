package controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.jfree.chart.JFreeChart;
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

import utils.Pattern;
import analytics.ClusteringAnalytics;
import dataPrepration.BritishTownsLoader;
import dataPrepration.DataSets;
import dataPrepration.GermanTownsLoader;

public class ChartCreator {

	public JFreeChart createChart(int numClusters, int DHFRuns, int DHBRuns,
			int dataSet, int numRuns) throws CloneNotSupportedException {

		List<Pattern> patterns = null;
		switch (dataSet) {
		case 1:
			// British Towns
			BritishTownsLoader britishLoader = new BritishTownsLoader();
			try {
				britishLoader.load();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			patterns = britishLoader.getPatterns();
			break;
		case 2:
			// German Towns

			GermanTownsLoader germanLoader = new GermanTownsLoader();
			try {
				germanLoader.load();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			patterns = germanLoader.getPatterns();
			break;
		default:
			return null;
		}

		final XYDataset data = createDataset(numClusters, DHFRuns, DHBRuns,
				patterns, numRuns, dataSet);

		final XYItemRenderer renderer1 = new StandardXYItemRenderer();

		final NumberAxis rangeAxis1 = new NumberAxis("Objective Function");

		final XYPlot subplot1 = new XYPlot(data, null, rangeAxis1, renderer1);

		subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		// parent plot…

		final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(
				new NumberAxis("Run Number"));

		plot.setGap(10.0);

		// add the subplots…

		plot.add(subplot1, 1);

		plot.setOrientation(PlotOrientation.VERTICAL);

		// return a new chart containing the overlaid plot…

		String title = ((dataSet == 1) ? "British Towns " : "German Towns ")
				+ numClusters + " Clusters";
		return new JFreeChart(title,

		JFreeChart.DEFAULT_TITLE_FONT, plot, true);

	}

	/**
	 * 
	 * Creates a sample dataset.
	 * 
	 * 
	 * 
	 * @return Series 1.
	 * @throws CloneNotSupportedException 
	 */

	private XYDataset createDataset(int numClusters, int DHFRuns, int DHBRuns,
			List<Pattern> patterns, int numRuns, int dataSet) throws CloneNotSupportedException {

		XYSeriesCollection collection = new XYSeriesCollection();

		XYSeries kmeans = new XYSeries("KMeans");
		XYSeries dhb = new XYSeries("DHB");
		XYSeries dhf = new XYSeries("BHF");
		XYSeries abf = new XYSeries("ABF");
		XYSeries afb = new XYSeries("AFB");
		try {
			DataSets ds = null;
			switch (dataSet) {
			case 1:
				ds = DataSets.BRITICH_TOWNS;
				break;
			case 2:
				ds = DataSets.GERMAN_TOWNS;
				break;
			case 3:
				ds = DataSets.IRIS;

			}

			ClusteringAnalytics analytics = new ClusteringAnalytics(ds,
					numClusters, DHFRuns, DHBRuns);

			for (int i = 1; i <= numRuns; i++) {
				analytics.next();
				kmeans.add(i, analytics.getKMeansObj());
				dhb.add(i, analytics.getDHBObj());
				//dhf.add(i, analytics.getDHFObj());
				//abf.add(i, analytics.getABFObj());
				//afb.add(i, analytics.getAFBObj());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO Handle
		}

		collection.addSeries(kmeans);
		collection.addSeries(dhb);
		collection.addSeries(dhf);
		collection.addSeries(abf);
		collection.addSeries(afb);

		return collection;

	}

}
