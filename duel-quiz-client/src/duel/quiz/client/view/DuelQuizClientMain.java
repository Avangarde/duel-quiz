/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.client.view;

import duel.quiz.client.controller.PlayerController;
import duel.quiz.client.controller.QuestionController;
import duel.quiz.client.controller.TicketController;
import duel.quiz.client.exception.ServerDownException;
import duel.quiz.client.model.Answer;
import duel.quiz.client.model.Category;
import duel.quiz.client.model.Duel;
import duel.quiz.client.model.Player;
import duel.quiz.client.model.Question;
import duel.quiz.client.model.Ticket;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author corteshs
 */
public class DuelQuizClientMain {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String cls = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    public static Player currentPlayer = null;
    private static Ticket ticket = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int option = 0;

        //TODO Verify that there is a connection with the server first(?)
        while (option != 3) {
            mainMenu();
            option = readInteger();
            switch (option) {
                case 1:
                    if (signInOrSignUp(true)) {
                        gameRoomMenu();

                    }
                    break;
                case 2:
                    System.out.println("Sign Up");
                    signInOrSignUp(false);
                    break;
                case 3:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println(ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
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
        System.out.print("\nSelect a number:\t");
        try {
            String input = reader.readLine();
            option = Integer.parseInt(input);
        } catch (IOException ex) {
            Logger.getLogger(DuelQuizClientMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            System.out.println(ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
        }
        return option;
    }

    /**
     * Reads an integer from the console and returns it
     *
     * @return
     * @throws NumberFormatException
     */
    private static String readString() {
        String input = null;
        System.out.print("Write your selection:\t");
        try {
            input = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(DuelQuizClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
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
        boolean valid = false;
        String msg = "Connect";
        while (!signed && !failed) {
            try {
                if (ticket == null) {
                    ticket = TicketController.getNewTicket();
                } else {
                    ticket = TicketController.validateTicket(ticket);
                }
                Player player = getPlayer();
                PlayerController pc = new PlayerController(ticket.getServerAddress());
                if (signIn) {
                    signed = pc.signIn(player);
                    DuelQuizClientMain.currentPlayer = player;
                    msg = "Login";
                } else {
                    signed = pc.signUp(player);
                    msg = "SignUp";
                }
            } catch (ServerDownException ex) {
                System.err.println("Server down :(");
                ticket = null;
            }
            valid = false;
            while (!signed && !failed && !valid) {
                System.out.println(cls);
                if (!signIn) {
                    System.out.println("User Already Exists");
                }
                System.out.println("Cannot " + msg + " \n\t Try Again ? ");
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
                        System.out.println(cls + ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
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

    private static void gameRoomMenu() {
        System.out.println(cls + "****  *****  ***** Duel Quiz/Game Room *****  *****  ****");
        Integer option = 0;
        while (option != 5) {
            System.out.println(ConsoleColors.ANSI_PURPLE + "Current score: "
                    + DuelQuizClientMain.currentPlayer.getScore() + ConsoleColors.ANSI_RESET);
            getNotifications();
            System.out.println("What do you want to do?");
            System.out.println("1. See all notifications");
            System.out.println("2. See current games");
            System.out.println("3. Start a new game");
            System.out.println("4. Suggest question");
            System.out.println("5. Logout\n");
            //TODO if there is time
            //System.out.println("5. Show statistics (maybe)\n");

            option = readInteger();

            switch (option) {
                case 1:
                    System.out.println("Notifications");
                    displayNotifications();
                    break;
                case 2:
                    System.out.println("Current Games");
                    displayCurrentGames();
                    break;
                case 3:
                    System.out.println("Start a new game");
                    startNewGameMenu();
                    break;
                case 4:
                    System.out.println("Suggest a Question");
                    suggestQuestion();
                    break;
                case 5:

                    //TODO do formal logout with client-server communication
                    break;
                default:
                    System.out.println(ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
                    break;
            }
        }
    }

    private static void getNotifications() {
        int nots = new PlayerController(ticket.getServerAddress()).fetchNotificationNumber(currentPlayer.getUser());
        System.out.println(ConsoleColors.ANSI_GREEN + "You have "+ nots +" notification(s)" + ConsoleColors.ANSI_RESET);
    }

    private static void displayNotifications() {
        System.out.println(cls + "****  *****  ***** Duel Quiz/Game Room/Notifications *****  *****  ****");
        Integer option = 0;
        PlayerController controller = new PlayerController(ticket.getServerAddress());
        List<Duel> nots = controller.fetchNotifications(currentPlayer.getUser());

        //int currentIndex = 1;
        for (Duel each : nots) {
            //If the game is RUNNING and it is the players turn is displayed on red.
            //If the game is in WAITING and it is your turn, is displayed on green red and the user must decide whether or not he wants to play
            if (each.getStatus().equals(controller.RUNNING)) {
                System.out.println(each.getAdversary()  + " has played, it's your turn");
            }else if (each.getStatus().equals(controller.WAITING)){
                System.out.println(each.getAdversary()  + " has challenged to a duel, it's your turn");
            }
        }

        System.out.println("\nPress any key to continue");
        readInteger();
    }

    private static void displayCurrentGames() {
        System.out.println(cls + "****  *****  ***** Duel Quiz/Game Room/Current Games *****  *****  ****");
        Integer option = 0;
        List<Duel> games = fetchCurrentGames();
        //For each game, depending on its conditions display a different action.
        //If the game is ENDED display in gray, its score displayed :)
        //If the game is RUNNING but it's not the players turn is displayed on green
        //If the game is in WAITING but is not your turn, is displayed on green also

        //If the game is RUNNING and it is the players turn is displayed on red.
        //If the game is in WAITING and it is your turn, is displayed on green red and the user must decide whether or not he wants to play
        int exit = games.size() + 1;
        while (option != exit) {
            System.out.println("What do you want to do?");

            int currentIndex = 1;
            for (Duel each : games) {
                System.out.print(currentIndex + ", Play against " + each.getAdversary()
                        + " ( " + each.getPlayer1() + " " + each.getScorePlayer1() + " - "
                        + each.getPlayer2() + " " + each.getScorePlayer2() + ") - ");
                if (each.getStatus().equals(PlayerController.ENDED)) {
                    System.out.println(ConsoleColors.ANSI_PURPLE + "Ended" + ConsoleColors.ANSI_RESET);
                }
                if (each.getStatus().equals(PlayerController.RUNNING)) {
                    if (each.getTurn().equals(each.getAdversary())) {
                        System.out.println(ConsoleColors.ANSI_GREEN + "Waiting for Adversary" + ConsoleColors.ANSI_RESET);
                    } else if (each.getTurn().equals(currentPlayer.getUser())) {
                        System.out.println(ConsoleColors.ANSI_RED + "It's your turn" + ConsoleColors.ANSI_RESET);
                    }
                }
                if (each.getStatus().equals(PlayerController.WAITING)) {
                    if (each.getTurn().equals(each.getAdversary())) {
                        System.out.println(ConsoleColors.ANSI_GREEN + "Waiting for Adversary to accept" + ConsoleColors.ANSI_RESET);
                    } else if (each.getTurn().equals(currentPlayer.getUser())) {
                        System.out.println(ConsoleColors.ANSI_RED + "Accept the challenge to play" + ConsoleColors.ANSI_RESET);
                    }
                }
                currentIndex++;
            }

            System.out.println(exit + ". Go Back\n");

            option = readInteger();

            if (option > 0 && option < exit) {
                continueAgainstPlayer(games.get(option - 1));
            } else if (option == exit) {
                break;
            } else {
                System.out.println(ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
            }
        }
    }

    private static void startNewGameMenu() {
        System.out.println(cls + "****  *****  ***** Duel Quiz/Game Room/Start a New Game *****  *****  ****");
        Integer option = 0;

        while (option != 3) {
            System.out.println("What do you want to do?");

            System.out.println("1. New random player");
            System.out.println("2. Challenge a player");
            System.out.println("3. Return to Game Room\n");

            option = readInteger();

            switch (option) {
                case 1:
                    System.out.println(cls + "Random Challenge");
                    randomChallenge();
                    break;
                case 2:
                    System.out.println(cls + "Challenge a player");
                    String player;

                    player = pickPlayer();

                    if (player != null) {
                        challengePlayer(player);
                    } else {
                        System.out.println(cls + ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
                    }
                    break;
                case 3:
                    gameRoomMenu();
                    break;
            }
        }
    }

    private static List<Duel> fetchCurrentGames() {

        //TODO fetch actual gamers, this players are sorted in a particular form
        List<Duel> games = new ArrayList<Duel>();
        games = new PlayerController(ticket.getServerAddress()).getPlayerGames(currentPlayer.getUser());

        return games;
    }

    private static void challengePlayer(String opponent) {
        try {
            ticket = TicketController.validateTicket(ticket);

            PlayerController playerController = new PlayerController(ticket.getServerAddress());
            playerController.challengePlayer(currentPlayer.getUser(), opponent);
            System.out.println("You must wait until player " + opponent
                    + " accepts the challenge...");
            Thread.sleep(1000);
            gameRoomMenu();
        } catch (ServerDownException ex) {
            System.err.println("Server down :(");
            challengePlayer(opponent);
        } catch (InterruptedException ex) {
        }
    }

    private static void continueAgainstPlayer(Duel each) {
        if (each.getStatus().equals(PlayerController.ENDED)) {
            System.out.println("You can't play a finished match");
        }
        if (each.getStatus().equals(PlayerController.RUNNING)) {
            if (each.getTurn().equals(each.getAdversary())) {
                System.out.println(cls + ConsoleColors.ANSI_RED + "Wait for your adversary to play\n" + ConsoleColors.ANSI_RESET);
            } else if (each.getTurn().equals(currentPlayer.getUser())) {
                System.out.println(cls + ConsoleColors.ANSI_GREEN +  "You chose to battle " + each.getAdversary() + ConsoleColors.ANSI_GREEN);
                continueDuel(each);
            }
        }

        if (each.getStatus().equals(PlayerController.WAITING)) {
            if (each.getTurn().equals(each.getAdversary())) {
                System.out.println(cls + ConsoleColors.ANSI_RED + "Wait for your adversary to play\n" + ConsoleColors.ANSI_RESET);
            } else if (each.getTurn().equals(currentPlayer.getUser())) {
                System.out.println("Do you accept " + each.getAdversary() + "\'s Challenge?");
                System.out.println("1. Yes\n2. No");
                int input = readInteger();
                if (input == 1) {
                    System.out.println(cls + ConsoleColors.ANSI_GREEN + "You accepted the challenge\n" + ConsoleColors.ANSI_RESET);
                    continueDuel(each);
                } else {
                    System.out.println(cls + ConsoleColors.ANSI_PURPLE + "You refused the challenge\n" + ConsoleColors.ANSI_PURPLE);
                }
            }
        }
    }

    
    private static void randomChallenge() {
        try {
            ticket = TicketController.validateTicket(ticket);
        } catch (ServerDownException ex) {
            System.err.println("Server down :(");
            return;
        }
        //@TODO request the user for a random Player and get the questions
        PlayerController playerController = new PlayerController(ticket.getServerAddress());
        List<Category> round = playerController.obtainCategoryQuestionsAnswers();
        Duel duel = playerController.getDuel();
        //Important to pass to server
        Category categorySelected = pickCategory(round);

        answerAllQuestions(categorySelected);

        new QuestionController().transmitPlayedData(categorySelected, currentPlayer.getUser(),duel);

    }

    /**
     * Get a player list from server and user pick one
     *
     * @return player selected
     * @throws ServerDownException
     */
    private static String pickPlayer() {
        try {
            ticket = TicketController.validateTicket(ticket);

            PlayerController playerController = new PlayerController(ticket.getServerAddress());
            List<String> playerList = playerController.fetchPlayerList();

            int option = -1;
            String oponent = null;
            while (option < 1 || option > playerList.size()) {
                System.out.println("Choose a player to challenge: \n");
                for (int i = 0; i < playerList.size(); i++) {
                    System.out.println((i + 1) + ". " + playerList.get(i));
                }

                option = readInteger();
                if (option > 0 && option <= playerList.size()) {
                    oponent = playerList.get(option + 1);

                } else {
                    System.out.println(ConsoleColors.ANSI_RED + "Invalid Option: Choose a player" + ConsoleColors.ANSI_RESET);
                }
            }
            return oponent;
        } catch (ServerDownException ex) {
            System.err.println("Server down :(");
            return pickPlayer();
        }
    }

    private static void suggestQuestion() {
        //Select Category name //Number
        System.out.println(cls + "****  *****  ***** Duel Quiz/Game Room/SuggestQuestion *****  *****  ****");
        Integer option = 0;
        String categorySelected = "";
        String question = "";
        String rightAnswer = "";
        ArrayList<String> wrongAnswers = new ArrayList<String>();
        QuestionController controller = new QuestionController();
        List<String> categoryList = controller.fetchAllCategories();

        int exit = categoryList.size() + 1;
        while (option != exit) {

            System.out.println("Choose a category:\n");
            int currentIndex = 1;
            for (String each : categoryList) {
                System.out.println(currentIndex + ". " + each);
                currentIndex++;
            }

            System.out.println(exit + ". Go Back\n");

            option = readInteger();

            if (option >= 0 && option < exit) {
                categorySelected = categoryList.get(option);
                System.out.println("Enter question:\n");
                question = readString();
                System.out.println("Enter right answer:\n");
                rightAnswer = readString();
                System.out.println("Enter wrong answers:\n");
                System.out.println("1:\n");
                wrongAnswers.add(readString());
                System.out.println("2:\n");
                wrongAnswers.add(readString());
                System.out.println("3:\n");
                wrongAnswers.add(readString());

                controller.createNewQuestion(categorySelected, question, rightAnswer, wrongAnswers);

            } else if (option == exit) {
                break;
            } else {
                System.out.println(ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
            }
        }
        //Suggest Question
        //Correct Answer
        //Other answers

    }

    public static Ticket getTicket() {
        return ticket;
    }

    public static void setTicket(Ticket ticket) {
        DuelQuizClientMain.ticket = ticket;
    }

    /**
     *
     * @param question the question obtained from the server
     * @param isChallenger determines whether this client is the challenger or
     * the challenged
     */
    private static void answerIndividualQuestion(Question question, boolean isChallenger) {
        System.out.println(question.getQuestion() + "\n");
        int option = -1;
        while (option < 1 || option > 4) {
            int index = 1;
            for (Answer each : question.getAnswers()) {
                System.out.println(index + " " + each.getAnswer());
                index++;
            }
            System.out.println();
            option = readInteger();
        }

        option--;
        //If not challenger then challenged
        if (!isChallenger) {
            for (Answer each : question.getAnswers()) {
                if (each.isChosenByAdversary()) {
                    System.out.println("Your enemy chose \"" + each.getAnswer() + "\" and...");
                    if (each.isCorrect()) {
                        System.out.println(ConsoleColors.ANSI_GREEN + "HE IS RIGHT!" + ConsoleColors.ANSI_RESET);
                    } else {
                        System.out.println(ConsoleColors.ANSI_RED + "HE IS WRONG!!!!!!!!!!!" + ConsoleColors.ANSI_RESET);
                    }
                }
            }
        } else {
            question.getAnswers().get(option).setChosenByAdversary(true);
        }

        if (question.getAnswers().get(option).isCorrect()) {
            System.out.println(ConsoleColors.ANSI_GREEN + "YOU ARE RIGHT!" + ConsoleColors.ANSI_RESET);
        } else {
            System.out.println(ConsoleColors.ANSI_RED + "YOU ARE WRONG!!!!!!!!!!!" + ConsoleColors.ANSI_RESET);
        }

    }

    private static Category pickCategory(List<Category> round) {
        int option = -1;
        Category ret = new Category();

        while (option < 1 || option > 3) {

            System.out.println("Choose a category:\n");

            System.out.println("1. " + round.get(0).getName());
            System.out.println("2. " + round.get(1).getName());
            System.out.println("3. " + round.get(2).getName());
            //System.out.println("4. Go back");

            option = readInteger();

            if (option > 0 && option < 4) {
                ret = round.get(option - 1);
            } else {
                System.out.println(ConsoleColors.ANSI_RED + "Invalid Option: Choose a category" + ConsoleColors.ANSI_RESET);
            }
        }
        return ret;
    }

    private static void answerAllQuestions(Category categorySelected) {
        //@TODO answer the questions and send them to the Server
        System.out.println("\nFirst Question in " + categorySelected.getName() + ":\n");
        answerIndividualQuestion(categorySelected.getListQuestions().get(0), true);
        System.out.println("\nSecond Question in " + categorySelected.getName() + ":\n");
        answerIndividualQuestion(categorySelected.getListQuestions().get(1), true);
        System.out.println("\nSecond Question in " + categorySelected.getName() + ":\n");
        answerIndividualQuestion(categorySelected.getListQuestions().get(2), true);
    }

    private static void continueDuel(Duel duel) {
        try {
            ticket = TicketController.validateTicket(ticket);

            PlayerController playerController = new PlayerController(ticket.getServerAddress());
            List<Category> round = playerController.getQuestions();
            //Important to pass to server
            Category categorySelected = pickCategory(round);

            answerAllQuestions(categorySelected);

            new QuestionController().transmitPlayedData(categorySelected, currentPlayer.getUser(),duel);
        } catch (ServerDownException ex) {
            System.err.println("Server down :(");
            return;
        }
    }
}
