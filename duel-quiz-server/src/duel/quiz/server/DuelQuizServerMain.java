/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server;

import duel.quiz.server.controller.LoginController;
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

    static Socket socket = null;
    static ServerSocket serverSocket;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Port to listen the clients
        int PORT_LISTENER = 5000;
        try {
            serverSocket = new ServerSocket(PORT_LISTENER);
        } catch (IOException ex) {
            System.out.println("IO Exception");
//            Logger.getLogger(DuelQuizServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //@TODO Update the availabilty list (Load at the beginning)
        Thread thread = new Thread(new LoginController());
            thread.start();
        //Listening while running
        while (true) {
            try {
                System.out.println("Ready to receive connections...");
                //Accept a connection
                socket = receiveConnection(socket, serverSocket);
                //Closing
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Cannot close socket");
                }
            } catch (Exception e) {
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
        System.out.println("Welcome");
        //Receiving protocol message from client/first handshake
        String message = in.readUTF();
        //Business logic
        Boolean response = treatMessage(out, in, message);
        //Response
        System.out.println("Response..." + response);
        out.writeBoolean(response);
        System.out.println("Sending response... ");
        out.flush();
        System.out.println("OK");
        return socket;
    }

    private static Boolean treatMessage(DataOutputStream out, DataInputStream in, String message) throws IOException {

        //Method to treat the incoming messages.
        Boolean output = false;

        switch (message) {
            case "LOGIN":

                String user = in.readUTF(); //Obtain user (from message or protocol)
                String pass = in.readUTF(); //Obtain pass
                Player player = LoginController.loginUser(user, pass);
                //@TODO GRAVE Fix login when incorrect pass
                if (player != null) {
                    out.writeInt(player.getScore());
                    output = true;
                } else {
                    output = false;

                }

                //TODO Construct a model for the user
                //TODO Set Variables in DAO
                break;

            case "REGISTER":

                user = in.readUTF(); //Obtain user (from message or protocol)
                pass = in.readUTF(); //Obtain pass
                output = LoginController.registerUser(user, pass);
                break;

            case "CHALLENGE":
                String userChallenged = in.readUTF();
                break;
            case "RANDOMPLAY":
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
