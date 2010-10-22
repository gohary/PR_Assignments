package dataPrepration;

import java.io.FileNotFoundException;
import java.util.List;

import utils.Pattern;

public class GermanTownsLoader {

	private int numCluster;
	private List<Pattern> patterns;
	private int numPatterns;

	public void load() throws FileNotFoundException {
		// TODO
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
