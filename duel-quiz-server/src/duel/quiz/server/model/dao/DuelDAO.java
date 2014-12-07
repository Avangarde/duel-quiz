/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model.dao;

import duel.quiz.server.model.Duel;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corteshs
 */
public class DuelDAO extends AbstractDataBaseDAO {

    public static int create(String status, String activeUser) {

        Connection connection = connect();
        int ret = -1;
        try {
            //Obtain next value in sequence.

            PreparedStatement preStatement = connection.prepareStatement("select Seq_Duel.nextval from USER_SEQUENCES");

            ResultSet rs = preStatement.executeQuery();
            if (rs.next()) {
                ret = rs.getInt(1);
            } else {
                throw new SQLException();
            }


            PreparedStatement statement = connection.prepareStatement(
                    "Insert into DUEL "
                            + "(DUELID,STATUS,SCOREPLAYER1,SCOREPLAYER2,TURN) "
                            + "values (?,?,'1','0',?)");
            statement.setLong(1, ret);
            statement.setString(2, status);
            statement.setString(3, activeUser);

            int res = statement.executeUpdate();


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

            statement.executeUpdate();

            statement.close();
            connection.close();

            ret = true;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Constraint Violation: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return ret;
    }

    public static void updateScore(int duelID, int score, int player) {
        //@TODO don't forget to add the p1hasplayed and p2hasplayed int the db
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static List<Duel> getAllNotifications(String user) {
        List<Duel> duelList = new ArrayList<>();

        Connection connection = connect();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM Duel "
                    + "WHERE turn = ?");
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                duelList.add(new Duel(result.getLong("duelId"), result.getString("status"), result.getInt("scorePlayer1"), result.getInt("scorePlayer2")));
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
        return duelList;



    }

    public static List<Duel> getAllDuels(String user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
