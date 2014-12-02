package util;

import duel.quiz.server.LoadBalancerThread;
import duel.quiz.server.Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author rojascle
 */
public class LoadBalancerFinder extends Thread {

    private Server sourceServer;
    private final int TIME_OUT = 5000;
    private final int port = 4444;


    public LoadBalancerFinder(Server sourceServer) throws IOException {
        this("LoadBalancerFinder", sourceServer);
    }

    public LoadBalancerFinder(String name, Server sourceServer) throws IOException {
        super(name);
        this.sourceServer = sourceServer;
    }

    @Override
    public void run() {
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
                sourceServer.setLoadBalancerAddress(received);
                socket.leaveGroup(address);
                socket.close();
                
                //@TODO Update Database (If needed)
                new LoadBalancerThread(sourceServer).start();
                
            } catch (SocketTimeoutException ex) {
                //If no response, the source server is the load balancer
                sourceServer.setLoadBalancerAddress(sourceServer.getAddress());
                sourceServer.setLoadBalancer(Boolean.TRUE);
                //The server list is initialized and the server is added
                sourceServer.setServers(new TreeSet<Server>());
                sourceServer.getServers().add(sourceServer);
                
                new LoadBalancerThread(sourceServer).start();
                //@TODO Start Fault detector
                
                //Broadcast load balancer address
                buf = new byte[256];                                
                while (sourceServer.getLoadBalancerAddress() != null) {
                    buf = sourceServer.getLoadBalancerAddress().getBytes();
                    InetAddress group = InetAddress.getByName("230.0.0.1");
                    packet = new DatagramPacket(buf, buf.length, group, port);
                    System.out.println("Sending load balancer address: " + sourceServer.getLoadBalancerAddress());
                    socket.send(packet);
                    try {
                        sleep((long) (Math.random() * TIME_OUT));
                    } catch (InterruptedException e) {
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
