package co.schmitt.si.parser;

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

    public static final String SZENARIO_FILE = "Szenario_Struktur.xml";
    static int currentQuestionID = 0;
    static HashMap<String, Integer> NextQuestionID = new HashMap<String, Integer>();

    private List QuestionsList;

    public Parser() {

        SAXBuilder builder = new SAXBuilder();

        try {
            File xmlFile = new File(ClassLoader.getSystemClassLoader().getResource(SZENARIO_FILE).toURI());
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            QuestionsList = rootNode.getChildren("QUESTION");
        } catch (URISyntaxException uriexp) {
            uriexp.printStackTrace();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

    public String getNextQuestion(String answer) {
        currentQuestionID = NextQuestionID.get(answer);

        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);

        return currentQuestion.getChildText("TEXT");
    }

    public String getFirstQuestion() {

        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);

        return currentQuestion.getChildText("TEXT");
    }

    public List<String> getChoices() {

        // Current Question
        Element currentQuestion = (Element) QuestionsList
                .get(currentQuestionID);

        // List with CHOICES
        List choicesList = (List) currentQuestion.getChildren("CHOICES");

        // CHOICES Element
        Element choicesListElement = (Element) choicesList.get(0);

        // CHOICE List
        List choices = choicesListElement.getChildren();

        ArrayList<String> choicesArrayList = new ArrayList<String>();

        for (int i = 0; i < choices.size(); i++) {
            // CHOICE Element
            Element choiceElement = (Element) choices.get(i);

            String text = choiceElement.getChildText("TEXT");
            String next_question_id = choiceElement
                    .getChildText("NEXT_QUESTION_ID");

            choicesArrayList.add(text);
            if (next_question_id != null) {
                NextQuestionID.put(text, Integer.parseInt(next_question_id) - 1);
            }

        }

        return choicesArrayList;
    }
}
