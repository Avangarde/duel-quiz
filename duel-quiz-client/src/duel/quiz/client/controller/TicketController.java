package duel.quiz.client.controller;

import duel.quiz.client.exception.ServerDownException;
import duel.quiz.client.model.Ticket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Date;

/**
 *
 * @author Edward
 */
public class TicketController {

    private static final int VALIDITY = 10000;
    private static final int TIME_OUT = 5000;
    private static final int port = 4444;

    public static Ticket getNewTicket() throws ServerDownException {
        Ticket ticket = null;
        try {
            MulticastSocket socket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName("230.0.0.1");
            socket.joinGroup(address);

            byte[] buf = new byte[256];

            // get load balancer address
            socket.setSoTimeout(TIME_OUT);
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                System.out.println("Waiting for load balancer address...");
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Addres: " + packet.getAddress());
                System.out.println("Load Balancer: " + received);
                
                ticket = new Ticket();
                ticket.setServerAddress(received);
                ticket.setLastConnexion(new Date());
                
            } catch (SocketTimeoutException ex) {
                throw new ServerDownException("Server down");
            }
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
