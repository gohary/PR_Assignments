package kMeans;

import java.util.List;
import java.util.Set;

import utils.Pattern;
import utils.algorithms.KMeansUtils;

public class ABFAndAFB<T extends Pattern> extends KMeansTypeAlgorithm<T> {

	public enum Alternating {
		DHF, DHB;
	}

	private Alternating firstTechnique;
	private int DHFRuns, DHBRuns;
	/**
	 * holds the sub of the DHF & DHB frequencies
	 */
	private int subRunSize;

	/**
	 * 
	 * @param first
	 * @param DHFRuns
	 *            if set to zero, the algorithm is going to be pure DHB
	 * @param DHBRuns
	 *            if set to zero, the algorithm is going to be pure DHF
	 */

	public ABFAndAFB(Alternating first, int DHFRuns, int DHBRuns,
			int numClusters, List<T> patterns, KMeansUtils<T> utils) {
		super(numClusters, patterns, utils);
		this.firstTechnique = first;
		this.DHBRuns = DHBRuns;
		this.DHFRuns = DHFRuns;
		subRunSize = DHBRuns + DHFRuns;

	}

	public void cluster() {
		int iterationNumber = 0;
		do {
		} while (iterate(++iterationNumber));

	}

	/**
	 * 
	 * @param iterationNumber
	 * @return true if the assignment changed
	 */
	public boolean iterate(int iterationNumber) {
		int subRunId = iterationNumber % (subRunSize);
		if (subRunId == 0)
			subRunId = subRunSize;
		int firstRuns = (firstTechnique == Alternating.DHB) ? DHBRuns : DHFRuns;
		if (subRunId <= firstRuns) {
			// run first
			switch (firstTechnique) {
			case DHB:
				return doDHBIteration();
			case DHF:
				return doDHFIteration();

			}
		} else {
			// run second
			switch (firstTechnique) {
			case DHF:
				return doDHBIteration();
			case DHB:
				return doDHFIteration();
			}
		}

		return false;
	}

	private boolean doDHFIteration() {
		// System.out.println("FFFFFFF");
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

	private boolean doDHBIteration() {
		// System.out.println("BBBBBBBBBBBBB");
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
			//System.err.println("err");
		}
		Set<Integer> toClusterSet = clusters.get(toCluster);
		if (!toClusterSet.add(patternId)) {
		}
		patternClusterMap.put(patternId, toCluster);

		centers.set(fromCluster,
				utils.getCenter(utils.getPatterns(fromClusterSet, patterns)));
		centers.set(toCluster,
				utils.getCenter(utils.getPatterns(toClusterSet, patterns)));
	}

}
