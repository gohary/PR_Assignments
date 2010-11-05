package fuzzy.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fuzzy.controller.PlotActionListener;

public class OptionsPan extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField numClustersF, alpha, m, eps;
	private JComboBox algorithm;
	private JButton runButton;

	public OptionsPan(PlotActionListener controller) {

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(new JLabel("Num Clusters"));
		numClustersF = new JTextField();
		numClustersF.setColumns(3);
		add(numClustersF);
		add(new JLabel("Alpha"));
		alpha = new JTextField("1", 3);
		alpha.setText("0.5");
		add(alpha);

		add(new JLabel("m"));
		m = new JTextField("1", 3);
		m.setText("2");
		add(m);
		setSize(650, 200);

		add(new JLabel("eps"));
		eps = new JTextField("30", 3);
		eps.setText("0.001");
		add(eps);

		add(new JLabel("Algorithm"));
		algorithm = new JComboBox(new String[] { "Fuzzy", "Soft - TW" });
		add(algorithm);
		runButton = new JButton("Run");
		add(runButton);
		runButton.addActionListener(controller);
		controller.setOptionsPan(this);
		this.setPreferredSize(new Dimension(850, 30));
	}

	public int getNumClusters() {
		return Integer.parseInt(this.numClustersF.getText());
	}

	public float getAlpha() {
		return Float.parseFloat(this.alpha.getText());
	}

	public int getM() {
		return Integer.parseInt(this.m.getText());
	}

	public int getAlgorithm() {
		return this.algorithm.getSelectedIndex();
	}

	public float getEps() {
		return Float.parseFloat(this.eps.getText());
	}
}
