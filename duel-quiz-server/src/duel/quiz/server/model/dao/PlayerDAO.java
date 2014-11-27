package duel.quiz.server.model.dao;

import duel.quiz.server.model.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
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
    
    public static boolean setPlayerStatus(String user, boolean isOnline){
        String status;
        if (isOnline){
            status = "AVAILABLE";
        }else{
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

}
