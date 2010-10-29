package dataPrepration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.Pattern;
import utils.Point2D.Point2D;

public class GaussianLoader {

	private int numCluster = 2;
	private List<Pattern> patterns;
	private int numPatterns;

	public void load() throws FileNotFoundException {
		patterns = new ArrayList<Pattern>();
		Scanner sc = new Scanner(new File("data/gaussian2clusters.txt"));
		while (sc.hasNextInt()) {
			sc.nextInt();
			patterns.add(new Point2D(sc.nextFloat(), sc.nextFloat()));
		}

		sc.close();
		numPatterns = patterns.size();
	}

	public int getNumOfClusters() {
		return this.numCluster;
	}

	public List<Pattern> getPatterns() {
		return this.patterns;
	}

	public int getNumPatterns() {
		return this.numPatterns;
	}
}
