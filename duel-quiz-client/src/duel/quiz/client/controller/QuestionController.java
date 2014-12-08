/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.controller;

import static duel.quiz.client.controller.AbstractController.PORT;
import duel.quiz.client.exception.ServerDownException;
import duel.quiz.client.model.Answer;
import duel.quiz.client.model.Category;
import duel.quiz.client.model.Duel;
import duel.quiz.client.model.Question;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
    private static final String GET_ANSWERED_QUESTIONS = "GET_ANSWERED_QUESTIONS";
    private final int TIME_OUT = 300000;

    public List<String> fetchAllCategories() throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        List<String> listCategories = new ArrayList<String>();
        try {
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
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
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
        //Empty if something went wrong
        return listCategories;
    }

    public boolean createNewQuestion(String categorySelected, String question, String rightAnswer, List<String> wrongAnswers) throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
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
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
        //Empty if something went wrong
        return true;
    }

    public void transmitPlayedData(Category categorySelected, String user, Duel duel) throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Sending request for categories
            output.writeUTF(SENDING_ROUND_DATA);
            output.writeUTF(user);
            output.writeUTF(duel.getAdversary());
            output.writeInt(duel.getDuelID());

            //@TODO Solve IndexOutOfBoundsException
            output.writeUTF(categorySelected.getName());

            for (Question each : categorySelected.getListQuestions()) {

                output.writeUTF(each.getQuestion());
                //Repeats 4 times
                for (Answer each2 : each.getAnswers()) {
                    output.writeUTF(each2.getAnswer());
                    output.writeBoolean(each2.isCorrect());
                    output.writeBoolean(each2.isChosenByAdversary());
                }
            }

            output.flush();

            //Final Status
            System.out.println(input.readUTF());
            System.out.println("THANK YOU FOR PLAYING\n");
            System.out.println("If the player accepts you will see the duel state in the games area :)\n");


            skClient.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
        //Empty if something went wrong
    }

    public Category getCategorySelected(int duelID, int roundId) throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        Category cat = null;
        try {
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Sending request for categories
            output.writeUTF(GET_ANSWERED_QUESTIONS);
            output.writeInt(duelID);
            output.writeInt(roundId);
            output.flush();
            
            cat = new Category(input.readUTF());
            List<Question> questions = new ArrayList<>(3);
            readQuestions(input, cat, questions);
            cat.setListQuestions(questions);


            skClient.close();
        } catch (UnknownHostException ex) {
//            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
        //Empty if something went wrong
        return cat;
    }
    
    private void readQuestions(DataInputStream input, Category category, List<Question> questions) throws IOException {
        for (int i = 0; i < 3; i++) {
            Question question = new Question(-1, input.readUTF(), category);
            questions.add(question);
            for (int j = 0; j < 4; j++) {
                Answer answer = new Answer(input.readLong(), input.readUTF(), input.readBoolean(), question);
                question.getAnswers().add(answer);
            }
        }
    }
}
