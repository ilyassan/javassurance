package Models;

import java.sql.ResultSet;

public class Advisor extends Person {

    Advisor(Integer id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }

    public void create() {
        String sql = "INSERT INTO advisors (first_name, last_name, email) VALUES (?, ?, ?) RETURNING id";

        this.id = withStatementReturning(sql, stmt -> {
            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setString(3, this.email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return null;
        });
    }

    public static Advisor find(int id) {
        Advisor found = new Advisor(null, null, null, null); // temp object to call base methods

        String sql = "SELECT * FROM advisors WHERE id = ?";

        return found.withStatement(sql, stmt -> {
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
                return null; // Not found
            }
        });
    }
}