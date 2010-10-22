package testing;

import java.io.FileNotFoundException;
import java.util.Arrays;
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

		List<FourDPoint> patterns = Arrays.asList(loader.getPatterns().toArray(
				new FourDPoint[] {}));

		int numClusters = loader.getNumOfClusters();

		ABFAndAFB<FourDPoint> abf = new ABFAndAFB<FourDPoint>(
				ABFAndAFB.Alternating.DHF, 1, 1);
		abf.setPatternUtils(new FourDPointUtils());
		List<Set<Integer>> rs = abf.cluster(patterns, numClusters);
		System.out.println("Obj: "+new FourDPointUtils().calculateObjectiveFunction(
				patterns, rs, abf.getCenters()));
		int k = 1;
		for (Set<Integer> cluster : rs) {
			System.out.println("---------------------------------------");
			for (int i : cluster) {
				System.out.print(i + ", ");
			}

			k++;
		}
	}

}
