package Models;

import Enums.ContractType;
import Enums.IncidentType;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Incident extends Model {
    public Integer id;
    public IncidentType type;
    public LocalDate date;
    public double cost;
    public String description;
    public Integer contractId;

    public Incident(Integer id, IncidentType type, LocalDate date, String description, double cost, Integer contractId) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.contractId = contractId;
    }

    public Integer getId() {
        return id;
    }

    public IncidentType getType() {
        return type;
    }

    public void setType(IncidentType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }


    public void create() {
        String sql = "INSERT INTO incidents (type, date, description, cost, contract_id) VALUES (?, ?, ?, ?, ?)";

        this.id = withStatementReturning(sql, stmt -> {
            stmt.setString(1, this.type.name());
            stmt.setDate(2, Date.valueOf(this.date));
            stmt.setString(3, this.description);
            stmt.setDouble(4, this.cost);
            stmt.setInt(5, this.contractId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    public static Incident find(int id) {
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
                        rs.getInt("cost"),
                        rs.getInt("contract_id")
                );
            } else {
                return null;
            }
        });
    }

    public static boolean delete(int id) {
        String sql = "DELETE FROM incidents WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        });
    }

    public static List<Incident> getAll() {
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
                            rs.getInt("cost"),
                            rs.getInt("contract_id")
                    )
                );
            }
            return incidents;
        });
    }
}
