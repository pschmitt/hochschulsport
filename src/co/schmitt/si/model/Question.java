package co.schmitt.si.model;

/**
 * User: pschmitt
 * Date: 8/5/13
 * Time: 3:12 PM
 */
public class Question {

    public enum QUESTION_TYPE {
        TEAM_SPORT("TEAM_SPORT"), LOCATION("LOCATION"), SPORT_CATEGORY("SPORT_CATEGORY");
        private String value;

        private QUESTION_TYPE(String value) {
            this.value = value;
        }
    }

    private String mQuestion;
    private String mAnswer;

    private QUESTION_TYPE mType;

    public Question(String question) {
        mQuestion = question;
    }

    public Question(String question, QUESTION_TYPE type) {
        mQuestion = question;
        mType = type;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
    }

    public String getAnswer() {
        return this.mAnswer;
    }

    public void setType(QUESTION_TYPE type) {
        this.mType = type;
    }

    public QUESTION_TYPE getType() {
        return this.mType;
    }

    public String getQuestion() {
        return this.mQuestion;
    }

    @Override
    public String toString() {
        return "[" + mType.name() + "] " + mQuestion + ": " + mAnswer;
    }
}
