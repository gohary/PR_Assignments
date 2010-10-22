package ui;

import java.util.List;

import javax.swing.JPanel;

public class PaintingPanel extends JPanel {

	private List<Float> kmeansObjs;
	private List<Float> AFBObjs;
	private List<Float> ABFObjs;

	public PaintingPanel() {

	}

	/**
	 * 
	 * @param patterns
	 * @param clusters
	 *            if null, just show the patterns
	 */
	public void paint(List<Float> kmeansObjs, List<Float> AFBObjs,
			List<Float> ABFObjs) {
		this.kmeansObjs = kmeansObjs;
		this.ABFObjs = AFBObjs;
		this.ABFObjs = ABFObjs;
		this.repaint();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
