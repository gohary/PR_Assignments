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

public class ABFAndAFB<T extends Pattern> {

	public enum Alternating {
		DHF, DHB;
	}

	private KMeansUtils<T> utils;
	private List<T> patterns;
	private List<Set<Integer>> clusters;
	private int numClusters;

	// maintained in a list to be able to use a generic type T
	private List<T> centers;
	private int numPatterns;
	private Map<Integer, Integer> patternClusterMap;

	private Alternating firstTechnique;
	private int DHFRuns, DHBRuns;
	/**
	 * holds the sub of the DHF & DHB frequencies
	 */
	private int subRunSize;

	/**
	 * 
	 * @param first
	 *            if DHF/DHB is first, the class is going to be implementing
	 *            AFB/ABF.<br/>
	 *            By default, each technique DHF/DHB is going to be run once a
	 *            time.
	 * 
	 */
	public ABFAndAFB(Alternating first) {
		this(first, 1, 1);
	}

	/**
	 * 
	 * @param first
	 * @param DHFRuns
	 *            if set to zero, the algorithm is going to be pure DHB
	 * @param DHBRuns
	 *            if set to zero, the algorithm is going to be pure DHF
	 */
	public ABFAndAFB(Alternating first, int DHFRuns, int DHBRuns) {
		this.firstTechnique = first;
		this.DHBRuns = DHBRuns;
		this.DHFRuns = DHFRuns;
		subRunSize = DHBRuns + DHFRuns;
	}

	public void setPatternUtils(KMeansUtils<T> utils) {
		this.utils = utils;
	}

	public List<Set<Integer>> cluster(List<T> patterns, int numClusters) {
		this.patterns = patterns;
		this.numClusters = numClusters;
		numPatterns = patterns.size();
		initClusters();

		int iterationNumber = 0;
		do {
		} while (iterate(++iterationNumber));

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
		assignInitialPatternsToClusters();
	}

	private void assignInitialPatternsToClusters() {
		int i = 0;
		for (T pattern : patterns) {
			int nearestCenter = getNearestCenter(pattern);
			clusters.get(nearestCenter).add(i);
			patternClusterMap.put(i, nearestCenter);
			i++;
		}

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
		//System.out.println("FFFFFFF");
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
		//System.out.println("BBBBBBBBBBBBB");
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
		fromClusterSet.remove(patternId);
		Set<Integer> toClusterSet = clusters.get(toCluster);
		toClusterSet.add(patternId);
		patternClusterMap.put(patternId, toCluster);

		centers.set(fromCluster, utils.getCenter(getPatterns(fromClusterSet)));
		centers.set(toCluster, utils.getCenter(getPatterns(toClusterSet)));
	}

	private List<T> getPatterns(Set<Integer> cluster) {
		List<T> patts = new ArrayList<T>();
		for (int i : cluster) {
			patts.add(patterns.get(i));
		}
		return patts;
	}

	public List<T> getCenters() {
		return this.centers;
	}

}
