package DAO;

import Models.Advisor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvisorDAO extends DAO {
    public Integer save(Advisor advisor) {
        String sql = "INSERT INTO advisors (first_name, last_name, email) VALUES (?, ?, ?)";

        return withStatementReturning(sql, stmt -> {
            stmt.setString(1, advisor.firstName);
            stmt.setString(2, advisor.lastName);
            stmt.setString(3, advisor.email);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    public Advisor findById(int id) {
        String sql = "SELECT * FROM advisors WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Advisor(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                );
            } else {
                return null;
            }
        });
    }

    public List<Advisor> findAll() {
        String sql = "SELECT * FROM advisors";

        return withStatement(sql, stmt -> {
            ResultSet rs = stmt.executeQuery();
            List<Advisor> advisors = new ArrayList<>();
            while (rs.next()) {
                advisors.add(new Advisor(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                ));
            }
            return advisors;
        });
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM advisors WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        });
    }
}