/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.view;

import duel.quiz.client.controller.PlayerController;
import duel.quiz.client.model.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corteshs
 */
public class DuelQuizClientMain {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Duel Quiz");
        signIn();
    }

    private static void signIn() {
        boolean signed = false;
        while (!signed) {

            Player player = getPlayer();
            PlayerController pc = new PlayerController();
            signed = pc.login(player);
            if (!signed) {
                System.out.println("Cannot Login \n\t Try Again ?");
            }
        }
        System.out.println("Logged !");
    }

    private static Player getPlayer() {
        String user = new String();
        String pass = new String();
        try {
            System.out.print("Username : ");
            user = reader.readLine();
            System.out.print("Pass : ");
            pass = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(DuelQuizClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        Player player = new Player(user, pass);
        return player;
    }
}
