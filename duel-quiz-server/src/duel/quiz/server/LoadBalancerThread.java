package duel.quiz.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edward
 */
public class LoadBalancerThread extends Thread {

    private Server server;
    private Socket socket = null;
    private ServerSocket serverSocket;
    private final int port = 4466;
    private final int TIME_OUT = 5000;
    private final String DATE_FORMAT = Server.DATE_FORMAT;
    private static final String REGISTER = "REGISTER";
    private static final String GET_SERVER = "GET SERVER";
    private static final String GET_CLIENTS = "GET CLIENTS";
    private static final String ADD_CLIENT = "ADD CLIENT";
    private static final String GET_PLAYERS = "GET PLAYERS";
    public static final String DISPONIBLE = "DISPONIBLE";

    public LoadBalancerThread(Server server) throws IOException {
        this("FaultDetectorThread", server);
    }

    public LoadBalancerThread(String name, Server server) throws IOException {
        super(name);
        this.server = server;
    }

    @Override
    public void run() {
        if (server.isLoadBalancer()) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            while (server.isLoadBalancer()) {
                try {
                    System.out.println("Load Balancer ready to receive connections...");
                    //Accept a connection
                    socket = receiveConnection(socket, serverSocket);
                    //Closing
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        System.out.println("Cannot close socket");
                    }
                } catch (SocketTimeoutException ex) {
                    //@TODO Server fault 
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
        } else {
            try {
                socket = new Socket(server.getLoadBalancerAddress(), port);
                socket.setSoTimeout(TIME_OUT);
                DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                //Sending Register Message
                output.writeUTF(REGISTER);
                output.writeUTF(server.getAddress());
                //output.writeInt(server.getNumberOfClients());
                output.writeUTF(server.getLastConnexion());
                System.out.println("Sending Register Message-> address: " + server.getAddress() + " lastConnexion: " + server.getLastConnexion());
                output.flush();

                boolean updateBD = input.readBoolean();
                if (updateBD) {
                    //@TODO Read list of instructions to update DB
                    //and update DB ?
                    System.out.println("Database Update needed...");
                }
                //Send new date of Conexion and state
                server.setStatus(Server.DISPONIBLE);
                server.setLastConnexion(getCurrentDate());
                output.writeUTF(server.getLastConnexion());
                output.writeUTF(server.getStatus());
                System.out.println("Sending new date of connexion: " + server.getLastConnexion());
                output.flush();

                socket.close();
            } catch (SocketTimeoutException ex) {
                //@TODO Load Balancer fault ?
            } catch (IOException ex) {
                Logger.getLogger(LoadBalancerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Socket receiveConnection(Socket socket, ServerSocket serverSocket) throws IOException {
        socket = serverSocket.accept();
        socket.setSoTimeout(TIME_OUT);
        //Creates two streams of data
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        System.out.println("Treating message from : " + socket.getInetAddress());
        //Receiving protocol message from client/first handshake
        String message = in.readUTF();
        //Business logic
        treatMessage(out, in, message, socket);
        return socket;
    }

    private void treatMessage(DataOutputStream out, DataInputStream in, String message, Socket socket) throws IOException {

        //Method to treat the incoming messages.
        switch (message) {
            case REGISTER:
                String address = in.readUTF();
                String lastConnexion = in.readUTF();
                Server newServer = new Server(address);
                newServer.setLastConnexion(lastConnexion);

                if (server.getServers().contains(newServer)) {
                    server.getServers().remove(newServer);
                }
                System.out.println("Registering new server: " + newServer.getAddress());
                server.getServers().add(newServer);

                boolean update = updateNeeded(newServer);
                out.writeBoolean(update);
                if (update) {
                    //@TODO Send list of instructions to update DB                    
                }
                System.out.println("Sending response... update-> " + update);
                out.flush();
                String date = in.readUTF();
                String status = in.readUTF();
                System.out.println("Updating server info -> status: " + status + " last Connexion: " + date);
                newServer.setLastConnexion(date);
                newServer.setStatus(status);

                //@TODO send info to others servers ? 
                break;

            case GET_SERVER:
                System.out.println("Selecting server...");
                String username = in.readUTF();
                Server minCharged = selectServer(socket, username);

                System.out.println("Sending server address: " + minCharged.getAddress()
                        + " to client " + socket.getInetAddress());
                out.writeUTF(minCharged.getAddress());
                out.flush();
                break;
            default:
                //the message is not compliant with any other message
                break;
        }
    }

    private boolean updateNeeded(Server newServer) {
        return server.getLastConnexionDate().before(newServer.getLastConnexionDate());
    }

    private String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date date = new GregorianCalendar().getTime();
        return format.format(date);
    }

    private Server selectServer(Socket socket, String username) {
        Iterator<Server> iterator = server.getServers().iterator();
        Server minCharged = server;
        Socket socketToServer;
        int portToServer = 5000;
        while (iterator.hasNext()) {
            Server current = iterator.next();
            if (current.getStatus().equals(DISPONIBLE)) {
                try {
                    socketToServer = new Socket(current.getAddress(), portToServer);
                    socketToServer.setSoTimeout(TIME_OUT);
                    DataInputStream input = new DataInputStream(new BufferedInputStream(socketToServer.getInputStream()));
                    DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socketToServer.getOutputStream()));

                    //Sending request to server
                    output.writeUTF(GET_CLIENTS);
                    System.out.println("Getting clients from : " + current.getAddress());
                    output.flush();
                    int numClients = input.readInt();
                    current.setNumberOfClients(numClients);
                    for (int i = 0; i < numClients; i++) {
                        input.readUTF();
                    }
                    input.readBoolean();
                    
                    if (current.getNumberOfClients() < minCharged.getNumberOfClients()) {
                        minCharged = current;
                    }
                    socketToServer.close();
                } catch (SocketTimeoutException | ConnectException ex) {
                    //@TODO Server Down
                    System.err.println("Server down: " + current.getAddress());
                } catch (IOException ex) {
                    Logger.getLogger(FaultDetectorThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Send message to update number of clients of server
        String clientAddress = socket.getInetAddress().toString();
        try {
            socketToServer = new Socket(minCharged.getAddress(), portToServer);
            socketToServer.setSoTimeout(TIME_OUT);
            DataInputStream input = new DataInputStream(new BufferedInputStream(socketToServer.getInputStream()));
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socketToServer.getOutputStream()));

            //Sending message to server
            output.writeUTF(ADD_CLIENT);
            System.out.println("Adding client " + clientAddress + 
                    " to server : " + minCharged.getAddress());
            output.writeUTF(clientAddress);
            output.writeUTF(username);
            output.flush();

            if (input.readBoolean()) {
                minCharged.setNumberOfClients(minCharged.getNumberOfClients() + 1);
            }
            

            socketToServer.close();
        } catch (SocketTimeoutException | ConnectException ex) {
            //@TODO Server Down
            System.err.println("Server down: " + minCharged.getAddress());
        } catch (IOException ex) {
            Logger.getLogger(FaultDetectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return minCharged;
    }
}
