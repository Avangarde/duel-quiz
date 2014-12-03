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
    private static Player currentPlayer = null;
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
        System.out.print("Select a number:\t");
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
            }

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
        System.out.println(ConsoleColors.ANSI_GREEN + "You have new notifications" + ConsoleColors.ANSI_RESET);
    }

    private static void displayNotifications() {
        System.out.println(cls + "****  *****  ***** Duel Quiz/Game Room/Notifications *****  *****  ****");
        Integer option = 0;
        List<String> nots = fetchNotifications();

        int currentIndex = 1;
        for (String each : nots) {
            System.out.println(each);
        }

        System.out.println("\nPress any key to continue");
        readInteger();
    }

    private static void displayCurrentGames() {
        System.out.println(cls + "****  *****  ***** Duel Quiz/Game Room/Current Games *****  *****  ****");
        Integer option = 0;
        List<String> games = fetchCurrentGames();
        int exit = games.size() + 1;
        while (option != exit) {
            System.out.println("What do you want to do?");

            int currentIndex = 1;
            for (String each : games) {
                System.out.println(currentIndex + ", Play against " + each);
                currentIndex++;
            }

            System.out.println(exit + ". Go Back\n");

            option = readInteger();

            if (option >= 0 && option < exit) {
                continueAgainstPlayer(games.get(option));
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
            System.out.println("2. Challenge a friend");
            System.out.println("3. Go Back\n");

            option = readInteger();

            switch (option) {
                case 1:
                    System.out.println("Random Challenge");
                    randomChallenge();
                    break;
                case 2:
                    System.out.println("Challenge a friend");
                    displayListPlayers();
                    System.out.println("Enter name of player");
                    String player = readString();
                    if (player != null) {
                        challengePlayer(player);
                    } else {
                        System.out.println(cls + ConsoleColors.ANSI_RED + "Invalid Option" + ConsoleColors.ANSI_RESET);
                    }
                    break;
                case 3:
                    break;
            }
        }
    }

    private static List<String> fetchCurrentGames() {

        //TODO fetch actual gamers
        List<String> games = new ArrayList<String>();
        games.add("Sergio");
        games.add("Juan");
        games.add("Edward");
        return games;
    }

    private static void challengePlayer(String get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void continueAgainstPlayer(String get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static List<String> fetchNotifications() {
        //TODO fech actual nots
        List<String> games = new ArrayList<String>();
        games.add("Sergio challenged you");
        games.add("Juan played his turn");
        games.add("The match against edward is ended");
        return games;
    }

    private static void randomChallenge() {
        try {
            if (ticket == null) {
                ticket = TicketController.getNewTicket();
            } else {
                ticket = TicketController.validateTicket(ticket);
            }
        } catch (ServerDownException ex) {
            System.err.println("Server down :(");
            return;
        }
        //@TODO request the user for a random Player and get the questions
        PlayerController playerController = new PlayerController(ticket.getServerAddress());
        List<Category> round = playerController.obtainCategoryQuestionsAnswers();
        //Important to pass to server
        Category categorySelected = new Category();
        //@TODO Menu category pick

        int option = -1;

        while (option < 1 || option > 3) {

            System.out.println("Choose a category:\n");

            System.out.println("1. " + round.get(1).getName());
            System.out.println("2. " + round.get(2).getName());
            System.out.println("3. " + round.get(3).getName());
            //System.out.println("4. Go back");

            option = readInteger();

            if (option > 0 && option < 4) {
                categorySelected = round.get(option);

            } else {
                System.out.println(ConsoleColors.ANSI_RED + "Invalid Option: Choose a category" + ConsoleColors.ANSI_RESET);

            }
        }
        System.out.println("You chose Pikachu");
        //@TODO answer the questions and send them to the Server
        System.out.println("First Question in " + categorySelected.getName() + ":\n");
        answerQuestion(categorySelected.getListQuestions().get(1), true);
        System.out.println("Second Question in " + categorySelected.getName() + ":\n");
        answerQuestion(categorySelected.getListQuestions().get(2), true);
        System.out.println("Second Question in " + categorySelected.getName() + ":\n");
        answerQuestion(categorySelected.getListQuestions().get(3), true);



    }
    //@TODO Server must request to the clients until someone accepts
    //@TODO set in the local BD the status of the match (random Player - En attente)
    //@TODO when in the server side someone accepts, update the local BD with the name and the score
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    private static void displayListPlayers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    private static void answerQuestion(Question question, boolean isChallenger) {
        System.out.println(question.getQuestion() + "\n");
        int option = -1;
        while (option < 1 || option > 4) {
            int index = 1;
            for (Answer each : question.getAnswers()) {
                System.out.println(index + " " + each + "\n");
                index++;
            }

            option = readInteger();
        }

        //Si no eres el retador eres el retado
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
        }else{
            question.getAnswers().get(option).setChosenByAdversary(true);
        }

        if (question.getAnswers().get(option).isCorrect()) {
            System.out.println(ConsoleColors.ANSI_GREEN + "YOU ARE RIGHT!" + ConsoleColors.ANSI_RESET);
        } else {
            System.out.println(ConsoleColors.ANSI_RED + "YOU ARE WRONG!!!!!!!!!!!" + ConsoleColors.ANSI_RESET);
        }

    }
}