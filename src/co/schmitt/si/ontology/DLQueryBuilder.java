package co.schmitt.si.ontology;

import co.schmitt.si.model.DLQuery;
import co.schmitt.si.model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pschmitt
 * Date: 8/5/13
 * Time: 3:04 PM
 */
public class DLQueryBuilder {
    @SuppressWarnings("serial")
    private static final Map<String, Boolean> BOOLEAN_ANSWER_MAP = new HashMap<String, Boolean>() {{
        put("ja", true);
        put("nein", false);
        put("Ja", true);
        put("Nein", false);
        put("yes", true);
        put("no", false);
        put("Yes", true);
        put("No", false);
    }};
    private static final String CONNECTOR_AND = " and ";

    private DLQueryBuilder() {}

    /**
     * Build a DL-Query based on the answers the user gave
     *
     * @param scenario The scenario (list of questions)
     * @return The DL-Query
     */
    public static DLQuery buildQuery(List<Question> scenario) {
        String dlQuery = "";
        List<String> notDlQueries = new ArrayList<>();
        for (Question q : scenario) {
            Question.QUESTION_TYPE questionType = q.getType();
            String answer = OntologyString.convert(q.getAnswer());
            switch (questionType) {
                case TEAM_SPORT:
                    dlQuery += DLQueries.TEAM_OR_INDIVIDUAL_SPORT + answer + CONNECTOR_AND;
                    break;
                case SPORT_CATEGORY:
                    if (isBooleanAnswer(answer) && q.getTopic() != null) {
                        if (answerToBoolean(answer)) {
                            dlQuery += DLQueries.SPORTS_BY_CATEGORY + q.getTopic() + CONNECTOR_AND;
                        } else {
                            notDlQueries.add(DLQueries.SPORTS_BY_CATEGORY + q.getTopic());
                        }
                    } else {
                        dlQuery += DLQueries.SPORTS_BY_CATEGORY + answer + CONNECTOR_AND;
                    }
                    break;
                case LOCATION:
                    dlQuery += DLQueries.SPORTS_BY_LOCATION + answer + CONNECTOR_AND;
                    break;
            }
        }
        // Remove trailing " and "
        dlQuery = dlQuery.substring(0, dlQuery.length() - CONNECTOR_AND.length());
        return new DLQuery(dlQuery, notDlQueries);
    }

    /**
     * Check whether an answer is a boolean
     *
     * @param answer The answer the user gave
     * @return True if answer is a boolean
     */
    private static boolean isBooleanAnswer(String answer) {
        return BOOLEAN_ANSWER_MAP.containsKey(answer);
    }

    /**
     * Try to convert a string answer to a boolean
     *
     * @param answer The answer the user gave
     * @return A boolean corresponding to, if answer not of boolean type return false
     */
    private static boolean answerToBoolean(String answer) {
        boolean booleanAnswer = false;
        if (isBooleanAnswer(answer)) {
            booleanAnswer = BOOLEAN_ANSWER_MAP.get(answer);
        }
        return booleanAnswer;
    }
}
