/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model;

import java.util.List;

/**
 *
 * @author corteshs
 */
public class Round {
    
    private long roundID;
    
    //Foreign
    private Duel duelID;
    private Category categoryName; 
    
    private List<Question> listQuestions;

    public long getRoundID() {
        return roundID;
    }

    public void setRoundID(long roundID) {
        this.roundID = roundID;
    }

    public Duel getDuelID() {
        return duelID;
    }

    public void setDuelID(Duel duelID) {
        this.duelID = duelID;
    }

    public Category getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Category categoryName) {
        this.categoryName = categoryName;
    }

    public List<Question> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(List<Question> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public Round(long roundID, Duel duelID, Category categoryName) {
        this.roundID = roundID;
        this.duelID = duelID;
        this.categoryName = categoryName;
    }
    
    
    
}
