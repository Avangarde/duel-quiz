package duel.quiz.server.controller;

import duel.quiz.server.Server;
import duel.quiz.server.model.Ticket;
import java.util.List;

/**
 *
 * @author rojascle
 */
public class TicketController {
    
    private static final int VALIDITY = 300000;
    
    public static int validateTickets(Server server) {
        long currenTime = System.currentTimeMillis();
        final List<Ticket> tickets = server.getTickets();
        for (Ticket t : tickets) {
            long dif = currenTime - t.getLastConnexion().getTime();
            if (dif > VALIDITY) {
                tickets.remove(t);
            }
        }
        server.setNumberOfClients(tickets.size());
        return server.getNumberOfClients();
    }
    
}
