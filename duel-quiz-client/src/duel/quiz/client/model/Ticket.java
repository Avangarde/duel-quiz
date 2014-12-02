package duel.quiz.client.model;

import java.util.Date;

/**
 *
 * @author Edward
 */
public class Ticket {

    private Date lastConnexion;
    private String serverAddress;

    public Ticket() {
    }

    public Date getLastConnexion() {
        return lastConnexion;
    }

    public void setLastConnexion(Date lastConnexion) {
        this.lastConnexion = lastConnexion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

}
