/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.controller;

import static duel.quiz.client.controller.AbstractController.HOST;
import duel.quiz.client.model.Player;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author martijua
 */
public class PlayerController extends AbstractController {

    private static final String LOGIN_SMS = "LOGIN";
    private static final String SIGNUP_SMS = "REGISTER";

    public boolean signIn(Player player) {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        boolean logged = false;
        try {
            //@TODO: deal with java.net.ConnectException
            skClient = new Socket(HOST, PORT);
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
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("IO Exception");
        }
        return logged;
    }

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
}
