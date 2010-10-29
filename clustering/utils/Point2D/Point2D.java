package utils.Point2D;

import utils.Pattern;

public class Point2D extends Pattern {

	private double d1, d2;

	public Point2D(double d1, double d2) {
		this.d1 = d1;
		this.d2 = d2;
	}

	public double getD1() {
		return d1;
	}

	public void setD1(double d1) {
		this.d1 = d1;
	}

	public double getD2() {
		return d2;
	}

	public void setD2(double d2) {
		this.d2 = d2;
	}

	@Override
	public String toString() {
		return d1 + " " + d2;
	}
}
