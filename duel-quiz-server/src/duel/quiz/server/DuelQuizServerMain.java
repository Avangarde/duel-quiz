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
        // TODO code application logic here

        //Puerto que se abrirá en esta máquina para responder a los clientes
        int PUERTO_SQRT = 5001;

        Socket socket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PUERTO_SQRT);
        } catch (Exception e) {
            System.err.println("Error");
            return;
        }
        //Mientras el programa esté funcionando
        while (true) {
            try {
                System.out.println("Ready to receive connections...");
                //Acepto una petición de conexión
                socket = serverSocket.accept();

                //Creo dos canales de comunicación con la conexión
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                System.out.println("Welcome");
                //System.out.print("Esperando valor... ");

                //Recibo el valor desde el cliente
                String message = in.readUTF();

                
                String response = treatMessage(message);
                
                System.out.print("Response..." + response);
                //Realizo la operación

                //La pongo en el canal de salida
                out.writeUTF(response);
                System.out.print("Sending response... ");
                //Se envía
                out.flush();
                System.out.println("OK");

                //Se cierra la petición de conexión llamada socket
                try {
                    socket.close();
                } catch (IOException ex) {
                }
            } catch (Exception e) {
                System.err.println("Cerrando la conexion del socket ...");
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
        
        switch (message){
            case "LOGIN":
                String user = "user"; //Obtain user (from message or message)
                String pass = "pass"; //Obtain pass
                
                //TODO Construct a model for the user
                //TODO Set Variables in DAO
                break;
            default:
                output = "ERROR1";
                break;
               
        }
        
        return output;
        
    }
}

