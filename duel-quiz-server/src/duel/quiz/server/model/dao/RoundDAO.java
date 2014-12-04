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
public class RoundDAO extends AbstractDataBaseDAO{

    public static int create(int duelID, String name) {
        Connection connection = connect();
        int ret = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Round "
                    + "(duelid, categoryname) "
                    + "Values (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, duelID);
            statement.setString(2, name);

            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                ret = rs.getInt(1);
                System.out.println(rs.getInt(ret));
            }

            statement.close();
            connection.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User already exists");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ret;
    }

    public static boolean linkRoundToQuestion(int duelID, int roundID, long questionID) {
        Connection connection = connect();
        boolean ret = false;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO RoundQuestion "
                    + "Values (?,?,?)");
            statement.setInt(1, duelID);
            statement.setInt(2, roundID);
            statement.setLong(3, questionID);

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
    
}
