package Models;

import Database.DB;

import java.sql.*;

public abstract class Model {
    protected static final DB db = DB.getInstance();

    protected Connection conn() throws SQLException {
        return db.getConnection();
    }

    // Functional interface to wrap PreparedStatement code
    public static interface StatementExecutor<T> {
        T apply(PreparedStatement stmt) throws SQLException;
    }

    protected static <T> T withStatement(String sql, StatementExecutor<T> executor) {
        PreparedStatement stmt = null;
        try {
            stmt = db.getConnection().prepareStatement(sql);
            return executor.apply(stmt);
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static <T> T withStatementReturning(String sql, StatementExecutor<T> executor) {
        PreparedStatement stmt = null;
        try {
            stmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return executor.apply(stmt);
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
