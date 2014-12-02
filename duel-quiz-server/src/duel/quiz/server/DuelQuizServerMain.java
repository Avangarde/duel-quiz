/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server;

import duel.quiz.server.controller.QuestionController;
import duel.quiz.server.controller.PlayerController;
import duel.quiz.server.model.Player;
import java.io.*;
import java.net.*;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Port to listen the clients
        int PORT_LISTENER = 5000;
        try {
            serverSocket = new ServerSocket(PORT_LISTENER);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(0);
//            Logger.getLogger(DuelQuizServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //@TODO Update the availabilty list (Load at the beginning) (TO DISCUSS !!)
        PlayerController lc = new PlayerController();
        Thread thread = new Thread(lc);
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
        Boolean response = treatMessage(out, in, message);
        //Response
        out.writeBoolean(response);
        out.flush();
        return socket;
    }

    private static Boolean treatMessage(DataOutputStream out, DataInputStream in, String message) throws IOException {

        //Method to treat the incoming messages.
        Boolean output = false;

        switch (message) {
            case "LOGIN":

                String user = in.readUTF(); //Obtain user (from message or protocol)
                String pass = in.readUTF(); //Obtain pass
                Player player = PlayerController.loginPlayer(user, pass);
                if (player != null) {
                    out.writeInt(player.getScore());
                    output = true;
                } else {
                    out.writeInt(-1);
                    output = false;
                }

                //TODO Construct a model for the user
                //TODO Set Variables in DAO
                break;

            case "REGISTER":

                user = in.readUTF(); //Obtain user (from message or protocol)
                pass = in.readUTF(); //Obtain pass
                output = PlayerController.registerPlayer(user, pass);
                break;

            case "CHALLENGE":
                String userChallenged = in.readUTF();
                break;
            case "RANDOMPLAY":
                QuestionController.sendNewQuestions(out,in);
                //@TODO Find an adversary in a new Thread
                /*@TODO when found, send it to the player (see if after the 
                questions)*/
                break;
            case "REQUESTCATS":
                //Transmits all categories
                QuestionController.transmitCategories(true, out, in);
                break;
            case "NEWQUESTION":
                QuestionController.createNewQuestion(out, in);
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
