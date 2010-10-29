package kMeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import utils.Pattern;
import utils.algorithms.KMeansUtils;

public class NormalKmeans<T extends Pattern> extends KMeansTypeAlgorithm<T> {

	public NormalKmeans(int numClusters, List<T> patterns, KMeansUtils<T> utils) {
		super(numClusters, patterns, utils);
	}

	public void cluster() {
		while (assignPatternsToClusters()) {
			recalculateCenters();
		}
	}

	private void recalculateCenters() {
		int i = 0;
		for (Set<Integer> cluster : clusters) {
			T cen = utils.getCenter(getPatterns(cluster));
			centers.set(i, cen);
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
					if (!clusters.get(clusterId).remove(i)) {
						System.err.println("err");
					}
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

}
