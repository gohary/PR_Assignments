package dataPrepration;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.Point2D.Point2D;

public class GaussianLoader {

	private int numCluster = 2;
	private List<Point2D> patterns;
	private int numPatterns;

	public List<Point2D> load() throws FileNotFoundException {
		patterns = new ArrayList<Point2D>();
		Scanner sc = new Scanner(new File("dat/gaussianClose.txt"));
		while (sc.hasNextInt()) {
			sc.nextInt();
			patterns.add(new Point2D(sc.nextFloat(), sc.nextFloat()));
		}

		sc.close();
		numPatterns = patterns.size();
		return patterns;
	}

	public int getNumOfClusters() {
		return this.numCluster;
	}

	public List<Point2D> getPatterns() {
		return this.patterns;
	}

	public int getNumPatterns() {
		return this.numPatterns;
	}
}
