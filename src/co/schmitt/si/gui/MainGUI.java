package co.schmitt.si.gui;

import co.schmitt.si.model.Sport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Sophia
 */
@SuppressWarnings("serial")
public class MainGUI extends JFrame implements ActionListener {

    public static final String ACTION_ANSWER = "answer";
    public static final String ANSWERCARD = "answercard";
    public static final String TIMETABLECARD = "timetablecard";
    // TODO DEBUG
    private static String actualCard;
    private JPanel cards;
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
        setQuestion("M\u00f6chtest du lieber Mannschaftssport oder Einzelsport machen?");
        contentPanel.add(questionLabel, c);

        // make answerComboBox and add it to card
        String[] answerPossibilities = {"Mannschaftssport", "Einzelsport"};
        answerComboBox = new JComboBox<String>(answerPossibilities);
        JPanel card1 = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        card1.add(answerComboBox, c);

        // make table and add it to card
        JTable timeTable = new TimeTable();
        timeTable.setModel(new TimeTableModel());
        timeTable.getTableHeader().setReorderingAllowed(false);
        timeTable.setRowSelectionAllowed(false);
        JScrollPane timePane = new JScrollPane(timeTable);

        // create panel that contains cards
        cards = new JPanel(new CardLayout());
        cards.add(card1, ANSWERCARD);
        cards.add(timePane, TIMETABLECARD);
        // TODO DEBUG
        // remember which card
        actualCard = ANSWERCARD;

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

        // make and add answerButton
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 10, 50, 50);
        answerButton = new JButton("Antworten");
        answerButton.setActionCommand(ACTION_ANSWER);
        contentPanel.add(answerButton, c);

        // TODO DEBUG
        // make and add test cards button
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 10, 10, 10);
        JButton testCardButton = new JButton("Teste Cards");
        testCardButton.setActionCommand("testCards");
        testCardButton.addActionListener(this);
        contentPanel.add(testCardButton, c);

        add(contentPanel);
    }

    /**
     * Respond to button event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("testCards")) {
            // TODO DEBUG
            // example on how to change cards
            CardLayout cl = (CardLayout) (cards.getLayout());
            if (actualCard.equals(ANSWERCARD)) {
                cl.show(cards, TIMETABLECARD);
                actualCard = TIMETABLECARD;
            } else {
                cl.show(cards, ANSWERCARD);
                actualCard = ANSWERCARD;
            }
        }
    }

    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    public void setChoices(List<String> items) {
        answerComboBox.removeAllItems();
        for (String item : items) {
            answerComboBox.addItem(item);
        }
    }

    public String getAnswer() {
        return (String) answerComboBox.getSelectedItem();
    }

    private void setCard(String card) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, card);
        // TODO DEBUG
        actualCard = card;
    }

    public void showTimeTable(List<Sport> sports) {
        // TODO: Parse and display sports !
        // TODO: Neu starten button ?
        setCard(TIMETABLECARD);
        answerButton.setVisible(false);
        setQuestion("Ihr pers\u00f6nlicher Studenplan: ");
    }

    /**
     * Allow an external class to react on button press
     *
     * @param listener The listener class
     */
    public void registerAnswerListener(ActionListener listener) {
        answerButton.addActionListener(listener);
    }
}
