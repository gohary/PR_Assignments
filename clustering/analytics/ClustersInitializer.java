package analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import utils.Pattern;

public class ClustersInitializer {

	private int numClusters;
	private Random randoms;

	private List<Set<Integer>> clusters;
	private Map<Integer, Integer> patternClusterMap;

	public ClustersInitializer(int numClusters) {
		this.numClusters = numClusters;
		randoms = new Random();
	}

	public void initClusters(List<? extends Pattern> patterns) {
		int numPatterns = patterns.size();
		clusters = new ArrayList<Set<Integer>>();
		for (int i = 0; i < numClusters; i++) {
			clusters.add(new HashSet<Integer>());
		}
		patternClusterMap = new HashMap<Integer, Integer>();

		for (int i = 0; i < numPatterns; i++) {
			int randCluster = randoms.nextInt(numClusters);
			clusters.get(randCluster).add(i);
			patternClusterMap.put(i, randCluster);
		}

		// Handle Empty Clusters
		int i = -1;
		for (Set<Integer> cluster : clusters) {
			i++;
			if (cluster.size() == 0) {
				/*
				 * selecting a random pattern and move it to this cluster and
				 * making sure that removing that pattern from its cluster never
				 * make its original cluster empty
				 */
				do {
					int randPattern = randoms.nextInt(numPatterns);
					int orignalCluster = patternClusterMap.get(randPattern);
					if (clusters.get(orignalCluster).size() == 1)
						continue;

					clusters.get(orignalCluster).remove(randPattern);
					cluster.add(randPattern);
					patternClusterMap.put(randPattern, i);
					break;
				} while (true);

			}

		}
	}

	public List<Set<Integer>> getClusters() {
		return clusters;
	}

	public Map<Integer, Integer> getPatternClusterMap() {
		return patternClusterMap;
	}

}