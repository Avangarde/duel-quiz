package duel.quiz.server.model;

import java.util.Date;

/**
 *
 * @author Edward
 */
public class Ticket {

    private Date lastConnexion;
    private String clientAddress;
    private Player player;

    public Ticket() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getLastConnexion() {
        return lastConnexion;
    }

    public void setLastConnexion(Date lastConnexion) {
        this.lastConnexion = lastConnexion;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
}
