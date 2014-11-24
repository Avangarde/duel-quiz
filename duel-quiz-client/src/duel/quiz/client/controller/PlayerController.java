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
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martijua
 */
public class PlayerController extends AbstractController {

    private static final String LOGIN_SMS = "LOGIN";

    public boolean login(Player player) {
        Socket skCliente;
        DataInputStream input;
        DataOutputStream output;
        boolean logged = false;
        try {
            skCliente = new Socket(HOST, PUERTO);
            input = new DataInputStream(new BufferedInputStream(skCliente.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skCliente.getOutputStream()));
            
            //Sending login SMS, user and passwd
            output.writeUTF(LOGIN_SMS);
            output.writeUTF(player.getUsername());
            output.writeUTF(player.getPasswd());
            output.flush();
            
            //Getting response
            logged = input.readBoolean();
            skCliente.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logged;
    }
}
