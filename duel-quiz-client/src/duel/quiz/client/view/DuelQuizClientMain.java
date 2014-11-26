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

    private static final BufferedReader reader
            = new BufferedReader(new InputStreamReader(System.in));
    private static final String cls = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int option = 0;
        while (option != 3) {
            mainMenu();
            option = readInteger();
            switch (option) {
                case 1:
                    signInOrSignUp(true);
                    break;
                case 2:
                    System.out.println("Sign Up");
                    signInOrSignUp(false);
                    break;
                case 3:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }

    }

    /**
     * Reads an integer from the console and returns it
     *
     * @return
     * @throws NumberFormatException
     */
    private static int readInteger() throws NumberFormatException {
        int option = 0;
        System.out.print("Select a number:\t");
        try {
            String input = reader.readLine();
            option = Integer.parseInt(input);
        } catch (IOException ex) {
            Logger.getLogger(DuelQuizClientMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid option");
        }
        return option;
    }

    /**
     * Shows the Main Menu
     */
    private static void mainMenu() {
        System.out.println(cls + "****  *****  ***** Duel Quiz *****  *****  ****");
        System.out.println("Do you have an account ?");
        System.out.println("1. Sign In");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit\n");
    }

    /**
     *
     * @return
     */
    private static boolean signInOrSignUp(boolean signIn) {
        boolean signed = false;
        boolean failed = false;
        while (!signed && !failed) {

            Player player = getPlayer();
            PlayerController pc = new PlayerController();
            String msg;
            if (signIn) {
                signed = pc.signInorSignUp(player, true);
                msg = "Login";
            } else {
                signed = pc.signInorSignUp(player, false);
                msg = "SignUp";
            }
            boolean valid = false;
            while (!signed && !failed && !valid) {
                System.out.println(cls + "Cannot " + msg + " \n\t Try Again ? ");
                System.out.println("\t1. Yes");
                System.out.println("\t2. No\t");
                switch (readInteger()) {
                    case 1:
                        valid = true;
                        break;
                    case 2:
                        failed = !failed;
                        break;
                    default:
                        System.out.println(cls + "Invalid Option");
                        break;
                }
            }
        }
        return signed;
    }

    /**
     * Creates and returns a player instance
     *
     * @return
     */
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
