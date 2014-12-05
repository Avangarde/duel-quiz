/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model;

/**
 *
 * @author corteshs
 */
public class Duel {
    private long duelID;
    //TODO Enum for String
    private String status;
    //How to determine which one is which
    private int scorePlayer1;
    private int scorePlayer2;

    public long getDuelID() {
        return duelID;
    }

    public void setDuelID(long duelID) {
        this.duelID = duelID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public Duel(long duelID, String status) {
        this.duelID = duelID;
        this.status = status;
    }

    public Duel(long duelID, String status, int scorePlayer1, int scorePlayer2) {
        this.duelID = duelID;
        this.status = status;
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
    }
    
    
    
}
