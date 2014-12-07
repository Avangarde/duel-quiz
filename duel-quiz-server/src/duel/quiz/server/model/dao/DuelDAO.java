/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model.dao;

import duel.quiz.server.model.Duel;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.closeConnection;
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

    public static final String ENDED = "Fini";
    public static final String RUNNING = "En cours";
    public static final String WAITING = "En attente";

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

        String statement1 = "SELECT * "
                + "FROM Duel WHERE turn=? AND status = ?";
        String statement2 = "SELECT * FROM PlayerDuel WHERE duelid = ?";


        PreparedStatement pStatement1;
        PreparedStatement pStatement2;
        try {
            pStatement1 = connection.prepareStatement(statement1);
            pStatement1.setString(1, user);
            pStatement1.setString(2, RUNNING);

            ResultSet result1 = pStatement1.executeQuery();

            while (result1.next()) {
                Duel temp = new Duel();
                temp.setDuelID(result1.getLong("duelId"));
                temp.setStatus(result1.getString("status"));
                //temp.setTurn(result1.getString("turn"));
                //temp.setScorePlayer1(result1.getInt("scorePlayer1"));
                //temp.setScorePlayer2(result1.getInt("scorePlayer2"));

                pStatement2 = connection.prepareStatement(statement2);
                pStatement2.setLong(1, temp.getDuelID());

                ResultSet result2 = pStatement2.executeQuery();
                //This should return two players per due, the first and the second
                while (result2.next()) {
                    if (temp.getPlayer1() == null) {
                        temp.setPlayer1(result2.getString("username"));
                    } else if (temp.getPlayer2() == null) {
                        temp.setPlayer2(result2.getString("username"));
                    }
                }
                result2.close();
                pStatement2.close();

                if (user.equals(temp.getPlayer1())) {
                    temp.setAdversary(temp.getPlayer2());
                } else if (user.equals(temp.getPlayer2())) {
                    temp.setAdversary(temp.getPlayer1());
                }

                duelList.add(temp);
            }

            pStatement1 = connection.prepareStatement(statement1);
            pStatement1.setString(1, user);
            pStatement1.setString(2, WAITING);

            result1 = pStatement1.executeQuery();

            while (result1.next()) {
                Duel temp = new Duel();
                temp.setDuelID(result1.getLong("duelId"));
                temp.setStatus(result1.getString("status"));
                //temp.setTurn(result1.getString("turn"));
                //temp.setScorePlayer1(result1.getInt("scorePlayer1"));
                //temp.setScorePlayer2(result1.getInt("scorePlayer2"));

                pStatement2 = connection.prepareStatement(statement2);
                pStatement2.setLong(1, temp.getDuelID());

                ResultSet result2 = pStatement2.executeQuery();
                //This should return two players per due, the first and the second
                while (result2.next()) {
                    if (temp.getPlayer1() == null) {
                        temp.setPlayer1(result2.getString("username"));
                    } else if (temp.getPlayer2() == null) {
                        temp.setPlayer2(result2.getString("username"));
                    }
                }
                result2.close();
                pStatement2.close();

                if (user.equals(temp.getPlayer1())) {
                    temp.setAdversary(temp.getPlayer2());
                } else if (user.equals(temp.getPlayer2())) {
                    temp.setAdversary(temp.getPlayer1());
                }

                duelList.add(temp);
            }

            result1.close();
            pStatement1.close();
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
        List<Duel> duelList = new ArrayList<>();

        Connection connection = connect();

        String statement1 = "SELECT d.duelid, status, scoreplayer1, scoreplayer2, turn, username "
                + "FROM Duel d, PlayerDuel p WHERE d.DUELID = p.DUELID AND p.USERNAME=? AND d.status = ?";
        String statement2 = "SELECT * FROM PlayerDuel WHERE duelid = ?";


        PreparedStatement pStatement1;
        PreparedStatement pStatement2;

        try {
            pStatement1 = connection.prepareStatement(statement1);
            pStatement1.setString(1, user);
            pStatement1.setString(2, ENDED);

            ResultSet result1 = pStatement1.executeQuery();

            while (result1.next()) {
                Duel temp = new Duel();
                temp.setDuelID(result1.getLong("duelId"));
                temp.setStatus(result1.getString("status"));
                temp.setTurn(result1.getString("turn"));
                temp.setScorePlayer1(result1.getInt("scorePlayer1"));
                temp.setScorePlayer2(result1.getInt("scorePlayer2"));

                pStatement2 = connection.prepareStatement(statement2);
                pStatement2.setLong(1, temp.getDuelID());

                ResultSet result2 = pStatement2.executeQuery();
                //This should return two players per due, the first and the second
                while (result2.next()) {
                    if (temp.getPlayer1() == null) {
                        temp.setPlayer1(result2.getString("username"));
                    } else if (temp.getPlayer2() == null) {
                        temp.setPlayer2(result2.getString("username"));
                    }
                }
                result2.close();
                pStatement2.close();

                if (user.equals(temp.getPlayer1())) {
                    temp.setAdversary(temp.getPlayer2());
                } else if (user.equals(temp.getPlayer2())) {
                    temp.setAdversary(temp.getPlayer1());
                }

                duelList.add(temp);
            }

            pStatement1 = connection.prepareStatement(statement1);
            pStatement1.setString(1, user);
            pStatement1.setString(2, RUNNING);

            result1 = pStatement1.executeQuery();

            while (result1.next()) {
                Duel temp = new Duel();
                temp.setDuelID(result1.getLong("duelId"));
                temp.setStatus(result1.getString("status"));
                temp.setTurn(result1.getString("turn"));
                temp.setScorePlayer1(result1.getInt("scorePlayer1"));
                temp.setScorePlayer2(result1.getInt("scorePlayer2"));

                pStatement2 = connection.prepareStatement(statement2);
                pStatement2.setLong(1, temp.getDuelID());

                ResultSet result2 = pStatement2.executeQuery();
                //This should return two players per due, the first and the second
                while (result2.next()) {
                    if (temp.getPlayer1() == null) {
                        temp.setPlayer1(result2.getString("username"));
                    } else if (temp.getPlayer2() == null) {
                        temp.setPlayer2(result2.getString("username"));
                    }
                }
                result2.close();
                pStatement2.close();

                if (user.equals(temp.getPlayer1())) {
                    temp.setAdversary(temp.getPlayer2());
                } else if (user.equals(temp.getPlayer2())) {
                    temp.setAdversary(temp.getPlayer1());
                }

                duelList.add(temp);
            }

            pStatement1 = connection.prepareStatement(statement1);
            pStatement1.setString(1, user);
            pStatement1.setString(2, WAITING);

            result1 = pStatement1.executeQuery();

            while (result1.next()) {
                Duel temp = new Duel();
                temp.setDuelID(result1.getLong("duelId"));
                temp.setStatus(result1.getString("status"));
                temp.setTurn(result1.getString("turn"));
                temp.setScorePlayer1(result1.getInt("scorePlayer1"));
                temp.setScorePlayer2(result1.getInt("scorePlayer2"));

                pStatement2 = connection.prepareStatement(statement2);
                pStatement2.setLong(1, temp.getDuelID());
                ResultSet result2 = pStatement2.executeQuery();
                //This should return two players per due, the first and the second
                while (result2.next()) {
                    if (temp.getPlayer1() == null) {
                        temp.setPlayer1(result2.getString("username"));
                    } else if (temp.getPlayer2() == null) {
                        temp.setPlayer2(result2.getString("username"));
                    }
                }
                result2.close();
                pStatement2.close();

                if (user.equals(temp.getPlayer1())) {
                    temp.setAdversary(temp.getPlayer2());
                } else if (user.equals(temp.getPlayer2())) {
                    temp.setAdversary(temp.getPlayer1());
                }

                duelList.add(temp);
            }

            result1.close();
            pStatement1.close();
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
}
