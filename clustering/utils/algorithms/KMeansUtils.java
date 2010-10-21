package utils.algorithms;

import java.util.List;

import utils.Pattern;
import utils.PatternUtils;

public abstract class KMeansUtils<T extends Pattern> extends PatternUtils<T> {

	public abstract T getCenter(List<T> patterns);

	public float calculateObjectiveFunction(List<List<T>> clusters) {
		// TODO
		return 0;
	}

	public float calculateObjectiveFunction(List<List<T>> clusters,
			List<T> centers) {
		// TODO
		return 0;
	}
}
