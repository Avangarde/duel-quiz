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

    public Connection connect() {
        try {
            System.out.print("Loading Oracle driver... ");
            //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("loaded");
            // Etablissement de la connection
            System.out.print("Connecting to the database... ");
            Connection conn = DriverManager.getConnection(CONN_URL, DBUSER, PASSWD);
            System.out.println("connected");
            return conn;
        } catch (SQLException ex) {
            System.err.println("failed");
            ex.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * fermeture d'une connexion
     *
     * @param c la connexion à fermer
     * @throws DAOException si problème lors de la fermeture de la connexion
     */
    protected void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            //@TODO: See what happens in server side
            c.close();
        }
    }
}
