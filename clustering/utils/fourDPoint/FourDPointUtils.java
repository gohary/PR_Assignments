package utils.fourDPoint;

import java.util.List;

import utils.algorithms.KMeansUtils;

public class FourDPointUtils extends KMeansUtils<FourDPoint> {

	@Override
	public FourDPoint getCenter(List<FourDPoint> patterns) {
		float[] centerDs = new float[4];
		for (FourDPoint p : patterns) {
			centerDs[0] += p.getD1();
			centerDs[1] += p.getD2();
			centerDs[2] += p.getD3();
			centerDs[3] += p.getD4();
		}
		int length = patterns.size();
		return new FourDPoint(centerDs[0] / length, centerDs[1] / length,
				centerDs[2] / length, centerDs[3] / length);
	}

	@Override
	/**
	 * Euclidean distance
	 */
	public float getDistanceSquare(FourDPoint p1, FourDPoint p2) {
		float distanceSquare = 0;
		float[] p1Ds = p1.toArray();
		float[] p2Ds = p1.toArray();
		for (int i = 0; i < 4; i++)
			distanceSquare += (p1Ds[i] - p2Ds[i]) * (p1Ds[i] - p2Ds[i]);
		return distanceSquare;
	}

}
