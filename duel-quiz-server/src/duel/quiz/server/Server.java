package duel.quiz.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import util.LoadBalancerFinder;

/**
 *
 * @author rojascle
 */
public class Server {
    
    private String address;
    private String status;
    
    private String loadBalancer;

    public Server() throws UnknownHostException {
        this.address = Inet4Address.getLocalHost().getHostAddress();
    }
    
    public void init() throws IOException {
        new LoadBalancerFinder(this).start();
        while (getLoadBalancer() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) { }
        }
        System.out.println("LOAD BALANCER FOUND!!!" + getLoadBalancer());
        DuelQuizServerMain.main(new String[0]);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    

    public String getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(String loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
    
    public static void main(String[] args) throws IOException {
        new Server().init();
    }
    
}
