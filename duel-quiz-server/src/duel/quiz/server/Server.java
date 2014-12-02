package duel.quiz.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeSet;
import util.LoadBalancerFinder;

/**
 *
 * @author rojascle
 */
public class Server implements Comparable<Server> {

    private String address;
    private String status;

    private String loadBalancerAddress;
    private Boolean loadBalancer;

    private Date lastConnexion;
    private int numberOfClients;

    private LinkedList<String> queueDB;
    public static final String NO_DISPONIBLE = "NO_DISPONIBLE";
    public static final String DISPONIBLE = "DISPONIBLE";
    
    public static final String DATE_FORMAT = "dd/MM/YYYY HH:mm:ss";

    //Only for loadBalancer
    private TreeSet<Server> servers;

    public Server() throws UnknownHostException {
        this.address = Inet4Address.getLocalHost().getHostAddress();
        this.loadBalancer = false;
        this.status = NO_DISPONIBLE;
        servers = null;
        //@TODO: Get that from somewhere
        lastConnexion = new Date();
    }
    
    public Server(String address) {
        this.address = address;
        this.loadBalancer = false;
        this.status = NO_DISPONIBLE;
        servers = null;
    }

    public void init() throws IOException {
        new LoadBalancerFinder(this).start();
        while (getLoadBalancerAddress() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        System.out.println("LOAD BALANCER FOUND!!!" + getLoadBalancerAddress());
        setStatus(DISPONIBLE);
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

    public String getLoadBalancerAddress() {
        return loadBalancerAddress;
    }

    public void setLoadBalancerAddress(String loadBalancerAddress) {
        this.loadBalancerAddress = loadBalancerAddress;
    }

    public Boolean isLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(Boolean loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public TreeSet<Server> getServers() {
        return servers;
    }

    public void setServers(TreeSet<Server> servers) {
        this.servers = servers;
    }

    public String getLastConnexion() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(lastConnexion);
    }
    
    public Date getLastConnexionDate() {
        return lastConnexion;
    }

    public void setLastConnexion(String lastConnexion) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            this.lastConnexion = format.parse(lastConnexion);
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLastConnexion(Date lastConnexion) {
        this.lastConnexion = lastConnexion;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Server)) {
            return false;
        }
        Server other = (Server) obj;
        if ((this.address == null && other.address != null)
                || (this.address != null && !this.address.equals(other.address))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.address);
        return hash;
    }    

    @Override
    public int compareTo(Server o) {
        return getAddress().compareTo(o.getAddress());
    }

    public static void main(String[] args) throws IOException {
        new Server().init();
    }
    
}
