package co.schmitt.si.parser;

import co.schmitt.si.Main;
import co.schmitt.si.model.Choice;
import co.schmitt.si.model.Question;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static final String SCENARIO_FILE = "Szenario_Struktur.xml";

    // XML Tags
    private static final String TAG_QUESTION_ID = "ID";
    private static final String TAG_QUESTION = "QUESTION";
    private static final String TAG_TEXT = "TEXT";
    private static final String TAG_TYPE = "TYPE";
    private static final String TAG_CHOICES = "CHOICES";
    private static final String TAG_NEXT_QUESTION_ID = "NEXT_QUESTION_ID";
    private static final String TAG_TOPIC = "TOPIC";
    private static final String ATTR_TYPE = "type";
    private static final String ATTR_TYPE_BOOL = "bool";

    private List<Element> questionsList;

    public Parser() {
        SAXBuilder builder = new SAXBuilder();

        try {
            System.out.println("CP: " + System.getProperty("java.class.path"));
//            System.out.println("URI: " + Parser.class.getResource(SCENARIO_FILE));
//            File xmlFile = new File(Parser.class.getResource("res" + File.pathSeparatorChar + SCENARIO_FILE).toURI());
            InputStream is = Parser.class.getClassLoader().getResourceAsStream(SCENARIO_FILE);
            Document document = builder.build(is);
            Element rootNode = document.getRootElement();
            questionsList = rootNode.getChildren(TAG_QUESTION);
        } catch ( NullPointerException uriexp) {
            uriexp.printStackTrace();
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
    }

    /**
     * Get the very first question. Should only be called once.
     *
     * @return The first question
     */
    public Question getFirstQuestion() {
        Element currentQuestion = questionsList
                .get(0);
        return getQuestion(currentQuestion);
    }

    /**
     * Retrieve the next question, depending on what the user answered
     *
     * @param question The question object holding the answer the user gave
     * @return The next question
     */
    public Question getNextQuestion(Question question) {
        Element currentQuestion = questionsList
                .get(question.getNextQuestionId() - 1);
        return getQuestion(currentQuestion);
    }

    /**
     * Parse a <QUESTION> element
     *
     * @param question The element to parse
     * @return A question object
     */
    private Question getQuestion(Element question) {
        Question q = new Question(question.getChildText(TAG_TEXT), getType(question), getQuestionId(question));
        if (isBooleanQuestion(question)) {
            q.setTopic(getTopic(question));
        }
        q.setChoices(getChoices(question));
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
     * Retrieve a question's ID (<ID>$ID</ID>)
     *
     * @param question The question
     * @return The ID of the question
     */
    private int getQuestionId(Element question) {
        return intToString(question.getChildText(TAG_QUESTION_ID));
    }

    /**
     * Retrieve a choice's next question ID (<NEXT_QUESTION_ID>$ID</NEXT_QUESTION_ID>)
     *
     * @param choice The choice element
     * @return The ID of the next question
     */
    private int getNextQuestionId(Element choice) {
        return intToString(choice.getChildText(TAG_NEXT_QUESTION_ID));
    }

    /**
     * Convert a string to an integer
     *
     * @param str The string to convert
     * @return An integer (-1 if str is null)
     */
    private int intToString(String str) {
        int i = -1;
        if (str != null) {
            i = Integer.parseInt(str);
        }
        return i;
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
    private List<Choice> getChoices(Element currentQuestion) {
        // List with CHOICES
        List<Element> choicesList = currentQuestion.getChildren(TAG_CHOICES);

        // CHOICES Element
        Element choicesListElement = choicesList.get(0);

        // CHOICE List
        List<Element> choices = choicesListElement.getChildren();
        List<Choice> choicesArrayList = new ArrayList<>();

        for (Element choiceElement : choices) {
            final String text = choiceElement.getChildText(TAG_TEXT);
            choicesArrayList.add(new Choice(text, getNextQuestionId(choiceElement)));
        }
        return choicesArrayList;
    }
}
