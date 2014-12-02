/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duel.quiz.server.model.dao;

import duel.quiz.server.model.Answer;
import java.util.List;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
