/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.controller;

import duel.quiz.server.model.Answer;
import duel.quiz.server.model.Category;
import duel.quiz.server.model.Question;
import duel.quiz.server.model.dao.AnswerDAO;
import java.util.List;
import duel.quiz.server.model.dao.QuestionDAO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    public static boolean createNewQuestion(DataOutputStream out, DataInputStream in) {
        List<String> wrongAnswers = new ArrayList<>();

        try {
            String categorySelected = in.readUTF();
            String question = in.readUTF();
            String rightAnswer = in.readUTF();
            wrongAnswers.add(in.readUTF());
            wrongAnswers.add(in.readUTF());
            wrongAnswers.add(in.readUTF());

            //TODO Persist
            out.writeUTF("Persisted :)");
        } catch (IOException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        return true;
    }

    public static void sendNewQuestions(DataOutputStream out, DataInputStream in) {
        //@TODO Set Round to 1 (Can't do until Player answer)
        //@TODO Get 3 random categories
        List<Category> categories = QuestionDAO.getAllCategories();
        int min = 1;
        int max = categories.size();
        int first = randInt(min, max);
        int second = randInt(min, max);
        while (first == second) {
            second = randInt(min, max);
        }
        int third = randInt(min, max);
        while (first == third || second == third) {
            third = randInt(min, max);
        }

        String nameCat1 = categories.get(first).getName();
        String nameCat2 = categories.get(second).getName();
        String nameCat3 = categories.get(third).getName();

        //@TODO Get 3 random questions from these categories
        //@TODO FIX when there are no questions from these category
        List<Question> questionsCat1 = QuestionDAO.getRandomQuestionsByCat(nameCat1);
        List<Question> questionsCat2 = QuestionDAO.getRandomQuestionsByCat(nameCat2);
        List<Question> questionsCat3 = QuestionDAO.getRandomQuestionsByCat(nameCat3);

        //@TODO Get the answers from these questions
        //@TODO FIX when there are no answers from these question
        for (Question question : questionsCat1) {
            question.setAnswers(AnswerDAO.getAnswers(question.getQuestionID()));
        }
        for (Question question : questionsCat2) {
            question.setAnswers(AnswerDAO.getAnswers(question.getQuestionID()));
        }
        for (Question question : questionsCat3) {
            question.setAnswers(AnswerDAO.getAnswers(question.getQuestionID()));
        }
        try {
            //@TODO Construct the response with all these values (and also the correct answer)
            
            //data is constructed as follows:
            //--------------------
            //String:   name of cat1
            //String:   name of cat2
            //String:   name of cat3
            //--------Questions and answer package------------
            //-------- Cat 1 Question 1 ------------
            //String:   question 1 of cat1
            //Long:     first Answer ID
            //String:   answer
            //Boolean:  correct or not
            //-------- Cat 1 Question 2 ------------
            //-------- Cat 1 Question 3 ------------
            //-------- Cat 2 Question 1 ------------
            //-------- Cat 2 Question 2 ------------
            //-------- Cat 2 Question 3 ------------
            //-------- Cat 3 Question 1 ------------
            //-------- Cat 3 Question 2 ------------
            //-------- Cat 3 Question 3 ------------

            out.writeUTF(nameCat1);
            out.writeUTF(nameCat2);
            out.writeUTF(nameCat3);
            for (Question question : questionsCat1) {
                out.writeUTF(question.getQuestion());
                for (Answer answer : question.getAnswers()) {
                    out.writeLong(answer.getAnswerID());
                    out.writeUTF(answer.getAnswer());
                    out.writeBoolean(answer.isCorrect());
                }
            }
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}