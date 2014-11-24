/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server;

import java.io.*;
import java.net.*;

/**
 *
 * @author corteshs
 */
public class DuelQuizServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Port to listen the clients
        int PORT_LISTENER = 5001;

        Socket socket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT_LISTENER);
        } catch (Exception e) {
            System.err.println("Error");
            return;
        }
        //Listening while running
        while (true) {
            try {
                System.out.println("Ready to receive connections...");
                //Accept a connection
                socket = serverSocket.accept();

                //Creates two streams of data
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                System.out.println("Welcome");

                //Recieving protocol message from client
                String message = in.readUTF();

                //Business logic
                String response = treatMessage(message);

                //Response
                System.out.print("Response..." + response);

                //Put on out channel (to client)
                out.writeUTF(response);
                System.out.print("Sending response... ");
                out.flush();
                System.out.println("OK");

                //Closing
                try {
                    socket.close();
                } catch (IOException ex) {
                }
            } catch (Exception e) {
                System.err.println("Closing...");
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }

    private static String treatMessage(String message) {

        //Method to treat the incoming messages.

        String output = "";

        switch (message) {
            case "LOGIN":
                String user = "user"; //Obtain user (from message or protocol)
                String pass = "pass"; //Obtain pass

                output = loginUser(user, pass);

                //TODO Construct a model for the user
                //TODO Set Variables in DAO
                break;
                
            case "REGISTER":
                break;
            default:
                //the message is not compliant with any other message
                output = "ERROR1";
                break;

        }

        return output;

    }

    private static String askForData(String dataHint) {

        return "";

    }

    private static String loginUser(String user, String pass) {
        //TODO Create a something and act on it

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
