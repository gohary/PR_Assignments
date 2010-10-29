package testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tests {

	public static void main(String[] args) {
		List<Integer> l = new ArrayList<Integer>();
		l.add(0);
		l.add(1);
		l.add(2);
		l.add(3);
		System.out.println(l);
		l = Arrays.asList(l.toArray(new Integer[] {}));
		System.out.println(l);
		
		int x= 1;
		x += (5-3) * (5-3);
		System.out.println(x);
	}
}
