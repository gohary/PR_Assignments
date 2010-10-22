package ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionsPan extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField numClustersF, DHBRunsF, DHFRunsF, numberOfRunsF;
	private JComboBox dataSetComboF;
	private JButton runButton;

	public OptionsPan() {

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
				"German Towns" });
		add(dataSetComboF);
		runButton = new JButton("Run");
		add(runButton);
	}
}
