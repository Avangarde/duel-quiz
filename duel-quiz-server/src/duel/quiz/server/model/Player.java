/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model;

/**
 *
 * @author Jummartinezro
 */
public class Player {

    private String user;
    private String pass;
    private String state;
    private Integer score;

    public Player(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

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

}
