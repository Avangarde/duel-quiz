/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model.dao;

import duel.quiz.server.model.Category;
import duel.quiz.server.model.Duel;
import duel.quiz.server.model.Round;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corteshs
 */
public class RoundDAO extends AbstractDataBaseDAO {

    public static void create(int duelID, int roundID, String name) {
        Connection connection = connect();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "Insert into ROUND "
                    + "(DUELID,ROUNDID,P1HASPLAYED,P2HASPLAYED,CATEGORYNAME) "
                    + "values (?,?,'1','0',?)");
            statement.setInt(1, duelID);
            statement.setInt(2, roundID);
            statement.setString(3, name);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Round already exists");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
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

            statement.executeUpdate();

            statement.close();
            connection.close();

            ret = true;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("The Round in the questions already exists");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * find the max round of the specified duel ID
     *
     * @param duelID
     * @return
     */
    public static Round findMaxRound(int duelID) {
        Connection connection = connect();
        Round r = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ROUND WHERE DUELID = ? HAVING ROUNDID = (SELECT MAX(ROUNDID) FROM ROUND WHERE DUELID=?)");
            ps.setInt(1, duelID);
            ps.setInt(2, duelID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r = new Round(rs.getInt("roundId"), new Duel(rs.getInt("duelId"), null), new Category(rs.getString("categoryName")));
                r.setP1HasPlayed(rs.getBoolean("P1HasPlayed"));
                r.setP2HasPlayed(rs.getBoolean("P2HasPlayed"));
            }
            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RoundDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public static void updateP2(int duelId,int roundId) {
        Connection c = connect();
        try {
            //
            PreparedStatement ps = c.prepareStatement("UPDATE round SET P2HASPLAYED = 1 WHERE DUELID = ? AND ROUNDID = ?");
            ps.setInt(1, duelId);
            ps.setInt(2, roundId);
            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(RoundDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
