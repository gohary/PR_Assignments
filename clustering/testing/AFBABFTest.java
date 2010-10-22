package testing;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import kMeans.ABFAndAFB;
import utils.fourDPoint.FourDPoint;
import utils.fourDPoint.FourDPointUtils;
import dataPrepration.BritishTownsLoader;

public class AFBABFTest {

	public static void main(String[] args) throws FileNotFoundException {

		BritishTownsLoader loader = new BritishTownsLoader();
		loader.load();

		List<FourDPoint> patterns = loader.getPatterns();

		int numClusters = loader.getNumOfClusters();

		ABFAndAFB<FourDPoint> abf = new ABFAndAFB<FourDPoint>(
				ABFAndAFB.Alternating.DHF);
		abf.setPatternUtils(new FourDPointUtils());
		List<Set<Integer>> rs = abf.cluster(patterns, numClusters);

		for (Set<Integer> cluster : rs) {
			System.out.println(cluster.size());
		}
	}

}
