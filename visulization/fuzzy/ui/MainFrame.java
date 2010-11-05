package fuzzy.ui;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

public class MainFrame extends JFrame {

	public MainFrame() {
		this.setSize(850, 600);
		setTitle("Fuzzy Clustering Assignment");
		setContentPane(new MainPanel(this));

		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
		
	}
	

	public static void main(String[] args) {
		new MainFrame();
	}

	private static final long serialVersionUID = 1L;

}
