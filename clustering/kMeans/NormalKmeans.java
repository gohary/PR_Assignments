package kMeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import utils.Pattern;
import utils.algorithms.KMeansUtils;

public class NormalKmeans<T extends Pattern> {

	private KMeansUtils<T> utils;
	private List<T> patterns;
	private List<Set<Integer>> clusters;
	private int numClusters;

	// maintained in a list to be able to use a generic type T
	private List<T> centers;
	private int numPatterns;
	private Map<Integer, Integer> patternClusterMap;

	public void setPatternUtils(KMeansUtils<T> utils) {
		this.utils = utils;
	}

	public List<Set<Integer>> cluster(List<T> patterns, int numClusters) {
		this.patterns = patterns;
		this.numClusters = numClusters;
		numPatterns = patterns.size();
		initClusters();

		do {
			recalculateCenters();
		} while (assignPatternsToClusters());

		return clusters;
	}

	/**
	 * Two runs within the same millisecond will have the same sequence of
	 * random numbers.
	 */
	private void initClusters() {
		// randomly select the centers
		clusters = new ArrayList<Set<Integer>>();
		centers = new ArrayList<T>();
		patternClusterMap = new HashMap<Integer, Integer>();
		HashSet<Integer> rands = new HashSet<Integer>();
		rands.add(-1);
		Random randGen = new Random();
		for (int i = 0; i < numClusters; i++) {
			clusters.add(new HashSet<Integer>());

			int randCenter = -1;
			while (rands.contains(randCenter)) {
				randCenter = randGen.nextInt(numPatterns);

			}

			rands.add(randCenter);
			centers.add(patterns.get(randCenter));
		}

		assignPatternsToClusters();
	}

	private void recalculateCenters() {
		int i = 0;
		for (Set<Integer> cluster : clusters) {
			centers.set(i, utils.getCenter(getPatterns(cluster)));
			i++;
		}
	}

	private List<T> getPatterns(Set<Integer> cluster) {
		List<T> patts = new ArrayList<T>();
		for (int i : cluster) {
			patts.add(patterns.get(i));
		}
		return patts;
	}

	/**
	 * 
	 * @return true if the assignment changed
	 */
	private boolean assignPatternsToClusters() {
		boolean assignmentChanged = false;
		int i = 0;
		for (T pattern : patterns) {
			int nearestCenter = getNearestCenter(pattern);

			if (clusters.get(nearestCenter).add(i)) {
				if (!assignmentChanged) {
					assignmentChanged = true;
				}
				// removing from the previous cluster
				Integer clusterId = patternClusterMap.get(i);
				if (clusterId != null) {
					clusters.get(clusterId).remove(i);
				}

				patternClusterMap.put(i, nearestCenter);

			}

			i++;
		}
		return assignmentChanged;
	}

	private int getNearestCenter(T pattern) {
		float minDistance = Float.MAX_VALUE;
		int minCenter = -1;
		int i = 0;
		for (T center : centers) {
			float distance = utils.getDistanceSquare(center, pattern);

			if (distance < minDistance) {
				minDistance = distance;
				minCenter = i;
			}
			i++;
		}

		return minCenter;
	}

	public List<T> getCenters() {
		return this.centers;
	}

}
