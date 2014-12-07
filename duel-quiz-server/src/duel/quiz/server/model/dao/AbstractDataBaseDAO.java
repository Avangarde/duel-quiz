package duel.quiz.server.model.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe abstraite permettant de factoriser du code pour les DAO basées sur
 * JDBC
 *
 * @author Philippe.Genoud@imag.fr
 */
public abstract class AbstractDataBaseDAO {

    private static final String CONN_URL = "jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1";
    private static final String DBUSER = "martijua";
    private static final String PASSWD = "martijua";
    //private static final String CONN_URL = "jdbc:mysql://localhost:3306/duelquiz";
    //private static final String DBUSER = "root";
    //private static final String PASSWD = "mysqlDB";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(CONN_URL, DBUSER, PASSWD);
            return conn;
        } catch (SQLException ex) {
            System.err.println("Failed to connect to the DB");
//            ex.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * fermeture d'une connexion
     *
     * @param c la connexio connexion
     * @throws java.sql.SQLException
     */
    protected static void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            //@TODO: See what happens in server side
            c.close();
        }
    }
}
