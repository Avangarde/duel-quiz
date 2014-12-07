/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model;

import java.util.List;

/**
 *
 * @author Jummartinezro
 */
public class Player {

    private String user;
    private String pass;
    private String state;
    private Integer score;
    private Ticket ticket;
    //Foreign
    private List<Answer> listAnswers;
    private List<Duel> listDuels;

    public Player(String user, String pass, String state, Integer score) {
        this.user = user;
        this.pass = pass;
        this.state = state;
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Answer> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<Answer> listAnswers) {
        this.listAnswers = listAnswers;
    }

    public List<Duel> getListDuels() {
        return listDuels;
    }

    public void setListDuels(List<Duel> listDuels) {
        this.listDuels = listDuels;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Player{" + "user=" + user + '}';
    }
}
