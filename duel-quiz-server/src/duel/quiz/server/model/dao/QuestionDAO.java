/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model.dao;

import duel.quiz.server.model.Category;
import duel.quiz.server.model.Question;
import static duel.quiz.server.model.dao.AbstractDataBaseDAO.closeConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corteshs
 */
public class QuestionDAO extends AbstractDataBaseDAO {

    public static List<Category> getAllCategories() {
        List<Category> listCategories = new ArrayList<>();
        Connection connection = connect();
        PreparedStatement stmnt;
        try {
            stmnt = connection.prepareStatement(
                    "SELECT * FROM Category"
            );
            ResultSet rslt = stmnt.executeQuery();

            while (rslt.next()) {
                listCategories.add(new Category(rslt.getString("categoryname")));
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
        return listCategories;
    }

    /**
     * Returns a List of 3 Questions given the category
     *
     * @param nameCat
     * @return
     */
    //@TODO at least 3 !
    public static List<Question> getRandomQuestionsByCat(String nameCat) {
        List<Question> questions = null;
        Connection connection = connect();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT * FROM Question WHERE CategoryName = ?");
            ps.setString(1, nameCat);
            try (ResultSet resultSet = ps.executeQuery()) {
                questions = new ArrayList<>();
                while (resultSet.next()) {
                    questions.add(new Question(
                            resultSet.getLong(1),
                            resultSet.getString(3),
                            new Category(resultSet.getString(2))));
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
        List<Question> retQuestions = new ArrayList<>(3);
        java.util.Random r = new Random();
        for (int i = 0; i < 3; i++) {
            retQuestions.add(questions.remove(r.nextInt(questions.size())));
        }
        return retQuestions;
    }

    /**
     * Returns a list of categories with available questions
     * @return
     */
    //@TODO at least 4 !
    public static List<Category> getAllCategoriesWithQuestions() {
        List<Category> listCategories = new ArrayList<>();
        Connection connection = connect();
        PreparedStatement stmnt;
        try {
            stmnt = connection.prepareStatement(
                    "Select DISTINCT CATEGORY.* from CATEGORY,QUESTION WHERE CATEGORY.CATEGORYNAME=QUESTION.CATEGORYNAME"
            );
            ResultSet rslt = stmnt.executeQuery();

            while (rslt.next()) {
                listCategories.add(new Category(rslt.getString("categoryname")));
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
        return listCategories;
    }
}
