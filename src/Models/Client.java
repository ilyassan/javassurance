package Models;

import java.sql.ResultSet;

public class Client extends Person {
    public String advisorId;

    public Client(String id, String firstName, String lastName, String email, String advisorId) {
        super(id, firstName, lastName, email);
        this.advisorId = advisorId;
    }

    public void create() {
        String sql = "INSERT INTO clients (first_name, last_name, email, advisor_id) VALUES (?, ?, ?, ?) RETURNING id";

        this.id = withStatementReturning(sql, stmt -> {
            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setString(3, this.email);
            stmt.setString(4, this.advisorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
            return null;
        });
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }
}