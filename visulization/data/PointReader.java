package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import data.Pattern;

public class PointReader {
	private int dimensions;
	
	public PointReader() {
		
	}

	public HashMap<String, List<Pattern>> read(String fileURL){
		HashMap<String, List<Pattern>> pointsMap = new HashMap<String, List<Pattern>>();
		File file = new File(fileURL);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
            	String line = scanner.nextLine();
                String[] tokens = line.split("\t");
                if(tokens.length == 0)
                	break;
                this.dimensions = tokens.length - 1;
                String classLabel = tokens[0];
                double[] vector = new double[dimensions];
                for(int i = 0; i < dimensions; i++){
                	vector[i] = Double.parseDouble(tokens[i + 1]);
                }
                Pattern pattern = new Pattern(vector, classLabel);
                if(!pointsMap.containsKey(classLabel))
                	pointsMap.put(classLabel, new ArrayList<Pattern>());
                pointsMap.get(classLabel).add(pattern);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		return pointsMap;

	}
	
	public int getDimensions() {
		return this.dimensions;
	}
}

