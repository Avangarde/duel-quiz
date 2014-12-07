/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server;

import duel.quiz.server.controller.QuestionController;
import duel.quiz.server.controller.PlayerController;
import duel.quiz.server.controller.TicketController;
import duel.quiz.server.model.Category;
import duel.quiz.server.model.Player;
import duel.quiz.server.model.Ticket;
import duel.quiz.server.model.dao.DuelDAO;
import duel.quiz.server.model.dao.PlayerDAO;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corteshs
 */
public class DuelQuizServerMain implements Runnable {

    private static final String cls = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    private static Socket socket = null;
    private static ServerSocket serverSocket;
    private static Server server;
    //Port to listen the clients
    private static int PORT_LISTENER = 5000;
    private static final String GET_CLIENTS = "GET CLIENTS";
    private static final String ADD_CLIENT = "ADD CLIENT";
    private static final String SENDING_ROUND_DATA = "SENDINGROUNDDATA";
    private static final String GET_PLAYERS = "GET PLAYERS";
    private static final String GET_DUELS = "GET DUELS";
    private static final String GET_NOTIFICATION_SIZE = "GETNOTSIZE";
    private static final String GET_NOTIFICATIONS = "GETNOTIFICATIONS";
    private static PlayerController playerController;

    /**
     * @param args the command line arguments
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        //Initialize server
        server = new Server();
        server.init();

        try {
            serverSocket = new ServerSocket(PORT_LISTENER);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(0);
//            Logger.getLogger(DuelQuizServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //@TODO Update the availabilty list (Load at the beginning) (TO DISCUSS !!)
        playerController = new PlayerController(server);
        Thread thread;
        thread = new Thread(playerController);
        thread.start();
        //Listening while running
        while (true) {
            try {
                System.out.println(cls + "Ready to receive connections...");
                //Accept a connection
                socket = receiveConnection(socket, serverSocket);
                //Closing
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Cannot close socket");
                }
            } catch (IOException e) {
                System.err.println("Closing...");
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        System.out.println("Cannot close other socket");
                    }
                }
            }
        }
    }

    private static Socket receiveConnection(Socket socket, ServerSocket serverSocket) throws IOException {
        socket = serverSocket.accept();
        //Creates two streams of data
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        //Receiving protocol message from client/first handshake
        String message = in.readUTF();
        //Business logic
        Boolean response = treatMessage(out, in, message, socket);
        //Response
        out.writeBoolean(response);
        out.flush();
        return socket;
    }

    private static Boolean treatMessage(DataOutputStream out, DataInputStream in, String message, Socket socket) throws IOException {

        //Method to treat the incoming messages.
        Boolean output = false;
        String user;
        String adversary;
        int idUser;

        switch (message) {
            case "LOGIN":

                user = in.readUTF(); //Obtain user (from message or protocol)
                String pass = in.readUTF(); //Obtain pass
                Player player = playerController.loginPlayer(user, pass);
                if (player != null) {
                    Ticket t = server.getTicketByAddres(socket.getInetAddress().toString());
                    player.setTicket(t);
                    t.setPlayer(player);
                    out.writeInt(player.getScore());
                    output = true;
                } else {
                    out.writeInt(-1);
                    output = false;
                }
                break;

            case "REGISTER":

                user = in.readUTF(); //Obtain user (from message or protocol)
                pass = in.readUTF(); //Obtain pass
                output = PlayerController.registerPlayer(user, pass);
                break;

            case "CHALLENGE":
                String challenger = in.readUTF();
                String challenged = in.readUTF();
                //Save in the database the duel with the players (create returns the duel's id)
                int duelId = DuelDAO.create("En Attente", challenger);
                DuelDAO.linkPlayerToDuel(challenger, duelId);
                DuelDAO.linkPlayerToDuel(challenged, duelId);

                output = true;

                break;
            case "RANDOMPLAY":
                QuestionController.sendNewQuestions(out, in);
                /*@TODO when found, send it to the player (see if after the 
                 questions)*/

                String usr = in.readUTF();

                List<Player> players = new ArrayList<>();
                players = (players.isEmpty())
                        ? PlayerDAO.getAvailablePlayers()
                        : PlayerDAO.getUnavailablePlayers();

                Player adv = players.get(new Random().nextInt(players.size()));
                while (adv.getUser().equals(usr)) {
                    adv = players.get(new Random().nextInt(players.size()));
                }
                //Save in the database the duel with the players (create returns the duel's id)
                int idDuel = DuelDAO.create("En Attente", usr);
                DuelDAO.linkPlayerToDuel(usr, idDuel);
                DuelDAO.linkPlayerToDuel(adv.getUser(), idDuel);
                //Send to the user the adversary and the duel id
                out.writeUTF(adv.getUser());
                out.writeInt(idDuel);
                out.flush();
                break;
            case "REQUESTCATS":
                //Transmits all categories
                QuestionController.transmitCategories(true, out, in);
                break;
            case "NEWQUESTION":
                QuestionController.createNewQuestion(out, in);
                break;
            case GET_CLIENTS:
                int numberOfClients = TicketController.validateTickets(server);
                out.writeInt(numberOfClients);
                for (Ticket ti : server.getTickets()) {
                    out.writeUTF(ti.getClientAddress());
                }
                output = true;
                break;
            case ADD_CLIENT:
                String clientAddress = in.readUTF();
                System.out.println("Creating ticket...");
                Ticket ticket = new Ticket();
                ticket.setClientAddress(clientAddress);
                ticket.setLastConnexion(new Date());
                String username = in.readUTF();
                if (!username.isEmpty()) {
                    Player playr = PlayerDAO.findPlayer(username);
                    if (playr != null) {
                        ticket.setPlayer(playr);
                        PlayerDAO.setPlayerStatus(username, true);
                    }
                }
                server.tickets.add(ticket);
                output = true;
                break;
            case SENDING_ROUND_DATA:
                user = in.readUTF();
                adversary = in.readUTF();
                idDuel = in.readInt();
                Category c = QuestionController.receivePlayedData(1, out, in);
                break;

            //Updates the turn to make the adversary the next one to answer
            //DuelDAO.updateTurn(idDuel,adversary);
            case GET_PLAYERS:
                List<String> playrs = playerController.getPlayers();
                out.writeInt(playrs.size());
                for (String p : playrs) {
                    out.writeUTF(p);
                }
                output = true;
                break;
            case GET_DUELS:
                user = in.readUTF();
                playerController.getPlayerGames(user, out, in);
                break;

            case GET_NOTIFICATIONS:
                user = in.readUTF();
                playerController.getNotifications(user, out, in);
                break;

            case GET_NOTIFICATION_SIZE:
                user = in.readUTF();
                playerController.getNotificationNumber(user, out, in);
                break;


            default:
                //the message is not compliant with any other message
                break;
        }
        return output;
    }

    @Override
    public void run() {
        try {
            receiveConnection(socket, serverSocket);
        } catch (IOException ex) {
            Logger.getLogger(DuelQuizServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
