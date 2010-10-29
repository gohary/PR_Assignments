package kMeans;

import java.util.List;
import java.util.Set;

import utils.Pattern;
import utils.algorithms.KMeansUtils;

public class DHF<T extends Pattern> extends KMeansTypeAlgorithm<T> {

	public DHF(int numClusters, List<T> patterns, KMeansUtils<T> utils) {
		super(numClusters, patterns, utils);
	}

	public void cluster() {
		while (doDHFIteration()) {
		}
	}

	private boolean doDHFIteration() {
		boolean assignmnetChanged = false;
		int i = 0;
		for (T pattern : patterns) {
			int currentCluster = patternClusterMap.get(i);
			int currentClusterSize = clusters.get(currentCluster).size();
			if (currentClusterSize == 1) {
				i++;
				continue;
			}

			float removeCost = getAssignmentChangeCost(pattern, currentCluster,
					currentClusterSize, false);

			int bestMoveCluster = currentCluster;

			for (int j = 0; j < numClusters; j++) {
				if (j == currentCluster) {
					continue;
				}
				float addCost = getAssignmentChangeCost(pattern, j, clusters
						.get(j).size(), true);
				if (addCost < removeCost) {

					bestMoveCluster = j;
					break;
				}

			}
			if (bestMoveCluster != currentCluster) {
				assignmnetChanged = true;
				// reassign & update the centroids
				reassign(i, currentCluster, bestMoveCluster);
			}

			i++;
		}
		return assignmnetChanged;

	}

	private float getAssignmentChangeCost(T pattern, int clusterId,
			int clusterSize, boolean isAdd) {
		int div = (isAdd) ? (clusterSize + 1) : (clusterSize - 1);
		float cost = (float) clusterSize / div;
		return cost * utils.getDistanceSquare(pattern, centers.get(clusterId));
	}

	/**
	 * reassign the pattern & update the centroids
	 * 
	 * @param patternId
	 * @param fromCluster
	 * @param toCluster
	 */
	private void reassign(int patternId, int fromCluster, int toCluster) {

		Set<Integer> fromClusterSet = clusters.get(fromCluster);
		if (!fromClusterSet.remove(patternId)) {
			System.err.println("err DHB rmv" + patternId + " " + fromCluster);
		}
		Set<Integer> toClusterSet = clusters.get(toCluster);
		if (!toClusterSet.add(patternId)) {
			System.err.println("err DHB add" + patternId + " " + toCluster);
		}
		patternClusterMap.put(patternId, toCluster);

		centers.set(fromCluster,
				utils.getCenter(utils.getPatterns(fromClusterSet, patterns)));
		centers.set(toCluster,
				utils.getCenter(utils.getPatterns(toClusterSet, patterns)));
	}

}