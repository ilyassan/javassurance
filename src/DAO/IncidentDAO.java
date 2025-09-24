package DAO;

import Enums.IncidentType;
import Models.Incident;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidentDAO extends DAO {
    public Integer save(Incident incident) {
        String sql = "INSERT INTO incidents (type, date, description, cost, contract_id) VALUES (?, ?, ?, ?, ?)";

        return withStatementReturning(sql, stmt -> {
            stmt.setString(1, incident.type.name());
            stmt.setDate(2, Date.valueOf(incident.date));
            stmt.setString(3, incident.description);
            stmt.setDouble(4, incident.cost);
            stmt.setInt(5, incident.contractId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    public Incident findById(int id) {
        String sql = "SELECT * FROM incidents WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Incident(
                        rs.getInt("id"),
                        IncidentType.valueOf(rs.getString("type")),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getDouble("cost"),
                        rs.getInt("contract_id")
                );
            } else {
                return null;
            }
        });
    }

    public List<Incident> findAll() {
        String sql = "SELECT * FROM incidents";

        return withStatement(sql, stmt -> {
            ResultSet rs = stmt.executeQuery();
            List<Incident> incidents = new ArrayList<>();
            while (rs.next()) {
                incidents.add(
                        new Incident(
                            rs.getInt("id"),
                            IncidentType.valueOf(rs.getString("type")),
                            rs.getDate("date").toLocalDate(),
                            rs.getString("description"),
                            rs.getDouble("cost"),
                            rs.getInt("contract_id")
                    )
                );
            }
            return incidents;
        });
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM incidents WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        });
    }
}