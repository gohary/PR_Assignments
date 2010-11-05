package analytics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kMeans.ABFAndAFB;
import kMeans.ABFAndAFB.Alternating;
import kMeans.DHB;
import kMeans.DHF;
import kMeans.KMeansTypeAlgorithm;
import kMeans.NormalKmeans;
import utils.Pattern;
import utils.algorithms.KMeansUtils;
import utils.fourDPoint.FourDPoint;
import utils.fourDPoint.FourDPointUtils;
import dataPrepration.BritishTownsLoader;
import dataPrepration.DataSets;
import dataPrepration.IRISLoader;

//TODO FIX THE GENERICS

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

	@SuppressWarnings("rawtypes")
	List centers;

	private int numClusters;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ClusteringAnalytics(DataSets dataSet, int numClusters, int dhfRuns,
			int dhbRuns) throws FileNotFoundException {
		initializer = new ClustersInitializer(numClusters);
		this.numClusters = numClusters;
		objFunctions = new float[NUM_ALGORITHMS];
		centers = new ArrayList();
		for (int j = 0; j < numClusters; j++) {
			centers.add(null);
		}
		switch (dataSet) {
		case BRITICH_TOWNS:
			patterns = new BritishTownsLoader().load();
			break;
		case IRIS:
			patterns = new IRISLoader().load();
			break;
		}
		utils = new FourDPointUtils();
		algorithms = new ArrayList<KMeansTypeAlgorithm>(NUM_ALGORITHMS);

		algorithms.add(new NormalKmeans<FourDPoint>(numClusters, patterns,
				utils));

		algorithms.add(new DHB<FourDPoint>(numClusters, patterns, utils));

		algorithms.add(new DHF<FourDPoint>(numClusters, patterns, utils));

		algorithms.add(new ABFAndAFB(Alternating.DHB, dhfRuns, dhbRuns,
				numClusters, patterns, utils));

		algorithms.add(new ABFAndAFB(Alternating.DHF, dhfRuns, dhbRuns,
				numClusters, patterns, utils));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void next() throws CloneNotSupportedException {
		if (numClusters < 7) {
			initializer.initClusters2(patterns);
		} else {
			initializer.initClusters2(patterns);
		}
		List<Set<Integer>> clusters = initializer.getClusters();
		List centers = initializer.centers;//calculateCenters(clusters);
		Map<Integer, Integer> patternClusterMap = initializer
				.getPatternClusterMap();

		int i = 0;
		for (KMeansTypeAlgorithm algorithm : algorithms) {

			algorithm.setInitialConfigs(cloneClusters(clusters),
					clone(patternClusterMap), cloneCenters(centers));
			algorithm.cluster();
			// patterns, clusters, centers
			objFunctions[i] = utils.calculateObjectiveFunction(patterns,
					algorithm.getClusters(), algorithm.getCenters());
			i++;
		}
	}

	private List<Set<Integer>> cloneClusters(List<Set<Integer>> clusters) {
		List<Set<Integer>> clone = new ArrayList<Set<Integer>>(clusters.size());
		for (Set<Integer> cluter : clusters) {
			Set<Integer> cloneCluster = new HashSet<Integer>();
			cloneCluster.addAll(cluter);
			clone.add(cloneCluster);
		}
		return clone;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List cloneCenters(List centers) throws CloneNotSupportedException {
		List clone = new ArrayList(centers.size());
		for (Object p : centers) {
			clone.add(((Pattern) p).clone());
		}
		return clone;
	}

	private Map<Integer, Integer> clone(Map<Integer, Integer> patternClusterMap) {
		Map<Integer, Integer> clone = new HashMap<Integer, Integer>();
		clone.putAll(patternClusterMap);
		return clone;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List calculateCenters(List<Set<Integer>> clusters) {
		int i = 0;

		for (Set<Integer> cluster : clusters) {
			Pattern cen = utils.getCenter(getPatterns(cluster));
			centers.set(i, cen);
			i++;
		}
		return centers;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Pattern> getPatterns(Set<Integer> cluster) {
		List patts = new ArrayList<Pattern>();
		for (int i : cluster) {
			patts.add(patterns.get(i));
		}
		return patts;
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
