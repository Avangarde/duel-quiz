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
            //Obtain next value in sequence.

            PreparedStatement preStatement = connection.prepareStatement("select Seq_Duel.nextval from USER_SEQUENCES");

            ResultSet rs = preStatement.executeQuery();
            if (rs.next()) {
                ret = rs.getInt(1);
            }else{
                throw new SQLException();
            }


            PreparedStatement statement = connection.prepareStatement("INSERT INTO Duel "
                    + "(duelid, status, scoreplayer1, scoreplayer2) "
                    + "Values (?,?,'0','0')");
            statement.setLong(1, ret);
            statement.setString(2, waiting);

            rs = statement.executeQuery();

            
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

    public static void updateScore(int duelID, int score, int player) {
        //@TODO
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
