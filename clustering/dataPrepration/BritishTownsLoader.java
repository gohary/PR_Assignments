package dataPrepration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.fourDPoint.FourDPoint;

/**
 * Loads the file data/BritishTowns.txt
 * 
 * @author Ahmed Elgohary
 * 
 */
public class BritishTownsLoader {

	private int numCluster;
	private List<FourDPoint> patterns;
	private int numPatterns;

	public void load() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("data/BritishTowns.txt"));
		numCluster = sc.nextInt();
		numPatterns = sc.nextInt();
		patterns = new ArrayList<FourDPoint>();
		int patternsCount = 0;
		while (patternsCount < numPatterns) {
			patterns.add(new FourDPoint(sc.nextFloat(), sc.nextFloat(), sc
					.nextFloat(), sc.nextFloat()));
			patternsCount++;
		}
		sc.close();
	}

	public int getNumOfClusters() {
		return this.numCluster;
	}

	public List<FourDPoint> getPatterns() {
		return this.patterns;
	}

	public int getNumPatterns() {
		return this.numPatterns;
	}
}
