/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.controller;

import static duel.quiz.client.controller.AbstractController.PORT;
import duel.quiz.client.model.Answer;
import duel.quiz.client.model.Category;
import duel.quiz.client.model.Question;
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
    private static final String SENDING_ROUND_DATA = "SENDINGROUNDDATA";

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

            //Final Status
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

    public void transmitPlayedData(Category categorySelected, String user) {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            skClient = new Socket(HOST, PORT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));


            //Sending request for categories
            output.writeUTF(SENDING_ROUND_DATA);
            output.flush();

            output.writeUTF(user);
            
            //@TODO Solve IndexOutOfBoundsException
            output.writeUTF(categorySelected.getName());
            
            for (Question each : categorySelected.getListQuestions()){
            
            output.writeUTF(each.getQuestion());
            //Repeats 4 times
            for (Answer each2 : each.getAnswers()) {
                output.writeUTF(each2.getAnswer());
                output.writeBoolean(each2.isCorrect());
                output.writeBoolean(each2.isChosenByAdversary());
            }}
            
            output.flush();

            //Final Status
            System.out.println(input.readUTF());
            System.out.println("THANK YOU FOR PLAYING\n");
            System.out.println("If the player accepts you will see the duel state in the games area :)\n");


            skClient.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (IOException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO Exception");
        }
        //Empty if something went wrong
    }
}
