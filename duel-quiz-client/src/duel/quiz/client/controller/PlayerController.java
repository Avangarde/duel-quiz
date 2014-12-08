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
import duel.quiz.client.model.Player;
import duel.quiz.client.model.Question;
import duel.quiz.client.model.Round;
import duel.quiz.client.view.DuelQuizClientMain;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author martijua
 */
public class PlayerController extends AbstractController {

    private static final String LOGIN_SMS = "LOGIN";
    private static final String SIGNUP_SMS = "REGISTER";
    private static final String RANDOMPLAY = "RANDOMPLAY";
    private static final String GET_PLAYERS = "GET PLAYERS";
    private static final String CHALLENGE = "CHALLENGE";
    private static final String GET_DUELS = "GET DUELS";
    private static final String GET_QUESTIONS = "GET QUESTIONS";
    private static final String GET_NOTIFICATION_SIZE = "GETNOTSIZE";
    private static final String GET_NOTIFICATIONS = "GETNOTIFICATIONS";
    //Values for duel status
    public static final String ENDED = "Fini";
    public static final String RUNNING = "En cours";
    public static final String WAITING = "En Attente";

    private Duel duel;

    public List<Duel> getPlayerGames(String user) throws ServerDownException {
        //Create an array
        List<Duel> ret = new ArrayList<>();
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            //@TODO: deal with java.net.ConnectException
            //Create Socket

            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Send message
            output.writeUTF(GET_DUELS);
            output.writeUTF(user);
            output.flush();

            //Populate array
            //How many will i receive?
            int it = input.readInt();

            for (int j = 1; j <= it; j++) {
                Duel temp = new Duel();

                //Native data in BD
                temp.setDuelID(input.readInt());
                temp.setStatus(input.readUTF());
                temp.setTurn(input.readUTF());
                temp.setScorePlayer1(input.readInt());
                temp.setScorePlayer2(input.readInt());

                //Not so native stuff
                temp.setAdversary(input.readUTF());
                temp.setPlayer1(input.readUTF());
                temp.setPlayer2(input.readUTF());
                
                if (input.readBoolean()) {
                    //Get current round
                    Round round = new Round(input.readInt());
                    round.setCategoryName(input.readUTF());
                    round.setP1Hasplayed(input.readBoolean());
                    round.setP2Hasplayed(input.readBoolean());
                    temp.setCurrentRound(round);
                }
                
                ret.add(temp);

            }

            skClient.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }

        //Populate array
        //Return Array
        return ret;
    }


    private final int TIME_OUT = 300000;

    public PlayerController(String host) {
        this.HOST = host;
    }

    /**
     * Sign-in a Player
     *
     * @param player
     * @return
     */
    public boolean signIn(Player player) throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        boolean logged = false;
        try {
            //@TODO: deal with java.net.ConnectException
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Sending signUp SMS, user and passwd
            output.writeUTF(LOGIN_SMS);
            output.writeUTF(player.getUser());
            output.writeUTF(player.getPass());
            output.flush();
            player.setScore(input.readInt());
            logged = input.readBoolean();
            skClient.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
        return logged;
    }

    /**
     * Sign up a player
     *
     * @param player
     * @return
     */
    public boolean signUp(Player player) throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        boolean logged = false;
        try {
            //@TODO: deal with java.net.ConnectException
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            output.writeUTF(SIGNUP_SMS);

            output.writeUTF(player.getUser());
            output.writeUTF(player.getPass());
            output.flush();

            logged = input.readBoolean();
            skClient.close();
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
        return logged;
    }

    /**
     * Must get a list with the player list
     *
     * @return
     */
    public List<String> fetchPlayerList() throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        List<String> listPlayers = new ArrayList<>();
        try {
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Sending request for players
            output.writeUTF(GET_PLAYERS);
            output.flush();

            int numPlayers = input.readInt();
            for (int i = 0; i < numPlayers; i++) {
                String player = input.readUTF();
                if (!player.equals(DuelQuizClientMain.currentPlayer.getUser())) {
                    listPlayers.add(player);
                }
            }
            input.readBoolean();
            skClient.close();
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
        //Empty if something went wrong
        return listPlayers;
    }

    /**
     * Request the server for a New Player and get the questions
     *
     */
    public List<Category> obtainCategoryQuestionsAnswers() {
        List<Category> ret = new ArrayList<>();
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            output.writeUTF(RANDOMPLAY);
            output.writeUTF(DuelQuizClientMain.currentPlayer.getUser());
            output.flush();

            //Receive the answers and deal with them
            Category category1 = new Category(input.readUTF());
            Category category2 = new Category(input.readUTF());
            Category category3 = new Category(input.readUTF());

            List<Question> questionsCat1 = new ArrayList<Question>(3);
            List<Question> questionsCat2 = new ArrayList<Question>(3);
            List<Question> questionsCat3 = new ArrayList<Question>(3);

            readQuestions(input, category1, questionsCat1);
            readQuestions(input, category2, questionsCat2);
            readQuestions(input, category3, questionsCat3);

            category1.setListQuestions(questionsCat1);
            category2.setListQuestions(questionsCat2);
            category3.setListQuestions(questionsCat3);

            ret.add(category1);
            ret.add(category2);
            ret.add(category3);

            String adv = input.readUTF();
            int idDuel = input.readInt();

            skClient.close();
            
            duel = new Duel(idDuel, null);
            duel.setAdversary(adv);
            //@TODO Answer the questions and send the answers to the server
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        } finally {
            return ret;
        }
    }

    public List<Category> getQuestions() {
        List<Category> ret = new ArrayList<>();
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            output.writeUTF(GET_QUESTIONS);
            output.writeUTF(DuelQuizClientMain.currentPlayer.getUser());
            output.flush();

            //Receive the questions and deal with them
            Category category1 = new Category(input.readUTF());
            Category category2 = new Category(input.readUTF());
            Category category3 = new Category(input.readUTF());

            List<Question> questionsCat1 = new ArrayList<Question>(3);
            List<Question> questionsCat2 = new ArrayList<Question>(3);
            List<Question> questionsCat3 = new ArrayList<Question>(3);

            readQuestions(input, category1, questionsCat1);
            readQuestions(input, category2, questionsCat2);
            readQuestions(input, category3, questionsCat3);

            category1.setListQuestions(questionsCat1);
            category2.setListQuestions(questionsCat2);
            category3.setListQuestions(questionsCat3);

            ret.add(category1);
            ret.add(category2);
            ret.add(category3);

            //@TODO Receive the answers and deal with them
            skClient.close();

        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        } finally {
            return ret;
        }
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

    public void challengePlayer(String player, String opponent) throws ServerDownException {
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            //@TODO: deal with java.net.ConnectException
            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            output.writeUTF(CHALLENGE);
            output.writeUTF(player);
            output.writeUTF(opponent);
            output.flush();

            input.readBoolean();
            skClient.close();
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }
    }

    public List<Duel> fetchNotifications(String user) throws ServerDownException {
        List<Duel> ret = new ArrayList<>();
        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            //@TODO: deal with java.net.ConnectException
            //Create Socket

            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Send message
            output.writeUTF(GET_NOTIFICATIONS);
            output.writeUTF(user);
            output.flush();

            //Populate array
            //How many will i receive?
            int it = input.readInt();

            for (int j = 1; j <= it; j++) {
                Duel temp = new Duel();

                //Native data in BD
                temp.setDuelID(input.readInt());
                temp.setStatus(input.readUTF());
                //temp.setTurn(input.readUTF());
                //temp.setScorePlayer1(input.readInt());
                //temp.setScorePlayer2(input.readInt());

                //Not so native stuff
                temp.setAdversary(input.readUTF());
                //temp.setPlayer1(input.readUTF());
                //temp.setPlayer2(input.readUTF());
                ret.add(temp);

            }

            skClient.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }

        //Populate array
        //Return Array
        return ret;
    }

    public int fetchNotificationNumber(String user) throws ServerDownException {
        int ret = 0;

        Socket skClient;
        DataInputStream input;
        DataOutputStream output;
        try {
            //@TODO: deal with java.net.ConnectException
            //Create Socket

            skClient = new Socket(HOST, PORT);
            skClient.setSoTimeout(TIME_OUT);
            input = new DataInputStream(new BufferedInputStream(skClient.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(skClient.getOutputStream()));

            //Send message
            output.writeUTF(GET_NOTIFICATION_SIZE);
            output.writeUTF(user);
            output.flush();

            //Populate array
            //How many will i receive?
            ret = input.readInt();

            skClient.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unknown Host");
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server fault 
            throw new ServerDownException("Server Down!");
        } catch (IOException ex) {
            throw new ServerDownException("Server Down!");
//            System.out.println("IO Exception");
        }

        return ret;

    }

    public Duel getDuel() {
        return duel;
    }

    public void setDuel(Duel duel) {
        this.duel = duel;
    }
}
