package co.schmitt.si;

import co.schmitt.si.gui.MainGUI;
import co.schmitt.si.gui.TimeTableModel;
import co.schmitt.si.model.Question;
import co.schmitt.si.model.Sport;
import co.schmitt.si.ontology.OntologyProvider;
import co.schmitt.si.parser.Parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pschmitt
 * Date: 8/5/13
 * Time: 1:35 PM
 */
public class Main implements ActionListener {

    private Parser mParser;
    private Question mCurrentQuestion;
    private List<String> mCurrentChoices;
    private List<Question> mScenario;
    private String mQuery;
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
        mCurrentQuestion = mParser.getFirstQuestion();
        mCurrentChoices = mParser.getChoices();
        mGui = new MainGUI();
        mGui.registerAnswerListener(this);
        mGui.setQuestion(mCurrentQuestion.getQuestion());
        mQuery = "";
        mScenario = new ArrayList<Question>();
    }

    public MainGUI getGui() {
        return mGui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Ignore all but "answer" actions
        if (!actionEvent.getActionCommand().equals(MainGUI.ACTION_ANSWER))
            return;
        mCurrentQuestion.setAnswer(mGui.getAnswer());
        System.out.println(mCurrentQuestion);
        proceedToNextQuestion();
    }

    private void proceedToNextQuestion() {
        if (mParser.hasNext(mCurrentQuestion.getAnswer())) {
            mCurrentQuestion = mParser.getNextQuestion(mCurrentQuestion.getAnswer());
            mCurrentChoices = mParser.getChoices();
            mGui.setChoices(mCurrentChoices);
            mGui.setQuestion(mCurrentQuestion.getQuestion());
        } else {
            // Reached end of scenario
            // TODO get sport details and display timetable
            List<Sport> sports = queryOntology();
            List<TimeTableModel> timeTableData = getTimeTableData(sports);
            // mGui.showTimetable(timeTableData);
        }
    }

    private List<Sport> queryOntology() {
        List<Sport> sportList = new ArrayList<Sport>();
        OntologyProvider provider = OntologyProvider.getInstance();

        // TODO Parse answers and build query
//        String dlQuery = DLQueryBuilder.buildQuery();
        System.out.println("Query: " + mQuery);
        return sportList;
    }

    private List<TimeTableModel> getTimeTableData(List<Sport> sports) {
        return null;
    }
}
