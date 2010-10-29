package kMeans;

import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Pattern;
import utils.algorithms.KMeansUtils;

public abstract class KMeansTypeAlgorithm<T extends Pattern> {

	protected KMeansUtils<T> utils;
	protected List<T> patterns;

	protected List<Set<Integer>> clusters;
	protected Map<Integer, Integer> patternClusterMap;

	// maintained in a list to be able to use a generic type T
	protected List<T> centers;

	protected int numClusters;
	protected int numPatterns;

	public KMeansTypeAlgorithm(int numClusters, List<T> patterns,
			KMeansUtils<T> utils) {
		this.numClusters = numClusters;
		this.numPatterns = patterns.size();
		this.patterns = patterns;
		this.utils = utils;

	}

	public void setInitialConfigs(List<Set<Integer>> clusters,
			Map<Integer, Integer> patternClusterMap, List<T> centers) {
		this.clusters = clusters;
		this.patternClusterMap = patternClusterMap;
		this.centers = centers;
	}

	public abstract void cluster();

	public List<T> getCenters() {
		return this.centers;
	}

	public List<Set<Integer>> getClusters() {
		return this.clusters;
	}
}
