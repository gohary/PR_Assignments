package fuzzy.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JTable;

import utils.fourDPoint.FourDPoint;
import dataPrepration.BritishTownsLoader;
import fuzzy.BezdekAlgorithm;
import fuzzy.soft.TWAlgorithm;
import fuzzy.ui.MainPanel;
import fuzzy.ui.OptionsPan;

public class PlotActionListener implements ActionListener {

	private OptionsPan optionsPan;
	private MainPanel charPainter;

	public void setOptionsPan(OptionsPan optionsPan) {
		this.optionsPan = optionsPan;
	}

	public void setCharPainter(MainPanel charPainter) {
		this.charPainter = charPainter;
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		String[] columnNames = { "wi1", "wi2", "wi3", "wi4" };
		float[][] ws = null;

		switch (optionsPan.getAlgorithm()) {
		case 1:
			// fuzzy
			BritishTownsLoader loader = new BritishTownsLoader();
			List<FourDPoint> patterns;
			try {
				patterns = loader.load();
				TWAlgorithm tw = new TWAlgorithm();
				tw.setPatterns(patterns);
				tw.setAlpha(optionsPan.getAlpha());
				tw.setM(optionsPan.getM());
				tw.setNumClusters(optionsPan.getNumClusters());
				tw.setEps(optionsPan.getEps());

				tw.startClustering();
				ws = tw.getMembershipMatrix();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			break;
		case 0:
			// soft
			loader = new BritishTownsLoader();
			try {
				patterns = loader.load();
				BezdekAlgorithm tw = new BezdekAlgorithm();
				tw.setPatterns(patterns);
				tw.setM(optionsPan.getM());
				tw.setNumClusters(optionsPan.getNumClusters());
				tw.setEps(optionsPan.getEps());
				tw.startClustering();
				ws = tw.getMembershipMatrix();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}

		Object[][] res = new Object[50][optionsPan.getNumClusters()];
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < optionsPan.getNumClusters(); j++) {
				res[i][j] = ws[i][j];
			}
		}
		charPainter.showResults(new JTable(res, columnNames));

	}

}
