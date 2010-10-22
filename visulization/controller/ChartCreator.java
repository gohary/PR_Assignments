package controller;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kMeans.ABFAndAFB;
import kMeans.ABFAndAFB.Alternating;
import kMeans.NormalKmeans;

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
import utils.fourDPoint.FourDPoint;
import utils.fourDPoint.FourDPointUtils;
import dataPrepration.BritishTownsLoader;
import dataPrepration.GermanTownsLoader;

public class ChartCreator {

	public JFreeChart createChart(int numClusters, int DHFRuns, int DHBRuns,
			int dataSet, int numRuns) {
		
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
	 */

	private XYDataset createDataset(int numClusters, int DHFRuns, int DHBRuns,
			List<Pattern> patterns, int numRuns, int dataSet) {

		final XYSeriesCollection collection = new XYSeriesCollection();

		collection.addSeries(createKmeansDateSet(numClusters, patterns,
				numRuns, dataSet));

		collection.addSeries(createAFBABFDateSet(true, numClusters, DHFRuns,
				DHBRuns, patterns, numRuns, dataSet));

		collection.addSeries(createAFBABFDateSet(false, numClusters, DHFRuns,
				DHBRuns, patterns, numRuns, dataSet));

		return collection;

	}

	private XYSeries createKmeansDateSet(int numClusters,
			List<Pattern> patterns, int numRuns, int dataSet) {
		final XYSeries kmeansResults = new XYSeries("KMeans");

		if (dataSet == 1) {
			NormalKmeans<FourDPoint> kmeans = new NormalKmeans<FourDPoint>();
			List<FourDPoint> britishTowns = Arrays.asList(patterns
					.toArray(new FourDPoint[] {}));
			FourDPointUtils utils = new FourDPointUtils();
			kmeans.setPatternUtils(utils);
			for (int i = 1; i <= numRuns; i++) {

				List<Set<Integer>> result = kmeans.cluster(britishTowns,
						numClusters);
				List<FourDPoint> centers = kmeans.getCenters();
				float objectiveFundtion = utils.calculateObjectiveFunction(
						britishTowns, result, centers);
				kmeansResults.add(i, objectiveFundtion);

			}
		}

		return kmeansResults;
	}

	private XYSeries createAFBABFDateSet(boolean isAFB, int numClusters,
			int DHFRuns, int DHBRuns, List<Pattern> patterns, int numRuns,
			int dataSet) {
		final XYSeries abf = new XYSeries((isAFB) ? "AFB" : "ABF");
		if (dataSet == 1) {
			ABFAndAFB<FourDPoint> abfAndAFB = new ABFAndAFB<FourDPoint>(
					(isAFB) ? Alternating.DHF : Alternating.DHB, DHFRuns,
					DHBRuns);

			List<FourDPoint> britishTowns = Arrays.asList(patterns
					.toArray(new FourDPoint[] {}));
			FourDPointUtils utils = new FourDPointUtils();
			abfAndAFB.setPatternUtils(utils);
			for (int i = 1; i <= numRuns; i++) {

				List<Set<Integer>> result = abfAndAFB.cluster(britishTowns,
						numClusters);
				List<FourDPoint> centers = abfAndAFB.getCenters();
				float objectiveFundtion = utils.calculateObjectiveFunction(
						britishTowns, result, centers);
				abf.add(i, objectiveFundtion);

			}
		}

		return abf;
	}

}
