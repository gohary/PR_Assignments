package kMeans;

import java.util.List;
import java.util.Set;

import utils.Pattern;
import utils.algorithms.KMeansUtils;

public class DHB<T extends Pattern> extends KMeansTypeAlgorithm<T> {

	public DHB(int numClusters, List<T> patterns, KMeansUtils<T> utils) {
		super(numClusters, patterns, utils);
	}

	public void cluster() {
		while (doDHBIteration()) {
		}
	}

	private boolean doDHBIteration() {
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
			float minCost = removeCost;

			int bestMoveCluster = currentCluster;

			for (int j = 0; j < numClusters; j++) {
				if (j == currentCluster) {
					continue;
				}
				float addCost = getAssignmentChangeCost(pattern, j, clusters
						.get(j).size(), true);
				if (addCost < minCost) {
					minCost = addCost;
					bestMoveCluster = j;
				}

			}
			if (minCost < removeCost) {
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
		return clusterSize
				* utils.getDistanceSquare(pattern, centers.get(clusterId))
				/ div;
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
			System.err.println("err");
		}
		Set<Integer> toClusterSet = clusters.get(toCluster);
		if (!toClusterSet.add(patternId)) {
			System.err.println("err");
		}
		patternClusterMap.put(patternId, toCluster);

		centers.set(fromCluster,
				utils.getCenter(utils.getPatterns(fromClusterSet, patterns)));
		centers.set(toCluster,
				utils.getCenter(utils.getPatterns(toClusterSet, patterns)));
	}

}
