package testing;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import kMeans.NormalKmeans;

import utils.fourDPoint.FourDPoint;
import utils.fourDPoint.FourDPointUtils;

import dataPrepration.BritishTownsLoader;

public class ConsoleTestNormalKmeans {

	public static void main(String[] args) throws FileNotFoundException {

		BritishTownsLoader loader = new BritishTownsLoader();
		loader.load();

		List<FourDPoint> patterns = loader.getPatterns();

		int numClusters = loader.getNumOfClusters();

		NormalKmeans<FourDPoint> normalKmeans = new NormalKmeans<FourDPoint>();
		normalKmeans.setPatternUtils(new FourDPointUtils());
		List<Set<Integer>> rs = normalKmeans.cluster(patterns, numClusters);

		for (Set<Integer> cluster : rs) {
			System.out.println(cluster.size());
		}
	}
}
