import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    private static Connection connection = initializeConnection();

    public static Connection getConnection() {
        return connection;
    }

    private static Connection initializeConnection() {
        String jdbcURL = "jdbc:mysql://localhost:3306/projekt";
        String user = "root";
        String password = "mysql";
        try {

            return DriverManager.getConnection(jdbcURL, user, password);
        } catch (SQLException ex) {
            throw new IllegalStateException("Connection could not be established", ex);
        }
    }
}
