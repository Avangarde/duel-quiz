/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model.dao;

import duel.quiz.server.model.Answer;
import duel.quiz.server.model.Question;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.closeConnection;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jummartinezro
 */
public class AnswerDAO extends AbstractDataBaseDAO {

    /**
     * Get the answers of the specified question ID
     *
     * @param questionID
     * @return
     */
    public static List<Answer> getAnswers(long questionID) {
        List<Answer> answers = null;
        Connection connection = connect();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT * FROM Answer WHERE QuestionId = ?");
            ps.setLong(1, questionID);
            try (ResultSet resultSet = ps.executeQuery()) {
                answers = new ArrayList<>();
                while (resultSet.next()) {
                    answers.add(new Answer(
                            resultSet.getInt(1),
                            resultSet.getString(3), resultSet.getBoolean(4),
                            new Question(resultSet.getInt(2), null, null)));
                }
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Answer> retQuestions = new ArrayList<>(4);
        java.util.Random r = new Random();
        for (int i = 0; i < 4; i++) {
            retQuestions.add(answers.remove(r.nextInt(answers.size())));
        }
        return retQuestions;
    }

    public static boolean linkPlayerToAnswer(String user, long answerID) {
        Connection connection = connect();
        boolean ret = false;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PlayerAnswer "
                    + "Values (?,?)");
            statement.setString(1, user);
            statement.setLong(2, answerID);

            statement.executeUpdate();

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

    public static long getByString(String answer) {
        long ret = -1;
        Connection connection = connect();
        PreparedStatement stmnt;
        try {
            stmnt = connection.prepareStatement(
                    "SELECT * FROM Answer WHERE answer = ?");
            stmnt.setString(1, answer);
            ResultSet rslt = stmnt.executeQuery();

            if (rslt.next()) {
                ret = rslt.getLong("answerid");
            }
            rslt.close();
            stmnt.close();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
}
