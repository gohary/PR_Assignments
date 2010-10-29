package test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import analytics.ClustersInitializer;

import kMeans.NormalKmeans;
import utils.Point2D.Point2D;
import utils.Point2D.Point2DUtils;
import dataPrepration.GaussianLoader;

public class Point2DTest {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		List<Point2D> points = new GaussianLoader().load();
		Point2DUtils utils = new Point2DUtils();

		NormalKmeans<Point2D> kmeans = new NormalKmeans<Point2D>(4, points,
				utils);

		ClustersInitializer init = new ClustersInitializer(4);
		init.initClusters1(points);

		List<Point2D> centers = new ArrayList<Point2D>();
		for (Set<Integer> c : init.getClusters()) {
			List<Point2D> pts = new ArrayList<Point2D>();
			for (int i : c) {
				pts.add(points.get(i));
			}
			centers.add(utils.getCenter(pts));
		}
		kmeans.setInitialConfigs(init.getClusters(),
				init.getPatternClusterMap(), centers);
		kmeans.cluster();

		List<Set<Integer>> clusters = init.getClusters();
		// centers = kmeans.getCenters();

		PrintWriter pw = new PrintWriter("data/rs2_kmeans");
		int i = 1;
		for (Set<Integer> c : clusters) {
			for (int k : c) {
				pw.println(i + " " + points.get(k));
			}
			i++;
		}
		System.out.println("Centers: ");
		for (Point2D cen : centers) {
			System.out.println(cen);
		}
		pw.close();
	}
}
