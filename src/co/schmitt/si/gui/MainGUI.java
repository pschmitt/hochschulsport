package co.schmitt.si.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sophia.
 */
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

    public MainGUI() {
        setTitle("Hochschulsport");
        setMinimumSize(new Dimension(800, 350));
        // setResizable(false);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        setQuestion("M�chtest du lieber Mannschaftssport oder Einzelsport machen?");
        contentPanel.add(questionLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 50, 50, 10);
        String[] answerPossibilities = {"Mannschaftssport", "Einzelsport"};
        answerComboBox = new JComboBox<String>(answerPossibilities);
        contentPanel.add(answerComboBox, c);

        // make and add button
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 10, 50, 50);
        answerButton = new JButton("Antworten");
        answerButton.setActionCommand("answer");
        answerButton.addActionListener(this);
        contentPanel.add(answerButton, c);

        // make and add table
        // should eventually be somewhere else later
        JTable timeTable = new TimeTable();
        timeTable.setModel(new TimeTableModel());
        timeTable.getTableHeader().setReorderingAllowed(false);
        timeTable.setRowSelectionAllowed(false);
        JScrollPane timePane = new JScrollPane(timeTable);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 50, 50, 50);
        contentPanel.add(timePane, c);

        add(contentPanel);
    }

    /**
     * Respond to button event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Do something
    }

    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    public void setAnswer(String[] items) {
        answerComboBox.removeAllItems();
        for (String item : items) {
            answerComboBox.addItem(item);
        }
    }

    public String getAnswer() {
        return (String) answerComboBox.getSelectedItem();
    }
}
