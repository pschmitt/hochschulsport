package co.schmitt.si;

import co.schmitt.si.db.DBProvider;
import co.schmitt.si.gui.MainGUI;
import co.schmitt.si.model.DLQuery;
import co.schmitt.si.model.Question;
import co.schmitt.si.model.Sport;
import co.schmitt.si.ontology.DLQueryBuilder;
import co.schmitt.si.ontology.OntologyProvider;
import co.schmitt.si.parser.Parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;

/**
 * User: pschmitt
 * Date: 8/5/13
 * Time: 1:35 PM
 */
public class Main implements ActionListener {

    private Parser mParser;
    private Question mCurrentQuestion;
    private Stack<Question> mScenario;
    private MainGUI mGui;

    /**
     * Constructor - Initializes the GUI
     */
    public Main() {
        mParser = new Parser();
        mScenario = new Stack<>();
        mCurrentQuestion = mParser.getFirstQuestion();
        mGui = new MainGUI();
        mGui.registerAnswerListener(this);
        mGui.registerBackButtonListener(this);
        updateGui();
    }

    /**
     * Starts the construction of the main frame.
     *
     * @param args
     */
    public static void main(String args[]) {
        final Main main = new Main();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                main.getGui().setVisible(true);
            }
        });
    }

    /**
     * Retrieve the GUI frame
     *
     * @return The GUI frame
     */
    private MainGUI getGui() {
        return mGui;
    }

    /**
     * React to [ answer | restart | back ] button press
     *
     * @param actionEvent The action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Ignore all but "answer" actions
        String actionCmd = actionEvent.getActionCommand();
        switch (actionCmd) {
            case MainGUI.ACTION_ANSWER:
                mCurrentQuestion.setAnswer(mGui.getAnswer());
                mScenario.add(mCurrentQuestion);
                System.out.println(mCurrentQuestion);
                proceedToNextQuestion();
                break;
            case MainGUI.ACTION_START_OVER:
                mScenario.clear();
                mCurrentQuestion = mParser.getFirstQuestion();
                mGui.restart();
                updateGui();
                break;
            case MainGUI.ACTION_BACK:
                // TODO DEbug
                mCurrentQuestion = mScenario.pop();
                if (mScenario.isEmpty()) {
                    mCurrentQuestion = mParser.getFirstQuestion();
                }
                rollbackGui();
                break;
        }
    }

    /**
     * Check whether we reached the end of the scenario and update the GUI accordingly
     */
    private void proceedToNextQuestion() {
        if (mCurrentQuestion.hasNext()) {
            mCurrentQuestion = mParser.getNextQuestion(mCurrentQuestion);
            updateGui();
        } else {
            // Reached end of scenario
            mGui.hideBackButton();
            displayResults();
        }
    }

    /**
     * Retrieve the corresponding sports from Ontology, search additional info in DB and display
     */
    private void displayResults() {
        // TODO get sport details and display timetable
        List<Sport> sports = queryOntology();
        // Print to console
        if (sports.isEmpty()) {
            System.err.println("No sport matched !");
        }
        for (Sport s : sports) {
            System.out.println("Matched: " + s.getName());
        }
        if (sports == null || sports.size() < 1) {
            // No matching sports returned
            mGui.showNoSportMatchedPopup();
            mGui.showBackButton();
        } else {
            List<Sport> timeTableData = getTimeTableData(sports);
            for (Sport s : timeTableData) {
                System.out.println(s.dump());
            }
            mGui.showTimeTable(timeTableData);
        }
    }

    /**
     * Update the user interface: display current question and possible answers
     */
    private void updateGui() {
        if (mCurrentQuestion.isFirstQuestion()) {
            mGui.hideBackButton();
        }
        mGui.setQuestion(mCurrentQuestion.getQuestion());
        mGui.setChoices(mCurrentQuestion.getChoices());
        if (mScenario.size() > 0) {
            mGui.showBackButton();
        }
    }

    /**
     * Reset the GUI: display last question (and discard last answer)
     */
    private void rollbackGui() {
        if (mCurrentQuestion.isFirstQuestion()) {
            mGui.hideBackButton();
        }
        updateGui();
        if (mCurrentQuestion.getAnswer() != null) {
            mGui.setAnswer(mCurrentQuestion.getAnswer());
        }
    }

    /**
     * Get all sports the user may be interested in
     *
     * @return A list holding all results
     */
    private List<Sport> queryOntology() {
        OntologyProvider provider = OntologyProvider.getInstance();
        DLQuery dlQuery = DLQueryBuilder.buildQuery(mScenario);
        System.err.println("DL: " + dlQuery.getQuery());
        System.err.println("NOT-DL: " + dlQuery.getNotQuery());
        return provider.sportsByDlQueryUnion(dlQuery);
    }

    /**
     * Get the event data to be displayed in the timetable
     *
     * @param sports A list containing all sports that we want to display in the timetable
     * @return The event data
     */
    private List<Sport> getTimeTableData(List<Sport> sports) {
        //        DBProvider.LEGACY = true;
        return DBProvider.getTimetableData(sports);
    }
}
