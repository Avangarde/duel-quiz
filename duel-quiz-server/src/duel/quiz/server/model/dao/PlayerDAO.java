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
                            + "WHERE nomUtilisateur = ? AND motDePass = ?");
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                player = new Player(result.getString("nomUtilisateur"), result.getString("motDePass"));
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

}
