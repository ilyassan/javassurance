package Models;

import java.sql.ResultSet;
import java.util.Optional;

public class Client extends Person {
    public Integer advisorId;

    public Client(Integer id, String firstName, String lastName, String email, Integer advisorId) {
        super(id, firstName, lastName, email);
        this.advisorId = advisorId;
    }

    public void create() {
        String sql = "INSERT INTO clients (first_name, last_name, email, advisor_id) VALUES (?, ?, ?, ?)";

        this.id = withStatementReturning(sql, stmt -> {
            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setString(3, this.email);
            stmt.setInt(4, this.advisorId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    public Integer getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Integer advisorId) {
        this.advisorId = advisorId;
    }

    public Optional<String> getFamilyName() {
        return Optional.ofNullable(this.lastName);
    }

    public static Client find(int id) {
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
}