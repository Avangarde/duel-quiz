/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.controller;

import duel.quiz.server.Server;
import duel.quiz.server.model.Duel;
import duel.quiz.server.model.Player;
import duel.quiz.server.model.Ticket;
import duel.quiz.server.model.dao.DuelDAO;
import duel.quiz.server.model.dao.PlayerDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jummartinezro
 */
public class PlayerController implements Runnable {

    /**
     * Number of minutes to wait to remove a non-available user from the list
     */
    private static final int REMOVE_PLAYERS_MINUTES = 5;
    private static final int LOAD_BALANCER_PORT = 4466;
    private static final int TIME_OUT = 60000;
    private static final String FIND_ADVERSARY = "FIND_ADVERSARY";
    private Server server;

    public PlayerController(Server server) {
        this.server = server;
    }

    /**
     * Logs a player and put his name in the availability list
     *
     * @param user
     * @param pass
     * @return
     */
    public Player loginPlayer(String user, String pass) {
        Player player;
        player = PlayerDAO.getPlayer(user, pass);
        if (player != null) {
            PlayerDAO.setPlayerStatus(user, true);
        } else {
            PlayerDAO.setPlayerStatus(user, false);
        }
        return player;
    }

    /**
     * Register a player
     *
     * @param user
     * @param pass
     * @return
     */
    public static Boolean registerPlayer(String user, String pass) {
        //Verify existence in all BDs
        if (PlayerDAO.getPlayer(user, pass) == null) {
            //Enregistrer l'informations dans la BD
            PlayerDAO.persist(user, pass);
            System.out.println("Account registered");
            return true;
        } else {
            System.out.println("User already exists here");
            return false;
        }
    }

    /**
     * Reads the list of available players and if he is not connected then
     * change his status
     */
    public void removePlayersUnavailables() {
        long actualTime = System.currentTimeMillis();
        for (Iterator<Ticket> iterator = server.getTickets().iterator(); iterator.hasNext();) {
            Ticket ticket = iterator.next();
            if (ticket != null) {
                if ((actualTime - ticket.getLastConnexion().getTime()) / 1000
                        > TimeUnit.MINUTES.toSeconds(REMOVE_PLAYERS_MINUTES)) {
                    if (ticket.getPlayer() != null) {
                        PlayerDAO.setPlayerStatus(ticket.getPlayer().getUser(), false);
                    }
                    server.getTickets().remove(ticket);
                    
                    System.out.println("Removed player " + ticket);
                    //@TODO Update #players and send it to the LB
                }
            }
        }
    }

    /**
     * Finds an adversary to the specified client.
     *
     * @param user
     */
    public void findAdversary(String user, String inetAddr, String loadBalancerAddr) {
        Socket socket = null;
        try {
            socket = new Socket(loadBalancerAddr, LOAD_BALANCER_PORT);
            socket.setSoTimeout(TIME_OUT);
            DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            //Sending Get Server Message
            output.writeUTF(FIND_ADVERSARY);
            output.writeUTF(user);
            output.writeUTF(inetAddr);
            System.out.print("Finding Adversary ...");
            output.flush();
            //@TODO Create a new Thread waiting for the Adversary Adress
//            String address = input.readUTF();
//            System.out.println("..." + address);   
        } catch (UnknownHostException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketTimeoutException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get 10 players (maximun). Look for available players first.
     *
     * @return
     */
    public List<String> getPlayers() {
        List<String> players = new ArrayList<>();

        List<Player> availablePlayers = PlayerDAO.getAvailablePlayers();
        for (Player player : availablePlayers) {
            players.add(player.getUser());
        }

        if (availablePlayers.size() < 10) {
            List<Player> unavailablePlayers;
            unavailablePlayers = PlayerDAO.getUnavailablePlayers();
            for (int i = 0; i < 10 - availablePlayers.size() && i < unavailablePlayers.size(); i++) {
                players.add(unavailablePlayers.get(i).getUser());
            }
        }

        return players;
    }

    @Override
    public void run() {
        while (true) {
            removePlayersUnavailables();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getPlayerGames(String user, DataOutputStream out, DataInputStream in) {
//Obtener todos los duelos del jugador segun estado
        //Popular lista
        List<Duel> duels = new ArrayList<>();
        duels = DuelDAO.getAllDuels(user);
        try {
            //Pasar tamano de arreglo

            out.writeInt(duels.size());
            out.flush();

            for (Duel temp : duels) {
                //Native data in BD
                out.writeLong(temp.getDuelID());
                out.writeUTF(temp.getStatus());
                //out.writeUTF("other");
                out.writeUTF(temp.getTurn());
                out.writeInt(temp.getScorePlayer1());
                out.writeInt(temp.getScorePlayer2());

                //Not so native stuff
                out.writeUTF(temp.getAdversary());
//                out.writeUTF("other");

                out.writeUTF(temp.getPlayer1());
//                out.writeUTF("other");

                out.writeUTF(temp.getPlayer2());

            }
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    public void getNotifications(String user, DataOutputStream out, DataInputStream in) {
        List<Duel> duels = new ArrayList<>();
        duels = DuelDAO.getAllNotifications(user);
        try {
            //Pasar tamano de arreglo

            out.writeInt(duels.size());
            out.flush();

            for (Duel temp : duels) {
                //Native data in BD
                out.writeLong(temp.getDuelID());
                out.writeUTF(temp.getStatus());
                //out.writeUTF("other");
                //out.writeUTF(temp.getTurn());
                //out.writeInt(temp.getScorePlayer1());
                //out.writeInt(temp.getScorePlayer2());

                //Not so native stuff
                out.writeUTF(temp.getAdversary());
                //out.writeUTF("other");

                //out.writeUTF(temp.getPlayer1());
                //out.writeUTF("other");

                //out.writeUTF(temp.getPlayer2());

            }
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getNotificationNumber(String user, DataOutputStream out, DataInputStream in) {
        int transmit = 0;
        List<Duel> duels = DuelDAO.getAllNotifications(user);
        try {
            //Pasar tamano de arreglo

            out.writeInt(duels.size());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
