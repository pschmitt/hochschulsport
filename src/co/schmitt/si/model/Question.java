package co.schmitt.si.model;

import java.util.List;

/**
 * User: pschmitt
 * Date: 8/5/13
 * Time: 3:12 PM
 */
public class Question {

    public enum QUESTION_TYPE {
        TEAM_SPORT, LOCATION, SPORT_CATEGORY
    }

    private int mQuestionId;
    private String mQuestion;
    private String mAnswer;
    private String mTopic;
    private List<Choice> mChoices;
    private QUESTION_TYPE mType;

    public Question(String question, QUESTION_TYPE type, int questionId) {
        mQuestion = question;
        mType = type;
        mQuestionId = questionId;
    }

    public String getAnswer() {
        return this.mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public QUESTION_TYPE getType() {
        return mType;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public List<Choice> getChoices() {
        return mChoices;
    }

    public void setChoices(List<Choice> choices) {
        mChoices = choices;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public int getNextQuestionId() {
        int nextQuestionId = -1;
        Choice choice = getChoice();
        if (choice != null) {
            nextQuestionId = choice.getNextQuestionId();
        }
        return nextQuestionId;
    }

    public boolean isFirstQuestion() {
        return mQuestionId == 1;
    }

    public boolean hasNext() {
        return (getChoice().hasNext());
    }

    private Choice getChoice() {
        Choice correspondingChoice = null;
        if (mAnswer != null) {
            for (Choice c : mChoices) {
                if (c.getText().equals(mAnswer)) {
                    return c;
                }
            }
        }
        return correspondingChoice;
    }

    @Override
    public String toString() {
        return "[" + mType.name() + "] " + mQuestion + ": " + mAnswer;
    }
}
