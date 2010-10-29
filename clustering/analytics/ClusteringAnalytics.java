package analytics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import utils.Pattern;
import utils.algorithms.KMeansUtils;
import utils.fourDPoint.FourDPoint;
import utils.fourDPoint.FourDPointUtils;
import kMeans.ABFAndAFB;
import kMeans.DHB;
import kMeans.DHF;
import kMeans.KMeansTypeAlgorithm;
import kMeans.NormalKmeans;
import kMeans.ABFAndAFB.Alternating;
import dataPrepration.BritishTownsLoader;
import dataPrepration.DataSets;

public class ClusteringAnalytics {

	private float[] objFunctions;

	@SuppressWarnings("rawtypes")
	private List<KMeansTypeAlgorithm> algorithms;

	private ClustersInitializer initializer;

	@SuppressWarnings("rawtypes")
	private KMeansUtils utils;

	private final static int NUM_ALGORITHMS = 5;

	@SuppressWarnings("rawtypes")
	private List patterns;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ClusteringAnalytics(DataSets dataSet, int numClusters, int dhfRuns,
			int dhbRuns) throws FileNotFoundException {
		initializer = new ClustersInitializer(numClusters);
		objFunctions = new float[NUM_ALGORITHMS];
		switch (dataSet) {
		case BRITICH_TOWNS:
			patterns = new BritishTownsLoader().load();
			utils = new FourDPointUtils();
			algorithms = new ArrayList<KMeansTypeAlgorithm>(NUM_ALGORITHMS);

			algorithms.add(new NormalKmeans<FourDPoint>(numClusters, patterns,
					utils));

			algorithms.add(new DHF<FourDPoint>(numClusters, patterns, utils));

			algorithms.add(new DHB<FourDPoint>(numClusters, patterns, utils));

			algorithms.add(new ABFAndAFB(Alternating.DHB, dhfRuns, dhbRuns,
					numClusters, patterns, utils));

			algorithms.add(new ABFAndAFB(Alternating.DHF, dhfRuns, dhbRuns,
					numClusters, patterns, utils));

			break;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void next() {

		List<Set<Integer>> cluster = initializer.getClusters();
		// clusters, patternClusterMap, centers

		int i = 0;
		for (KMeansTypeAlgorithm algorithm : algorithms) {

			algorithm.setInitialConfigs(null, null, null);
			algorithm.cluster();
			// patterns, clusters, centers
			objFunctions[i] = utils.calculateObjectiveFunction(patterns,
					algorithm.getClusters(), algorithm.getCenters());
			i++;
		}
	}

	public float getKMeansObj() {
		return objFunctions[0];
	}

	public float getDHBObj() {
		return objFunctions[1];
	}

	public float getDHFObj() {
		return objFunctions[2];
	}

	public float getABFObj() {
		return objFunctions[3];
	}

	public float getAFBObj() {
		return objFunctions[4];
	}

}
