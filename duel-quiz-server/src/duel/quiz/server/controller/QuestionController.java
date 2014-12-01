/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.controller;

import duel.quiz.server.model.Category;
import java.util.List;
import duel.quiz.server.model.dao.QuestionDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corteshs
 */
public class QuestionController {

    private static final String NO_MORE_CATEGORIES = "ENDOFDATA";

    public static boolean transmitCategories(boolean all, DataOutputStream out, DataInputStream in) {

        //1, Obtain from database
        List<Category> list = QuestionDAO.getAllCategories();
        try {
            if (all) {
                for (Category each : list) {
                    out.writeUTF(each.getName());
                }
                out.writeUTF(NO_MORE_CATEGORIES);
            } else {
                int min = 1;
                int max = list.size();
                int first = randInt(min, max);
                int second = randInt(min, max);
                while (first == second) {
                    second = randInt(min, max);
                }
                int third = randInt(min, max);
                while (first == third || second == third) {
                    third = randInt(min, max);
                }


                out.writeUTF(list.get(first).getName());
                out.writeUTF(list.get(second).getName());
                out.writeUTF(list.get(third).getName());

            }
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        //2, Transmit
        return true;

    }

    public static int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
