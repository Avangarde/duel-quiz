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

/**
 *
 * @author martijua
 */
public class PlayerController extends AbstractController {

    private static final String LOGIN_SMS = "LOGIN";
    private static final String SIGNUP_SMS = "REGISTER";

    public boolean signInorSignUp(Player player, boolean signIn) {
        Socket skCliente;
        DataInputStream input;
        DataOutputStream output;
        boolean logged = false;
        try {
            skCliente = new Socket(HOST, PUERTO);
            input = new DataInputStream(new BufferedInputStream(skCliente.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skCliente.getOutputStream()));

            //Sending signInorSignUp SMS, user and passwd
            if (signIn) {
                output.writeUTF(LOGIN_SMS);
            } else {
                output.writeUTF(SIGNUP_SMS);
            }
            output.writeUTF(player.getUsername());
            output.writeUTF(player.getPasswd());
            output.flush();

            //Getting response
            logged = input.readBoolean();
            skCliente.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (IOException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO Exception");
        }
        return logged;
    }

    public boolean signUp(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
