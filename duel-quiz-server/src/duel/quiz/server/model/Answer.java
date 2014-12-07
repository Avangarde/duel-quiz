/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model;

/**
 *
 * @author corteshs
 */
public class Answer {
    private int answerID;
    private String answer;
    private boolean correct;
    
    private Question questionID;
    
    //Added because of reasons
    private boolean chosenByAdversary = false;

    public boolean isChosenByAdversary() {
        return chosenByAdversary;
    }

    public void setChosenByAdversary(boolean chosenByAdversary) {
        this.chosenByAdversary = chosenByAdversary;
    }

    public Answer(String answer, boolean correct, boolean chosen) {
        this.answer = answer;
        this.correct = correct;
        this.chosenByAdversary = chosen;
    }
    
    

    public Answer(int answerID, String answer, boolean correct, Question questionID) {
        this.answerID = answerID;
        this.answer = answer;
        this.correct = correct;
        this.questionID = questionID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
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
