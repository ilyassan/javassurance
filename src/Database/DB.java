package Database;

import java.sql.*;

public class DB {
    private static DB instance;
    private final Connection connection;

    private DB() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/mydb";
        String user = "myuser";
        String password = "mypassword";

        this.connection = DriverManager.getConnection(url, user, password);
    }

    public static DB getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DB();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
