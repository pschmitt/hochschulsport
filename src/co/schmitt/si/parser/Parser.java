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
    public static final String TAG_TOPIC = "TOPIC";
    public static final String ATTR_TYPE = "type";
    public static final String ATTR_TYPE_BOOL = "bool";

    static int currentQuestionID = 0;
    static HashMap<String, Integer> NextQuestionID = new HashMap<String, Integer>();

    private List<Element> QuestionsList;

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

    /**
     * Retrieve the next question, depending on what the user answered
     *
     * @param answer The answer the user gave to the current question
     * @return The next question
     */
    public Question getNextQuestion(String answer) {
        if (NextQuestionID.containsKey(answer))
            currentQuestionID = NextQuestionID.get(answer);

        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);
        Question q = new Question(currentQuestion.getChildText(TAG_TEXT), getType(currentQuestion));
        if (isBooleanQuestion(currentQuestion)) {
            q.setTopic(getTopic(currentQuestion));
        }
        return q;
    }

    /**
     * Check if a question is boolean (yes|no question)
     *
     * @param question The question
     * @return True if parameter is a boolean question
     */
    private boolean isBooleanQuestion(Element question) {
        return (question.hasAttributes() && question.getAttributeValue(ATTR_TYPE).equals(ATTR_TYPE_BOOL));
    }

    /**
     * Get the very first question. Should only be called once.
     *
     * @return The first question
     */
    public Question getFirstQuestion() {
        currentQuestionID = 0;
        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);
        Question q = new Question(currentQuestion.getChildText(TAG_TEXT), getType(currentQuestion));
        if (isBooleanQuestion(currentQuestion)) {
            q.setTopic(getTopic(currentQuestion));
        }
        return q;
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

    /**
     * Retrieve a boolean question's topic (<TOPIC>$TOPIC</TOPIC>)
     *
     * @param question The question
     * @return The topic of the question
     */
    private String getTopic(Element question) {
        return question.getChildText(TAG_TOPIC);
    }

    /**
     * Retrieve all possible answers to the current question
     *
     * @return A list with all possible answers
     */
    public List<String> getChoices() {

        // Current Question
        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);

        // List with CHOICES
        List<Element> choicesList = (List<Element>) currentQuestion.getChildren(TAG_CHOICES);

        // CHOICES Element
        Element choicesListElement = (Element) choicesList.get(0);

        // CHOICE List
        List<Element> choices = choicesListElement.getChildren();

        ArrayList<String> choicesArrayList = new ArrayList<String>();

        for (int i = 0; i < choices.size(); i++) {
            // CHOICE Element
            Element choiceElement = (Element) choices.get(i);

            String text = choiceElement.getChildText(TAG_TEXT);
            String nextQuestionId = choiceElement
                    .getChildText(TAG_NEXT_QUESTION_ID);

            choicesArrayList.add(text);
            if (nextQuestionId != null) {
                NextQuestionID.put(text, Integer.parseInt(nextQuestionId) - 1);
            }

        }

        return choicesArrayList;
    }
}
