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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corteshs
 */
public class QuestionDAO extends AbstractDataBaseDAO{
    
    public static List<Category> getAllCategories(){
        List<Category> listCategories = new ArrayList<>();
        Connection connection = connect();
        PreparedStatement stmnt;
        try{
            stmnt = connection.prepareStatement(
                    "SELECT * FROM Category"
                    );
            ResultSet rslt = stmnt.executeQuery();
            
            while (rslt.next()){
                listCategories.add(new Category(rslt.getString("categoryname")));
            }
            rslt.close();
            stmnt.close();
        }catch (SQLException ex) {
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

    public static List<Question> getRandomQuestionsByCat(String nameCat1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
