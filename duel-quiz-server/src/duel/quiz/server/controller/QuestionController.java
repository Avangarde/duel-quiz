/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.controller;

import duel.quiz.server.model.Answer;
import duel.quiz.server.model.Category;
import duel.quiz.server.model.Question;
import duel.quiz.server.model.dao.AnswerDAO;
import duel.quiz.server.model.dao.DuelDAO;
import java.util.List;
import duel.quiz.server.model.dao.QuestionDAO;
import duel.quiz.server.model.dao.RoundDAO;
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

    /**
     * Construct the response with all these values (and also the correct answer)
     * 
     *     data is constructed as follows:
     *     --------------------
     *     String:   name of cat1
     *     String:   name of cat2
     *     String:   name of cat3
     *     --------Questions and answer package------------
     *     -------- Cat 1 Question 1 ------------
     *     String:   question 1 of cat1
     *     Long:     first Answer ID
     *     String:   answer
     *     Boolean:  correct or not
     *     -------- Cat 1 Question 2 ------------
     *     -------- Cat 1 Question 3 ------------
     *     -------- Cat 2 Question 1 ------------
     *     -------- Cat 2 Question 2 ------------
     *     -------- Cat 2 Question 3 ------------
     *     -------- Cat 3 Question 1 ------------
     *     -------- Cat 3 Question 2 ------------
     *     -------- Cat 3 Question 3 ------------
     * @param out
     * @param in
     */
    public static void sendNewQuestions(DataOutputStream out, DataInputStream in) {
        //@TODO Set Round to 1 (Can't do until Player answer)

        //Get 3 random categories
        List<Category> categories = QuestionDAO.getAllCategoriesWithQuestions();

        Random random = new Random();
        int first = random.nextInt(categories.size());
        int second = random.nextInt(categories.size());
        while (first == second) {
            second = random.nextInt(categories.size());
        }
        int third = random.nextInt(categories.size());
        while (first == third || second == third) {
            third = random.nextInt(categories.size());
        }

        String nameCat1 = categories.get(first).getName();
        String nameCat2 = categories.get(second).getName();
        String nameCat3 = categories.get(third).getName();

        // Get 3 random questions from these categories
        //@TODO FIX when there are no questions from these category
        List<Question> questionsCat1 = QuestionDAO.getRandomQuestionsByCat(nameCat1);
        List<Question> questionsCat2 = QuestionDAO.getRandomQuestionsByCat(nameCat2);
        List<Question> questionsCat3 = QuestionDAO.getRandomQuestionsByCat(nameCat3);

        setAnswers(questionsCat1);
        setAnswers(questionsCat2);
        setAnswers(questionsCat3);

        try {
             
            out.writeUTF(nameCat1);
            out.writeUTF(nameCat2);
            out.writeUTF(nameCat3);

            writeQuestions(questionsCat1, out);
            writeQuestions(questionsCat2, out);
            writeQuestions(questionsCat3, out);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the answers from these questions
     *
     * @param questionsCat1
     */
    private static void setAnswers(List<Question> questionsCat1) {
        //@TODO FIX when there are no answers from these question
        for (Question question : questionsCat1) {
            question.setAnswers(AnswerDAO.getAnswers(question.getQuestionID()));
        }
    }

    /**
     * Writes in the buffer the questions and their answers in the category
     *
     * @param questionsCat1
     * @param out
     * @throws IOException
     */
    private static void writeQuestions(List<Question> questionsCat1, DataOutputStream out) throws IOException {
        for (Question question : questionsCat1) {
            out.writeUTF(question.getQuestion());
            for (Answer answer : question.getAnswers()) {
                out.writeLong(answer.getAnswerID());
                out.writeUTF(answer.getAnswer());
                out.writeBoolean(answer.isCorrect());
            }
        }
    }

    public static Category receivePlayedData(int cases, DataOutputStream out, DataInputStream in) {
        
        Category ret = new Category();
        
        try {
            
            //User
            String user = in.readUTF();
            
            //USER ADVERSARY
            //String adversary = in.readUTF();
            
            //DUEL ID
            
            int duelID = in.readInt();
            
            //Name
            ret.setName(in.readUTF());
            
            //Question
            //I am so sorry for this code :/
            ret.setListQuestions(new ArrayList<Question>());
            ret.getListQuestions().add(new Question(-1, in.readUTF(), ret));
            ret.getListQuestions().get(0).setAnswers(new ArrayList<Answer>());
            ret.getListQuestions().get(0).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(0).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(0).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(0).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().add(new Question(-1, in.readUTF(), ret));
            ret.getListQuestions().get(1).setAnswers(new ArrayList<Answer>());
            ret.getListQuestions().get(1).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(1).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(1).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(1).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().add(new Question(-1, in.readUTF(), ret));
            ret.getListQuestions().get(2).setAnswers(new ArrayList<Answer>());
            ret.getListQuestions().get(2).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(2).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(2).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            ret.getListQuestions().get(2).getAnswers().add(new Answer(in.readUTF(), in.readBoolean(), in.readBoolean()));
            
            System.out.println("TODO LO RECIBI BIEEEEEEN" + '\n');
            
            //TODO Database operations
            
            //Obtain questions IDS
            
            ret.getListQuestions().get(0).setQuestionID(QuestionDAO.getByName(ret.getListQuestions().get(0).getQuestion()));
            ret.getListQuestions().get(1).setQuestionID(QuestionDAO.getByName(ret.getListQuestions().get(1).getQuestion()));
            ret.getListQuestions().get(2).setQuestionID(QuestionDAO.getByName(ret.getListQuestions().get(2).getQuestion()));
            
            //@TODO Obtain Answers Ids
            List<Answer> answersToPersist = new ArrayList<>();
            
            for (Question each : ret.getListQuestions()){
                for (Answer each2 : each.getAnswers()){
                    if (each2.isChosenByAdversary()){
                        each2.setAnswerID(AnswerDAO.getByString(each2.getAnswer()));
                        answersToPersist.add(each2);
                    }
                }
            }
            
            //Duel
            //int duelID = DuelDAO.create("WAITING");
            //DuelDAO.linkPlayerToDuel(user,duelID);
            
            //Round
            int roundID = RoundDAO.create(duelID, ret.getName());
            //Upon creation always first turn
            roundID = 1;
            RoundDAO.linkRoundToQuestion(duelID, roundID, ret.getListQuestions().get(0).getQuestionID());
            RoundDAO.linkRoundToQuestion(duelID, roundID, ret.getListQuestions().get(1).getQuestionID());
            RoundDAO.linkRoundToQuestion(duelID, roundID, ret.getListQuestions().get(2).getQuestionID());
            
            //Add Player Answers
            
            AnswerDAO.linkPlayerToAnswer(user, answersToPersist.get(0).getAnswerID());
            AnswerDAO.linkPlayerToAnswer(user, answersToPersist.get(1).getAnswerID());
            AnswerDAO.linkPlayerToAnswer(user, answersToPersist.get(2).getAnswerID());
            
            int player = 1;
            int score = countCorrectAnswers(answersToPersist);
            //Update Duel points
            DuelDAO.updateScore(duelID, score, player);

            //TODO Persist
            out.writeUTF("Persisted :)");
        } catch (IOException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        return ret;
    }

    public static void transmitAdversaryPlayedData(Category received, DataOutputStream out, DataInputStream in) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static int countCorrectAnswers(List<Answer> answersToPersist) {
        return 0;
    }
}
