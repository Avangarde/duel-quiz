/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.controller;

import duel.quiz.client.model.Player;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author martijua
 */
public class PlayerController extends AbstractController {

    private static final String LOGIN_SMS = "LOGIN";
    private static final String SIGNUP_SMS = "REGISTER";
    private static final String NO_MORE_PLAYERS = "ENDOFDATA";
    private static final String RANDOMPLAY = "RANDOMPLAY";
    private final int TIME_OUT = 5000;

    public PlayerController(String host) {
        this.HOST = host;
    }

    /**
     * Sign-in a Player
     *
     * @param player
     * @return
     */
    public boolean signIn(Player player) {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        boolean logged = false;
        try {
            //@TODO: deal with java.net.ConnectException
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Sending signUp SMS, user and passwd
            output.writeUTF(LOGIN_SMS);
            output.writeUTF(player.getUser());
            output.writeUTF(player.getPass());
            output.flush();
            player.setScore(input.readInt());
            logged = input.readBoolean();
            skClient.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (SocketTimeoutException ex) {
            //@TODO Server fault 
            System.err.println("Server down :(");
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("IO Exception");
        }
        return logged;
    }

    /**
     * Sign up a player
     *
     * @param player
     * @return
     */
    public boolean signUp(Player player) {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        boolean logged = false;
        try {
            //@TODO: deal with java.net.ConnectException
            skClient = new Socket(HOST, PORT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            output.writeUTF(SIGNUP_SMS);

            output.writeUTF(player.getUser());
            output.writeUTF(player.getPass());
            output.flush();

            logged = input.readBoolean();
            skClient.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (IOException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO Exception");
        }
        return logged;
    }

    /**
     * Must get a list with the player list
     *
     * @return
     */
    public List<String> fetchPlayerList() {
//        Socket skClient;
//        DataInputStream input;
//        DataOutputStream output;
//        List<String> listPlayers = new ArrayList<String>();
//        try {
//            skClient = new Socket(HOST, PORT);
//            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
//            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));
//
//
//            //Sending request for categories
//            output.writeUTF(RANDOMPLAY);
//            output.flush();
//
//            String dataSent = input.readUTF();
//
//
//            while (dataSent == null ? NO_MORE_PLAYERS != null : !dataSent.equals(NO_MORE_PLAYERS)) {
//                listPlayers.add(dataSent);
//                dataSent = input.readUTF();
//            }
//            skClient.close();
//        } catch (UnknownHostException ex) {
////            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Unknown Host");
//        } catch (IOException ex) {
////            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("IO Exception");
//        }
//        //Empty if something went wrong
//        return listPlayers;
        return null;
    }

    /**
     * Request the server for a New Player and get the questions
     *
     */
    public void requestRandomChallenge() {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            skClient = new Socket(HOST, PORT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            output.writeUTF(RANDOMPLAY);
            output.flush();

            //@TODO Receive the answers and deal with them
            skClient.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (IOException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO Exception");
        }
    }
}
