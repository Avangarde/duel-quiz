/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model.dao;

import static duel.quiz.server.model.dao.AbstractDataBaseDAO.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

/**
 *
 * @author corteshs
 */
public class DuelDAO extends AbstractDataBaseDAO {

    public static int create(String waiting) {
        
        Connection connection = connect();
        int ret = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Duel "
                    + "(status, scoreplayer1, scoreplayer2) "
                    + "Values (?,'0','0')", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, waiting);

            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                ret = rs.getInt(1);
                System.out.println(rs.getInt(ret));
            }

            statement.close();
            connection.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duel exits violates");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ret;
    }

    public static boolean linkPlayerToDuel(String user, int duelID) {
        Connection connection = connect();
        boolean ret = false;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PlayerDuel "
                    + "Values (?,?)");
            statement.setString(1, user);
            statement.setInt(2, duelID);

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

    public static void updateScore(int duelID) {
        //@TODO
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}