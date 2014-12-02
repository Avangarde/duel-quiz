package duel.quiz.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanmanuelmartinezromero on 02/12/14.
 */
public class Question {
    private long questionID;
    private String question;
    //Foreign
    private Category categoryName;

    List<Answer> answers;

    public Question(long questionID, String question, Category categoryName) {
        this.questionID = questionID;
        this.question = question;
        this.categoryName = categoryName;
        this.answers=new ArrayList<Answer>(4);
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Category getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Category categoryName) {
        this.categoryName = categoryName;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
