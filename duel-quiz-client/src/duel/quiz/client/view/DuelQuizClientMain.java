/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.view;

import java.util.Scanner;

/**
 *
 * @author corteshs
 */
public class DuelQuizClientMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String user;
        String pass;

        Scanner in = new Scanner(System.in);
        System.out.println("Duel Quiz");
        System.out.println("User : ");
        user = in.next();
        System.out.println("Pass : ");
        pass = in.next();
    }
}
