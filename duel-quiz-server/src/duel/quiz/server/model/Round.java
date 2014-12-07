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
    
    private int roundID;
    private boolean p1HasPlayed;
    private boolean p2HasPlayed;
    
    //Foreign
    private Duel duel;
    private Category categoryName; 
    
    private List<Question> listQuestions;

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public Duel getDuel() {
        return duel;
    }

    public void setDuel(Duel duel) {
        this.duel = duel;
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

    public Round(int roundID, Duel duelID, Category categoryName) {
        this.roundID = roundID;
        this.duel = duelID;
        this.categoryName = categoryName;
    }

    public boolean isP1HasPlayed() {
        return p1HasPlayed;
    }

    public void setP1HasPlayed(boolean p1HasPlayed) {
        this.p1HasPlayed = p1HasPlayed;
    }

    public boolean isP2HasPlayed() {
        return p2HasPlayed;
    }

    public void setP2HasPlayed(boolean p2HasPlayed) {
        this.p2HasPlayed = p2HasPlayed;
    }
}
