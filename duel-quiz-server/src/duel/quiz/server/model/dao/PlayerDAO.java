package duel.quiz.server.model.dao;

import duel.quiz.server.model.Player;

import java.sql.*;
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
                player = new Player(result.getString("username"), result.getString("password"), result.getString("state"), result.getInt("score"));
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
            status = "AVAILABLE";
        } else {
            status = "UNAVAILABLE";
        }


        Connection conn = null;
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Player SET state = ?"
                    + "WHERE username = ?");
            stmt.setString(1, status);
            stmt.setString(2, user);
            ResultSet rset = stmt.executeQuery();

            rset.close();
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
            System.out.println("Beginning Persist");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO player VALUES (?, ?, 'AVAILABLE', '0')");
            statement.setString(1, user);
            statement.setString(2, pass);
            System.out.println("Execution");
            statement.executeQuery();
            statement.close();
            System.out.println("Closing");
            connection.close();
            System.out.println("Closed");
            ret = true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User already exists");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return ret;
    }
}
