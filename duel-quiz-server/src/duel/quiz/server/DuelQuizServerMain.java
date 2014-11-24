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
        int PORT_LISTENER = 5000;

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

                //Recieving protocol message from client/first handshake
                String message = in.readUTF();

                //Business logic
                Boolean response = treatMessage(out, in, message);

                //Response
                System.out.print("Response..." + response);

                //Put on out channel (to client)
                out.writeBoolean(response);
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

    private static Boolean treatMessage(DataOutputStream out, DataInputStream in, String message) throws IOException {

        //Method to treat the incoming messages.

        Boolean output = false;

        switch (message) {
            case "LOGIN":

                //out.writeUTF("Username?");
                //System.out.print("Asking for username... ");
                //out.flush();
                String user = in.readUTF(); //Obtain user (from message or protocol)
                System.out.println("user " + user);
                String pass = in.readUTF(); //Obtain pass
                System.out.println("pss " + pass);

                output = loginUser(user, pass);

                //TODO Construct a model for the user
                //TODO Set Variables in DAO
                break;

            case "REGISTER":
                break;
            case "CHALLENGE":
                break;
            case "RANDOMPLAY":
                break;
            default:
                //the message is not compliant with any other message
                
                break;
        }

        return output;

    }

    private static String askForData(String dataHint) {

        return "";

    }

    private static Boolean loginUser(String user, String pass) {
        //TODO Create a something and act on it

        return true; //To change body of generated methods, choose Tools | Templates.
    }
}
