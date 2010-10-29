package utils.Point2D;

import java.util.List;

import utils.algorithms.KMeansUtils;

public class Point2DUtils extends KMeansUtils<Point2D> {

	@Override
	public Point2D getCenter(List<Point2D> patterns) {
		double d1 = 0, d2 = 0;
		for (Point2D p : patterns) {
			d1 += p.getD1();
			d2 += p.getD2();
		}
		int len = patterns.size();
		return new Point2D(d1 / len, d2 / len);
	}

	@Override
	public float getDistanceSquare(Point2D p1, Point2D p2) {

		return (float) ((p1.getD1() - p2.getD1()) * (p1.getD1() - p2.getD1()) + (p1
				.getD2() - p2.getD2()) * (p1.getD2() - p2.getD2()));
	}

	public static void main(String[] args) {
		// 0.2338053286075592 0.8840576410293579

		// 0.4141057253422971 0.9138093028573718

		// 0.19676387643953402 0.6932408662028572

		System.out
				.println((float) ((0.2338053286075592 - 0.4141057253422971)
						* (0.2338053286075592 - 0.4141057253422971) + (0.8840576410293579 - 0.9138093028573718)
						* (0.8840576410293579 - 0.9138093028573718)));
		

		System.out
				.println((float) ((0.2338053286075592 - 0.19676387643953402)
						* (0.2338053286075592 - 0.19676387643953402) + (0.8840576410293579 - 0.6932408662028572)
						* (0.8840576410293579 - 0.6932408662028572)));
	}
}
