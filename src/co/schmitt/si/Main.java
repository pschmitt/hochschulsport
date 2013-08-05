package co.schmitt.si;

import co.schmitt.si.gui.MainGUI;
import co.schmitt.si.gui.TimeTableModel;
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
    private String mCurrentQuestion;
    private String mResponse;
    private List<String> mCurrentChoices;
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
        mGui.setQuestion(mCurrentQuestion);
        mQuery = "";
    }

    public MainGUI getGui() {
        return mGui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Ignore all but "answer" actions
        if (!actionEvent.getActionCommand().equals(MainGUI.ACTION_ANSWER))
            return;
        mResponse = mGui.getAnswer();
        mQuery += " " + mResponse;
        System.out.println(mCurrentQuestion + ": " + mResponse);
        proceedToNextQuestion();
    }

    private void proceedToNextQuestion() {
        if (mParser.hasNext(mResponse)) {
            mCurrentQuestion = mParser.getNextQuestion(mResponse);
            mCurrentChoices = mParser.getChoices();
            mGui.setChoices(mCurrentChoices);
            mGui.setQuestion(mCurrentQuestion);
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

        System.out.println("Query: " + mQuery);
        return sportList;
    }

    private List<TimeTableModel> getTimeTableData(List<Sport> sports) {
        return null;
    }
}
