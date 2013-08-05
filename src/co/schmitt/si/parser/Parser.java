package co.schmitt.si.parser;

import co.schmitt.si.model.Question;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {

    public static final String SCENARIO_FILE = "Szenario_Struktur.xml";

    // XML Tags
    public static final String TAG_QUESTION = "QUESTION";
    public static final String TAG_TEXT = "TEXT";
    public static final String TAG_TYPE = "TYPE";
    public static final String TAG_CHOICES = "CHOICES";
    public static final String TAG_NEXT_QUESTION_ID = "NEXT_QUESTION_ID";

    static int currentQuestionID = 0;
    static HashMap<String, Integer> NextQuestionID = new HashMap<String, Integer>();

    private List QuestionsList;

    public Parser() {

        SAXBuilder builder = new SAXBuilder();

        try {
            File xmlFile = new File(ClassLoader.getSystemClassLoader().getResource(SCENARIO_FILE).toURI());
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            QuestionsList = rootNode.getChildren(TAG_QUESTION);
        } catch (URISyntaxException uriexp) {
            uriexp.printStackTrace();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

    /**
     * Check whether we reached the end of our scenario
     *
     * @param answer The last answer the user gave
     * @return True if there are any questions left
     */
    public boolean hasNext(String answer) {
        return NextQuestionID.containsKey(answer);
    }

    public Question getNextQuestion(String answer) {
        currentQuestionID = NextQuestionID.get(answer);

        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);

        return new Question(currentQuestion.getChildText(TAG_TEXT), getType(currentQuestion));
    }

    public Question getFirstQuestion() {

        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);

        return new Question(currentQuestion.getChildText(TAG_TEXT), getType(currentQuestion));
    }

    /**
     * Determine the type of question
     *
     * @param question XMLElement holding question
     * @return QUESTION_TYPE.[ LOCATION | TEAM_SPORT | SPORT_CATEGORY ]
     */
    private Question.QUESTION_TYPE getType(Element question) {
        String typeString = question.getChildText(TAG_TYPE);
        Question.QUESTION_TYPE actualType = null;
        if (Question.QUESTION_TYPE.LOCATION.name().equals(typeString)) {
            actualType = Question.QUESTION_TYPE.LOCATION;
        } else if (Question.QUESTION_TYPE.TEAM_SPORT.name().equals(typeString)) {
            actualType = Question.QUESTION_TYPE.TEAM_SPORT;
        } else if (Question.QUESTION_TYPE.SPORT_CATEGORY.name().equals(typeString)) {
            actualType = Question.QUESTION_TYPE.SPORT_CATEGORY;
        }
        return actualType;
    }

    public List<String> getChoices() {

        // Current Question
        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);

        // List with CHOICES
        List choicesList = (List) currentQuestion.getChildren(TAG_CHOICES);

        // CHOICES Element
        Element choicesListElement = (Element) choicesList.get(0);

        // CHOICE List
        List choices = choicesListElement.getChildren();

        ArrayList<String> choicesArrayList = new ArrayList<String>();

        for (int i = 0; i < choices.size(); i++) {
            // CHOICE Element
            Element choiceElement = (Element) choices.get(i);

            String text = choiceElement.getChildText(TAG_TEXT);
            String next_question_id = choiceElement
                    .getChildText(TAG_NEXT_QUESTION_ID);

            choicesArrayList.add(text);
            if (next_question_id != null) {
                NextQuestionID.put(text, Integer.parseInt(next_question_id) - 1);
            }

        }

        return choicesArrayList;
    }
}
