package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class PatternUtils<T extends Pattern> {

	/**
	 * Maybe overridden for efficient implementation
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public float getDistance(T p1, T p2) {
		return (float) Math.sqrt(getDistanceSquare(p1, p2));
	}

	public abstract float getDistanceSquare(T p1, T p2);

	public float getSimilarity(T p1, T p2) {
		return 1 - getDistance(p1, p2);
	}

	public List<T> getPatterns(Set<Integer> cluster, List<T> patterns) {
		List<T> patts = new ArrayList<T>();
		for (int i : cluster) {
			patts.add(patterns.get(i));
		}
		return patts;
	}

}
