package fuzzy.soft;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import dataPrepration.BritishTownsLoader;

import utils.fourDPoint.FourDPoint;

public class TWAlgorithm {

	private List<FourDPoint> patterns;
	private List<FourDPoint> centers;
	private int k;
	private float[][] membershipMatrix, distanceMatrix;

	private float alpha;

	private float tw, eps = 0.001f;

	private float m = 2;

	public void setPatterns(List<FourDPoint> patterns) {
		this.patterns = patterns;
	}

	public int getNumClusters() {
		return k;
	}

	public void setNumClusters(int numClusters) {
		this.k = numClusters;
	}

	public float[][] getMembershipMatrix() {
		return membershipMatrix;
	}

	public void setMembershipMatrix(float[][] membershipMatrix) {
		this.membershipMatrix = membershipMatrix;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setM(float m) {
		this.m = m;
	}

	public void setEps(float eps) {
		this.eps = eps;
	}

	public void startClustering() {
		init();
		List<FourDPoint> newCenters = null;
		boolean isFirst = true;
		do {
			if (isFirst) {
				isFirst = false;
			} else {
				centers = newCenters;
			}
			iterate();
			newCenters = calcCenters();
		} while (!isConverge(centers, newCenters));

	}

	private void init() {
		membershipMatrix = new float[patterns.size()][k];
		distanceMatrix = new float[patterns.size()][k];
		tw = alpha / k;
		// first K patterns as the centers
		centers = new ArrayList<FourDPoint>();
		for (int i = 0; i < k; i++) {
			centers.add(patterns.get(i));
		}
	}

	private void iterate() {
		calcDistanceMatrix();
		int numPatterns = patterns.size();
		for (int i = 0; i < numPatterns; i++) {
			boolean dis0Found = false;
			int j = 0;
			for (; j < k; j++) {
				if (distanceMatrix[i][j] == 0) {
					dis0Found = true;
					break;
				}
			}
			if (dis0Found) {
				membershipMatrix[i][j] = 1;
				for (int jj = 0; jj < k; jj++) {
					if (jj != j) {
						membershipMatrix[i][jj] = 0;
					}
				}
			} else {
				for (j = 0; j < k; j++) {
					float lamda = (float) Math.pow(distanceMatrix[i][j],
							1 / (1 - m));
					float tmp = 0;
					for (int jj = 0; jj < k; jj++) {
						tmp += (float) Math.pow(distanceMatrix[i][jj],
								1 / (1 - m));
					}
					lamda /= tmp;
					if (lamda < tw) {
						membershipMatrix[i][j] = 0;
					} else {
						membershipMatrix[i][j] = lamda;
					}
				}
			}

		}

		normalizeMemberships();
	}

	private void calcDistanceMatrix() {
		int numPatterns = patterns.size();
		for (int i = 0; i < numPatterns; i++) {
			for (int j = 0; j < k; j++) {
				distanceMatrix[i][j] = getDistanceSquare(patterns.get(i),
						centers.get(j));
			}
		}
	}

	private void normalizeMemberships() {
		int patternsNum = patterns.size();
		for (int i = 0; i < patternsNum; i++) {
			float semgaW = 0;
			for (int j = 0; j < k; j++) {
				semgaW += membershipMatrix[i][j];
			}

			for (int j = 0; j < k; j++) {
				membershipMatrix[i][j] /= semgaW;
			}
		}
	}

	private List<FourDPoint> calcCenters() {
		List<FourDPoint> newCenters = new ArrayList<FourDPoint>();
		// can do it faster
		for (int j = 0; j < k; j++) {
			float d1 = 0, d2 = 0, d3 = 0, d4 = 0;
			float segmaW = 0;
			int i = 0;
			for (FourDPoint p : patterns) {
				float memPwM = (float) Math.pow(membershipMatrix[i][j], m);
				d1 += p.getD1() * memPwM;
				d2 += p.getD2() * memPwM;
				d3 += p.getD3() * memPwM;
				d4 += p.getD4() * memPwM;

				segmaW += memPwM;
				i++;
			}
			newCenters.add(new FourDPoint(d1 / segmaW, d2 / segmaW,
					d3 / segmaW, d4 / segmaW));
		}
		return newCenters;

	}

	private boolean isConverge(List<FourDPoint> centers,
			List<FourDPoint> newCenters) {
		for (int j = 0; j < k; j++) {
			if (getDistanceSquare(centers.get(j), newCenters.get(j)) > eps) {
				return false;
			}
		}
		return true;
	}

	private float getDistanceSquare(FourDPoint p1, FourDPoint p2) {
		float distanceSquare = 0;
		float[] p1Ds = p1.toArray();
		float[] p2Ds = p2.toArray();
		for (int i = 0; i < 4; i++)
			distanceSquare += (p1Ds[i] - p2Ds[i]) * (p1Ds[i] - p2Ds[i]);

		return distanceSquare;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// Test
		BritishTownsLoader loader = new BritishTownsLoader();
		List<FourDPoint> patterns = loader.load();
		TWAlgorithm tw = new TWAlgorithm();
		tw.setPatterns(patterns);
		tw.setAlpha(0f);
		tw.setM(2);
		tw.setNumClusters(4);

		tw.startClustering();
		float[][] ws = tw.getMembershipMatrix();
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(ws[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
