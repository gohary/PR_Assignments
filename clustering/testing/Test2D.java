package testing;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kMeans.ABFAndAFB;
import kMeans.ABFAndAFB.Alternating;
import kMeans.NormalKmeans;
import utils.Point2D.Point2D;
import utils.Point2D.Point2DUtils;
import utils.fourDPoint.FourDPoint;
import utils.fourDPoint.FourDPointUtils;

import dataPrepration.GaussianLoader;

public class Test2D {

	public static void main(String[] args) throws FileNotFoundException {

		GaussianLoader loader = new GaussianLoader();
		loader.load();
		List<Point2D> patterns = Arrays.asList(loader.getPatterns().toArray(
				new Point2D[] {}));
		int numClusters = loader.getNumOfClusters();
		NormalKmeans<Point2D> normalKmeans = new NormalKmeans<Point2D>();
		Point2DUtils utils = new Point2DUtils();
		normalKmeans.setPatternUtils(utils);
		List<Set<Integer>> rs = normalKmeans.cluster(patterns, numClusters);
		List<Point2D> centers = normalKmeans.getCenters();

		
		System.out.println("Obj: "
				+ utils.calculateObjectiveFunction(patterns, rs, centers));

		for (Set<Integer> cluster : rs) {
			System.out.println(cluster.size());
		}

		
		int i = 1;
		for (Set<Integer> cluster : rs) {

			for (int j : cluster) {
				Point2D p = patterns.get(j);
				System.out.println(i + "\t" + p.getD1() + "\t" + p.getD2());
			}
			i++;
		}

		for (Point2D c : centers) {
			System.out.println(c.getD1() + "\t" + c.getD2());
		}
	}
}
