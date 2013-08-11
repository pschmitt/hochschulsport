package co.schmitt.si.model;

/**
 * User: pschmitt
 * Date: 8/11/13
 * Time: 11:27 PM
 */
public class Choice {
    private String mText;
    private int mNextQuestionId;

    public Choice(String text, int nextQuestionId) {
        mText = text;
        mNextQuestionId = nextQuestionId;
    }

    public String getText() {
        return mText;
    }

    public int getNextQuestionId() {
        return mNextQuestionId;
    }

    public boolean hasNext() {
        return mNextQuestionId > 1;
    }

    @Override
    public String toString() {
        return mText;
    }
}
