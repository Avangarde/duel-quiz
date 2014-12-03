package duel.quiz.client.model;

/**
 * Created by juanmanuelmartinezromero on 02/12/14.
 */
public class Answer {

    private long answerID;
    private String answer;
    private boolean correct;
    //Added because of reasons
    private boolean chosenByAdversary = false;

    public boolean isChosenByAdversary() {
        return chosenByAdversary;
    }

    public void setChosenByAdversary(boolean chosenByAdversary) {
        this.chosenByAdversary = chosenByAdversary;
    }
    private Question questionID;

    public Answer(long answerID, String answer, boolean correct, Question questionID) {
        this.answerID = answerID;
        this.answer = answer;
        this.correct = correct;
        this.questionID = questionID;
    }

    public long getAnswerID() {
        return answerID;
    }

    public void setAnswerID(long answerID) {
        this.answerID = answerID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Question questionID) {
        this.questionID = questionID;
    }
}
