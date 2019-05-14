/*
 * Classe responsável por abrir a conexão com o banco de dados
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author luis.junior
 * D.A.O - Data Access Object
 * J.D.B.C - Java DataBase Connection
 */
public class DAO {

    public DAO() {
        // tentativa
        try{
            Class.forName("com.mysql.jdbc.Driver");
        // pegar(exception)
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Driver não encontrado\nErro: " + e);
        }           
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection("jdbc:mysql://localhost:3306/projetoDB",
                        "root", "");
    }
}
