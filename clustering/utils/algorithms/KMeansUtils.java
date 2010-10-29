package utils.algorithms;

import java.util.List;
import java.util.Set;

import utils.Pattern;
import utils.PatternUtils;

public abstract class KMeansUtils<T extends Pattern> extends PatternUtils<T> {

	public abstract T getCenter(List<T> patterns);

	public float calculateObjectiveFunction(List<T> patterns,
			List<Set<Integer>> clusters, List<T> centers) {
		float objectiveFunction = 0;
		int i = 0;
		for (T center : centers) {
			Set<Integer> clusterPatterns = clusters.get(i);
			for (int p : clusterPatterns) {
				objectiveFunction += getDistanceSquare(patterns.get(p), center);
			}
			i++;
		}
		return objectiveFunction;
	}

}
