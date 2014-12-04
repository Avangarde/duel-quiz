/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.controller;

import duel.quiz.server.model.Player;
import duel.quiz.server.model.Ticket;
import duel.quiz.server.model.dao.PlayerDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;
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
    private static final String FIND_ADVERSARY="FIND_ADVERSARY";
    private static final HashMap<String, Long> availablePlayers = new HashMap<>();

    /**
     * Logs a player and put his name in the availability list
     * @param user
     * @param pass
     * @return
     */
    public static Player loginPlayer(String user, String pass) {
        Player player;
        player = PlayerDAO.getPlayer(user, pass);
        PlayerDAO.setPlayerStatus(user, true);
        if (player != null) {
            availablePlayers.put(user, System.currentTimeMillis());
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
    public static void removePlayersUnavailables() {
        long actualTime = System.currentTimeMillis();
        String player;
        for (Entry<String, Long> av : availablePlayers.entrySet()) {
            if ((actualTime - av.getValue()) / 1000
                    > TimeUnit.MINUTES.toSeconds(REMOVE_PLAYERS_MINUTES)) {
                player = av.getKey();
                PlayerDAO.setPlayerStatus(player, false);
                availablePlayers.remove(player);
                System.out.println("Removed player " + player);
                //@TODO Update #players and send it to the LB
            }
        }
    }
    
    /**
     * Finds an adversary to the specified client.
     * @param user 
     */
    public void findAdversary(String user,String inetAddr,String loadBalancerAddr) {
        Socket socket=null;
        try {
            socket= new Socket(loadBalancerAddr,LOAD_BALANCER_PORT);
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
        }catch (SocketTimeoutException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
