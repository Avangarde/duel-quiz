package duel.quiz.server.model.dao;

import duel.quiz.server.model.Player;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.closeConnection;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author martijua
 */
public class PlayerDAO extends AbstractDataBaseDAO {
    
    private static final String AVAILABLE = "AVAILABLE";
    private static final String UNAVAILABLE = "UNAVAILABLE";

    public static Player getPlayer(String user, String pass) {
        Player player = null;
        Connection connection = connect();


        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM Player "
                            + "WHERE username = ? AND password = ?");
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                player = new Player(result.getString("username"), "", result.getString("state"), result.getInt("score"));
            }
            result.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return player;
    }

    public static boolean setPlayerStatus(String user, boolean isOnline) {
        String status;
        if (isOnline) {
            status = AVAILABLE;
        } else {
            status = UNAVAILABLE;
        }


        Connection conn = null;
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Player SET state = ?"
                    + "WHERE username = ?");
            stmt.setString(1, status);
            stmt.setString(2, user);
            int rset = stmt.executeUpdate();
           
            stmt.close();
        } catch (SQLException e) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                closeConnection(conn);
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;

    }

    public static boolean persist(String user, String pass) {
        Connection connection = connect();
        boolean ret = false;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO player VALUES (?, ?, 'AVAILABLE', '0')");
            statement.setString(1, user);
            statement.setString(2, pass);
            statement.executeQuery();
            statement.close();
            connection.close();
            ret = true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User already exists");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return ret;
    }
    
    public static Player findPlayer(String user) {
        Player player = null;
        Connection connection = connect();


        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM Player WHERE username = ?");
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                player = new Player(result.getString("username"), "", result.getString("state"), result.getInt("score"));
            }
            result.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
            try {
                closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return player;
    }
    
    public static List<Player> getAvailablePlayers() {
        List<Player> players = new ArrayList<>();
        Connection connection = connect();

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM Player "
                    + "WHERE state = ?");
            statement.setString(1, AVAILABLE);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                players.add(new Player(result.getString("username"), "", result.getString("state"), result.getInt("score")));
            }
            result.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return players;
    }
    
    public static List<Player> getUnavailablePlayers() {
        List<Player> players = new ArrayList<>();
        Connection connection = connect();

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM Player "
                    + "WHERE state = ?");
            statement.setString(1, UNAVAILABLE);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                players.add(new Player(result.getString("username"), "", result.getString("state"), result.getInt("score")));
            }
            result.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return players;
    }
}
