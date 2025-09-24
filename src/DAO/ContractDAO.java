package DAO;

import Enums.ContractType;
import Models.Contract;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractDAO extends DAO {
    public Integer save(Contract contract) {
        String sql = "INSERT INTO contracts (type, start_date, end_date, client_id) VALUES (?, ?, ?, ?)";

        return withStatementReturning(sql, stmt -> {
            stmt.setString(1, contract.type.name());
            stmt.setDate(2, Date.valueOf(contract.startDate));
            if (contract.endDate != null) {
                stmt.setDate(3, Date.valueOf(contract.endDate));
            } else {
                stmt.setDate(3, null);
            }
            stmt.setInt(4, contract.clientId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    public Contract findById(int id) {
        String sql = "SELECT * FROM contracts WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Contract(
                        rs.getInt("id"),
                        ContractType.valueOf(rs.getString("type")),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                        rs.getInt("client_id")
                );
            } else {
                return null;
            }
        });
    }

    public List<Contract> findAll() {
        String sql = "SELECT * FROM contracts";

        return withStatement(sql, stmt -> {
            ResultSet rs = stmt.executeQuery();
            List<Contract> contracts = new ArrayList<>();
            while (rs.next()) {
                contracts.add(new Contract(
                        rs.getInt("id"),
                        ContractType.valueOf(rs.getString("type")),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                        rs.getInt("client_id")
                ));
            }
            return contracts;
        });
    }

    public List<Contract> findByClientId(int clientId) {
        String sql = "SELECT * FROM contracts WHERE client_id = ? ORDER BY start_date DESC";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            List<Contract> contracts = new ArrayList<>();
            while (rs.next()) {
                contracts.add(new Contract(
                        rs.getInt("id"),
                        ContractType.valueOf(rs.getString("type")),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                        rs.getInt("client_id")
                ));
            }
            return contracts;
        });
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM contracts WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        });
    }
}