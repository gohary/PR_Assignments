package dataPrepration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.Pattern;
import utils.fourDPoint.FourDPoint;

public class IRISLoader {

	private int numCluster;
	private List<Pattern> patterns;
	private int numPatterns;

	public List<Pattern>  load() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("data/GermanTowns.in"));
		numCluster = sc.nextInt();
		numPatterns = sc.nextInt();
		patterns = new ArrayList<Pattern>();
		int patternsCount = 0;
		while (patternsCount < numPatterns) {
			patterns.add(new FourDPoint(sc.nextFloat(), sc.nextFloat(), sc
					.nextFloat(), sc.nextFloat()));
			patternsCount++;
		}
		sc.close();
		return patterns;

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
