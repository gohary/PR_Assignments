package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.PlotActionListener;

public class OptionsPan extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField numClustersF, DHBRunsF, DHFRunsF, numberOfRunsF;
	private JComboBox dataSetComboF;
	private JButton runButton;

	public OptionsPan(PlotActionListener controller) {

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(new JLabel("Num Clusters"));
		numClustersF = new JTextField();
		numClustersF.setColumns(3);
		add(numClustersF);
		add(new JLabel("DHB Runs"));
		DHBRunsF = new JTextField("1", 3);
		add(DHBRunsF);

		add(new JLabel("DHF Runs"));
		DHFRunsF = new JTextField("1", 3);
		add(DHFRunsF);
		setSize(650, 200);

		add(new JLabel("Number Of Runs"));
		numberOfRunsF = new JTextField("30", 3);
		add(numberOfRunsF);

		add(new JLabel("DataSet"));
		dataSetComboF = new JComboBox(new String[] { "--", "British Towns",
				"German Towns", "IRIS" });
		add(dataSetComboF);
		runButton = new JButton("Run");
		add(runButton);
		runButton.addActionListener(controller);
		controller.setOptionsPan(this);
		this.setPreferredSize(new Dimension(850, 30));
	}

	public int getNumClusters() {
		return Integer.parseInt(this.numClustersF.getText());
	}

	public int getDHBRuns() {
		return Integer.parseInt(this.DHBRunsF.getText());
	}

	public int getDHFRuns() {
		return Integer.parseInt(this.DHFRunsF.getText());
	}

	/**
	 * 
	 * @return 0 none, 1 British Towns, 2 German Towns
	 */
	public int getDataSet() {
		return this.dataSetComboF.getSelectedIndex();
	}

	public int getNumRuns() {
		return Integer.parseInt(this.numberOfRunsF.getText());
	}
}
