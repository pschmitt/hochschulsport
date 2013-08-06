package co.schmitt.si.model;

/**
 * User: pschmitt
 * Date: 8/5/13
 * Time: 3:12 PM
 */
public class Question {

    public enum QUESTION_TYPE {
        TEAM_SPORT, LOCATION, SPORT_CATEGORY;
    }

    private String mQuestion;
    private String mAnswer;
    private String mTopic;
    //    private List<String> choices;
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

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public String getTopic() {
        return mTopic;
    }

    @Override
    public String toString() {
        return "[" + mType.name() + "] " + mQuestion + ": " + mAnswer;
    }
}
