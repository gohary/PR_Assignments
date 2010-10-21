package utils;

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

}
