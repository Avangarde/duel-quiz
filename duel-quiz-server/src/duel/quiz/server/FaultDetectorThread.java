package duel.quiz.server;

import duel.quiz.server.Server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.LoadBalancerFinder;

/**
 *
 * @author Edward
 */
public class FaultDetectorThread extends Thread {

    private Server server;
    private Socket socket = null;
    private ServerSocket serverSocket;
    private final int TIME_OUT = 5000;
    private final int port = 4455;
    private final String PING = "PING";
    
    private boolean running;

    public FaultDetectorThread(Server server) throws IOException {
        this("FaultDetectorThread", server);
    }

    public FaultDetectorThread(String name, Server server) throws IOException {
        super(name);
        this.server = server;        
    }

    @Override
    public void run() {
        this.running = true;
        while (running) {
            if (server.isLoadBalancer()) {
                Iterator<Server> iterator = server.getServers().iterator();
                while (iterator.hasNext()) {
                    Server current = iterator.next();
                    try {
                        socket = new Socket(current.getAddress(), port);
                        socket.setSoTimeout(TIME_OUT);
                        DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                        //Sending ping to server
                        output.writeUTF(PING);
                        System.out.println("Ping to : " + current.getAddress());
                        output.flush();
                        input.readBoolean();

                        socket.close();
                    } catch (SocketTimeoutException | ConnectException ex) {
                        //@TODO Server Down
                        System.err.println("Server down: " + current.getAddress());
                    } catch (IOException ex) {
                        Logger.getLogger(FaultDetectorThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    sleep((long) (Math.random() * TIME_OUT));
                } catch (InterruptedException e) {
                }
            } else {
                try {
                    serverSocket = new ServerSocket(port);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                try {
                    System.out.println("Listening Fault Detector...");
                    serverSocket.setSoTimeout(TIME_OUT);
                    socket = receiveConnection(socket, serverSocket);
                    //Closing
                    try {
                        socket.close();
                        serverSocket.close();
                    } catch (IOException ex) {
                        System.err.println("Cannot close socket");
                    }
                } catch (SocketTimeoutException ex) {
                    //@TODO Load Balancer fault 
                    System.err.println("Load Balancer Down");
                    
                    running = false;
                    new LoadBalancerFinder(server).start();
                } catch (Exception e) {
                    System.err.println("Closing...");
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            System.err.println("Cannot close other socket");
                        }
                    }
                } finally {
                    if (serverSocket != null) {
                        try {
                            serverSocket.close();
                        } catch (IOException ex) {
                            System.err.println("Cannot close server socket");
                        }
                    }
                }
            }
        }
        try {
            this.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(FaultDetectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Socket receiveConnection(Socket socket, ServerSocket serverSocket) throws IOException {
        socket = serverSocket.accept();
        //Creates two streams of data
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        System.out.println("Receiving ping from fault detector : " + socket.getInetAddress());
        //Receiving protocol message from client/first handshake
        String message = in.readUTF();
        //Business logic
        treatMessage(out, in, message);
        return socket;
    }

    private void treatMessage(DataOutputStream out, DataInputStream in, String message) throws IOException {

        //Method to treat the incoming messages.
        switch (message) {
            case PING:
                out.writeBoolean(true);
                out.flush();
                break;

            default:
                //the message is not compliant with any other message
                break;
        }
    }

}
