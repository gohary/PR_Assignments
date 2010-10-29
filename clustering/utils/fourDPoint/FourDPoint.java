package utils.fourDPoint;

import utils.Pattern;

public class FourDPoint extends Pattern {

	private float d1, d2, d3, d4;

	public FourDPoint() {

	}

	public FourDPoint(float d1, float d2, float d3, float d4) {
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
		this.d4 = d4;
	}

	public float getD1() {
		return d1;
	}

	public void setD1(float d1) {
		this.d1 = d1;
	}

	public float getD2() {
		return d2;
	}

	public void setD2(float d2) {
		this.d2 = d2;
	}

	public float getD3() {
		return d3;
	}

	public void setD3(float d3) {
		this.d3 = d3;
	}

	public float getD4() {
		return d4;
	}

	public void setD4(float d4) {
		this.d4 = d4;
	}

	public float[] toArray() {
		return new float[] { d1, d2, d3, d4 };
	}

	public void setArray(float[] ds) {
		d1 = ds[0];
		d2 = ds[1];
		d3 = ds[2];
		d4 = ds[3];
	}

}
