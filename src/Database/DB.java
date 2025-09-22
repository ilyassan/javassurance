package Database;

import java.sql.*;

public class DB {
    private static DB instance;
    private final Connection connection;

    private DB() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found. Please add postgresql.jar to classpath.", e);
        }

        String url = "jdbc:postgresql://localhost:5433/mydb";
        String user = "myuser";
        String password = "mypassword";

        this.connection = DriverManager.getConnection(url, user, password);
    }

    public static DB getInstance(){
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DB();
            }
            return instance;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to DB", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
