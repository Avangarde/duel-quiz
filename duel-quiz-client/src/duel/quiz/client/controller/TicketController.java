package duel.quiz.client.controller;

import duel.quiz.client.exception.ServerDownException;
import duel.quiz.client.model.Ticket;
import duel.quiz.client.view.DuelQuizClientMain;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;

/**
 *
 * @author Edward
 */
public class TicketController {

    private static final int VALIDITY = 300000;
    private static final int TIME_OUT = 60000;
    private static final int MULTICAST_PORT = 4444;
    private static final String LOAD_BALANCER_HOST = "224.0.0.1";
    private static final int LOAD_BALANCER_PORT = 4466;
    private static final String GET_SERVER = "GET SERVER";
    private static final String NO_LOGIN = "";

    private static String getLoadBalancerAddress() throws ServerDownException {
        String loadBalancerAddress = null;
        try {
            MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
            InetAddress address = InetAddress.getByName(LOAD_BALANCER_HOST);
            socket.joinGroup(address);

            byte[] buf = new byte[256];

            // get load balancer address
            socket.setSoTimeout(TIME_OUT);
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                //System.out.println("Waiting for load balancer address...");
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //System.out.println("Load Balancer: " + received);

                loadBalancerAddress = received;

            } catch (SocketTimeoutException ex) {
                throw new ServerDownException("Server down");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return loadBalancerAddress;
    }

    public static Ticket getNewTicket() throws ServerDownException {
        Ticket ticket = null;
        Socket socket = null;
        try {
            socket = new Socket(getLoadBalancerAddress(), LOAD_BALANCER_PORT);
            socket.setSoTimeout(TIME_OUT);
            DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            //Sending Get Server Message
            output.writeUTF(GET_SERVER);
            if (DuelQuizClientMain.currentPlayer != null) {
                output.writeUTF(DuelQuizClientMain.currentPlayer.getUser());
            } else {
                output.writeUTF(NO_LOGIN);
            }
            //System.out.print("Getting a new server address...");
            output.flush();
            String address = input.readUTF();
            System.out.println("..." + address);
            ticket = new Ticket();
            ticket.setServerAddress(address);
            ticket.setLastConnexion(new Date());
        } catch (SocketTimeoutException ex) {
            throw new ServerDownException("Server down");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ticket;
    }

    public static Ticket validateTicket(Ticket ticket) throws ServerDownException {
        long dif = System.currentTimeMillis() - ticket.getLastConnexion().getTime();
        if (dif > VALIDITY) {
            return getNewTicket();
        }
        return ticket;
    }
}
