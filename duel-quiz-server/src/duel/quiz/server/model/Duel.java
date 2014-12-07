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

    private int duelID;
    //TODO Enum for String
    private String status;
    //How to determine which one is which
    private int scorePlayer1;
    private int scorePlayer2;
    private String turn;
    private String adversary;
    private String player1;
    private String player2;

    public String getAdversary() {
        return adversary;
    }

    public void setAdversary(String adversary) {
        this.adversary = adversary;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Duel() {
    }
    


    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public int getDuelID() {
        return duelID;
    }

    public void setDuelID(int duelID) {
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

    public Duel(int duelID, String status) {
        this.duelID = duelID;
        this.status = status;
    }

    public Duel(int duelID, String status, int scorePlayer1, int scorePlayer2) {
        this.duelID = duelID;
        this.status = status;
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
    }
}
