package co.schmitt.si.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class MainGUI extends JFrame implements ActionListener {

	private JLabel questionLabel;
	private JComboBox<String> answerComboBox;
	private JButton answerButton;

	/**
	 * Starts the construction of the main frame.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		final JFrame frame = new MainGUI();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}

	private MainGUI() {
		setTitle("Hochschulsport");
		setMinimumSize(new Dimension(650, 200));
		setResizable(false);
		setLocationByPlatform(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			System.err.println("Failure in setting the System Look & Feel!");
		}
		createContent();
		pack();
	}

	/**
	 * Creates the Content.
	 */
	private void createContent() {
		JPanel contentPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c;

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50, 50, 10, 10);
		questionLabel = new JLabel();
        setQuestionLabel("Möchtest du lieber Mannschaftssport oder Einzelsport machen?"); // später
        // über
        // set-Methode
		contentPanel.add(questionLabel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 50, 50, 10);
		String[] answerPossibilities = { "Mannschaftssport", "Einzelsport" };
		answerComboBox = new JComboBox<String>(answerPossibilities);
		contentPanel.add(answerComboBox, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(10, 10, 50, 50);
		answerButton = new JButton("Antworten");
		answerButton.setActionCommand("answer");
		answerButton.addActionListener(this);
		contentPanel.add(answerButton, c);

		add(contentPanel);
	}

	/**
	 * Respond to button event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("answer")) {
			String [] items = {"Hi", "Kidz!"};
			setAnswerComBobox(items);
			setQuestionLabel("Neue Frage");
		}
	}

	public void setQuestionLabel(String question) {
		questionLabel.setText(question);
	}

	public void setAnswerComBobox(String [] items) {
		answerComboBox.removeAllItems();
		for (String item : items) {
			answerComboBox.addItem(item);
		}
	}
}
