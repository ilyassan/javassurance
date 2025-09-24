package DAO;

import Models.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends DAO {
    public Integer save(Client client) {
        String sql = "INSERT INTO clients (first_name, last_name, email, advisor_id) VALUES (?, ?, ?, ?)";

        return withStatementReturning(sql, stmt -> {
            stmt.setString(1, client.firstName);
            stmt.setString(2, client.lastName);
            stmt.setString(3, client.email);
            stmt.setInt(4, client.advisorId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getInt("advisor_id")
                );
            } else {
                return null;
            }
        });
    }

    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";

        return withStatement(sql, stmt -> {
            ResultSet rs = stmt.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getInt("advisor_id")
                ));
            }
            return clients;
        });
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        });
    }
}