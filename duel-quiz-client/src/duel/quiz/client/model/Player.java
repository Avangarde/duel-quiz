/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.model;

/**
 *
 * @author martijua
 */
public class Player {
    private String username;
    private String passwd;

    public Player(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }
       
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param passwd the passwd to set
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    
}
