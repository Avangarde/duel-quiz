package util;

import duel.quiz.server.FaultDetectorThread;
import duel.quiz.server.LoadBalancerThread;
import duel.quiz.server.Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.TreeSet;

/**
 *
 * @author rojascle
 */
public class LoadBalancerFinder extends Thread {

    private Server sourceServer;
    private final int TIME_OUT = 5000;
    private final int PORT = 4444;
    private static final String MULTICAST_HOST = "224.0.0.1";


    public LoadBalancerFinder(Server sourceServer) {
        this("LoadBalancerFinder", sourceServer);
    }

    public LoadBalancerFinder(String name, Server sourceServer) {
        super(name);
        this.sourceServer = sourceServer;
    }

    @Override
    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress address = InetAddress.getByName(MULTICAST_HOST);
            byte[] address1 = address.getAddress();;
            socket.joinGroup(address);

            byte[] buf = new byte[256];

            // get load balancer address
            socket.setSoTimeout(TIME_OUT);
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                System.out.println("Waiting for load balancer address...");
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Load Balancer: " + received);
                sourceServer.setLoadBalancerAddress(received);
                socket.leaveGroup(address);
                socket.close();
                
                //@TODO Update Database (If needed)
                //Register server in load Balancer
                new LoadBalancerThread(sourceServer).start();
                
                //Thread to response to fault detector
                new FaultDetectorThread(sourceServer).start();
                
            } catch (SocketTimeoutException ex) {
                //If no response, the source server is the load balancer
                sourceServer.setLoadBalancerAddress(sourceServer.getAddress());
                sourceServer.setLoadBalancer(Boolean.TRUE);
                //The server list is initialized and the server is added
                sourceServer.setServers(new TreeSet<Server>());
//                sourceServer.getServers().add(sourceServer);
                
                //Start Load Balancer
                new LoadBalancerThread(sourceServer).start();

                //Start Fault Detector
                new FaultDetectorThread(sourceServer).start();
                
                //Broadcast load balancer address
                buf = new byte[256];                                
                while (sourceServer.getLoadBalancerAddress() != null) {
                    buf = sourceServer.getLoadBalancerAddress().getBytes();
                    InetAddress group = InetAddress.getByName(MULTICAST_HOST);
                    packet = new DatagramPacket(buf, buf.length, group, PORT);
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
