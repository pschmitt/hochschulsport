package co.schmitt.si;

import co.schmitt.si.gui.MainGUI;
import co.schmitt.si.model.Question;
import co.schmitt.si.model.Sport;
import co.schmitt.si.ontology.DLQueryBuilder;
import co.schmitt.si.ontology.OntologyProvider;
import co.schmitt.si.parser.Parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * User: pschmitt
 * Date: 8/5/13
 * Time: 1:35 PM
 */
public class Main implements ActionListener {

    private Parser mParser;
    private Question mCurrentQuestion;
    private List<Question> mScenario;
    private MainGUI mGui;

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

    public Main() {
        mParser = new Parser();
        mScenario = new ArrayList<Question>();
        mCurrentQuestion = mParser.getFirstQuestion();
        mGui = new MainGUI();
        mGui.registerAnswerListener(this);
        updateGui();
    }

    public MainGUI getGui() {
        return mGui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Ignore all but "answer" actions
        String actionCmd = actionEvent.getActionCommand();
        if (actionCmd.equals(MainGUI.ACTION_ANSWER)) {
            mCurrentQuestion.setAnswer(mGui.getAnswer());
            mScenario.add(mCurrentQuestion);
            System.out.println(mCurrentQuestion);
            proceedToNextQuestion();
        } else if (actionCmd.equals(MainGUI.ACTION_START_OVER)) {
            mScenario.clear();
            mCurrentQuestion = mParser.getFirstQuestion();
            mGui.restart();
            updateGui();
        }
    }

    /**
     * Check whether we reached the end of the scenario and update the GUI accordingly
     */
    private void proceedToNextQuestion() {
        if (mParser.hasNext(mCurrentQuestion.getAnswer())) {
            mCurrentQuestion = mParser.getNextQuestion(mCurrentQuestion.getAnswer());
            updateGui();
        } else {
            // Reached end of scenario
            displayResults();
        }
    }

    /**
     * Retrieve the corresponding sports from Ontology, search additionnal info in DB and display
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
        List<Sport> timeTableData = getTimeTableData(sports);
        // mGui.showTimetable(timeTableData);
        mGui.showTimeTable(timeTableData);
    }

    /**
     * Update the user interface: display current question and possible answers
     */
    private void updateGui() {
        mGui.setQuestion(mCurrentQuestion.getQuestion());
        mGui.setChoices(mParser.getChoices());
    }

    /**
     * Get all sports the user may be interested in
     *
     * @return A list holding all results
     */
    private List<Sport> queryOntology() {
        OntologyProvider provider = OntologyProvider.getInstance();
        String dlQuery = DLQueryBuilder.buildQuery(mScenario);
        System.err.println(dlQuery);
        return provider.SportsByDlQuery(dlQuery);
    }

    /**
     * Get the event data to be displayed in the timetable
     *
     * @param sports A list containing all sports that we want to display in the timetable
     * @return The event data
     */
    private List<Sport> getTimeTableData(List<Sport> sports) {
        List<Sport> sportsWithTimetableData = new ArrayList<Sport>();
        for (Sport s : sports) {
            // TODO: do something clever !
//            DBProvider.getExtraFuckingInfo();
            sportsWithTimetableData.add(s);
        }
        return sports;
    }
}
