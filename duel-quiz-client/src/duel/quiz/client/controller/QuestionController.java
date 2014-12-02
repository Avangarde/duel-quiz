/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author corteshs
 */
public class QuestionController extends AbstractController {

    private static final String REQUEST_CATEGORIES = "REQUESTCATS";
    private static final String REQUEST_3_CATEGORIES = "REQUEST3CATS";
    private static final String NO_MORE_CATEGORIES = "ENDOFDATA";
    private static final String NEW_QUESTION = "NEWQUESTION";

    public List<String> fetchAllCategories() {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        List<String> listCategories = new ArrayList<String>();
        try {
            skClient = new Socket(HOST, PORT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));


            //Sending request for categories
            output.writeUTF(REQUEST_CATEGORIES);
            output.flush();

            String dataSent = input.readUTF();


            while (dataSent == null ? NO_MORE_CATEGORIES != null : !dataSent.equals(NO_MORE_CATEGORIES)) {
                listCategories.add(dataSent);
                dataSent = input.readUTF();

            }


            skClient.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (IOException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO Exception");
        }
        //Empty if something went wrong
        return listCategories;
    }

    public boolean createNewQuestion(String categorySelected, String question, String rightAnswer, List<String> wrongAnswers) {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            skClient = new Socket(HOST, PORT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));


            //Sending request for categories
            output.writeUTF(NEW_QUESTION);
            output.flush();

            //@TODO Solve IndexOutOfBoundsException
            output.writeUTF(categorySelected);
            output.writeUTF(question);
            output.writeUTF(rightAnswer);
            output.writeUTF(wrongAnswers.remove(1));
            output.writeUTF(wrongAnswers.remove(1));
            output.writeUTF(wrongAnswers.remove(1));

            output.flush();
            System.out.println(input.readUTF());


            skClient.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (IOException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO Exception");
        }
        //Empty if something went wrong
        return true;
    }
}
