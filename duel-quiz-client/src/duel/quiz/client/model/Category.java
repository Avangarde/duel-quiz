package duel.quiz.client.model;

import java.util.List;

/**
 * Created by juanmanuelmartinezromero on 02/12/14.
 */
public class Category {

    private String name;
    //Added List of questions to be able to pass it as a package
    private List<Question> listQuestions;

    public List<Question> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(List<Question> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() {
    }
    
    
}
