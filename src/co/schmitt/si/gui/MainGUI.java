package co.schmitt.si.gui;

import co.schmitt.si.model.Sport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Sophia
 */
@SuppressWarnings("serial")
public class MainGUI extends JFrame {

    public static final String ACTION_ANSWER = "answer";
    public static final String ACTION_BACK = "back";
    public static final String ACTION_START_OVER = "startOver";
    private static final String CARD_ANSWER = "answercard";
    private static final String CARD_TIMETABLE = "timetablecard";
    private static final String LABEL_ANSWER = "Antworten";
    private static final String LABEL_START_OVER = "Noch mal";
    private static final String LABEL_BACK = "<< Zur\u00FCck";
    private static final String LABEL_TIMETABLE = "Ihr pers\u00f6nlicher Studenplan: ";
    private static final String LABEL_TITLE = "Hochschulsport";

    private JPanel cards;
    private JLabel questionLabel;
    private JComboBox<String> answerComboBox;
    private JButton answerButton;
    private JButton backButton;
    private JTable timeTable;

    public MainGUI() {
        setTitle(LABEL_TITLE);
        setMinimumSize(new Dimension(800, 350));
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

        // make and add questionLabel
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(50, 50, 10, 10);
        questionLabel = new JLabel();
        contentPanel.add(questionLabel, c);

        // make answerComboBox and add it to card
        answerComboBox = new JComboBox<>();
        JPanel card1 = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        card1.add(answerComboBox, c);

        timeTable = new TimeTable();
        timeTable.setModel(new TimeTableModel());
        timeTable.getTableHeader().setReorderingAllowed(false);
        timeTable.setRowSelectionAllowed(false);
        JScrollPane timePane = new JScrollPane(timeTable);

        // create panel that contains cards
        cards = new JPanel(new CardLayout());
        cards.add(card1, CARD_ANSWER);
        cards.add(timePane, CARD_TIMETABLE);

        // add cards to contentPanel
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 50, 50, 10);
        contentPanel.add(cards, c);

        // make answerButton
        answerButton = new JButton(LABEL_ANSWER);
        answerButton.setActionCommand(ACTION_ANSWER);

        // make backButton
        backButton = new JButton(LABEL_BACK);
        backButton.setActionCommand(ACTION_BACK);
        backButton.setVisible(false);

        // add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(answerButton);
        buttonPanel.add(backButton);

        // add panel to contentPanel
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 10, 50, 50);
        contentPanel.add(buttonPanel, c);

        add(contentPanel);
    }

    /**
     * Set the question Label
     *
     * @param question The new label
     */
    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    /**
     * Set the possible answers
     *
     * @param items The answers to be displayed
     */
    public void setChoices(List<co.schmitt.si.model.Choice> items) {
        answerComboBox.removeAllItems();
        for (co.schmitt.si.model.Choice item : items) {
            answerComboBox.addItem(item.getText());
        }
    }

    /**
     * Retrieve the answer the user gave
     *
     * @return The answer
     */
    public String getAnswer() {
        return (String) answerComboBox.getSelectedItem();
    }

    /**
     * Set the answer selected in the combo box
     *
     * @param answer The answer
     */
    public void setAnswer(String answer) {
        answerComboBox.setSelectedItem(answer);
    }

    /**
     * Set the currently displayed card item
     *
     * @param card The card item ID to display
     */
    private void setCard(String card) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, card);
    }

    /**
     * Show the timetable Should only be called when the scenario is over
     *
     * @param sports A list containing all sports to be displayed in the timetable
     */
    public void showTimeTable(List<Sport> sports) {
        // TODO: Parse and display sports !
        TimeTableModel model = (TimeTableModel) timeTable.getModel();
        model.add(sports);
        model.fireTableDataChanged();
        setCard(CARD_TIMETABLE);
        // answerButton.setVisible(false);
        answerButton.setText(LABEL_START_OVER);
        answerButton.setActionCommand(ACTION_START_OVER);
        setQuestion(LABEL_TIMETABLE);
    }

    /**
     * Shows the back button
     */
    public void showBackButton() {
        backButton.setVisible(true);
    }

    /**
     * Hides the back button
     */
    public void hideBackButton() {
        backButton.setVisible(false);
    }

    /**
     * Make an external class react on button press
     *
     * @param listener The listener class
     */
    public void registerAnswerListener(ActionListener listener) {
        answerButton.addActionListener(listener);
    }

    /**
     * Make an external class react on back button press
     *
     * @param listener The listener class
     */
    public void registerBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    /**
     * Reset the GUI
     */
    public void restart() {
        setCard(CARD_ANSWER);
        answerButton.setActionCommand(ACTION_ANSWER);
        answerButton.setText(LABEL_ANSWER);
    }
}
