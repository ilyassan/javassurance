package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Advisor extends Person {

    public Advisor(String id, String firstName, String lastName, String email) {
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
                return rs.getString("id");
            }
            return null;
        });
    }

    public static Advisor find(int id) {

        String sql = "SELECT * FROM advisors WHERE id = ?";

        return withStatement(sql, stmt -> {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Advisor(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                );
            } else {
                return null; // Not found
            }
        });
    }

    public static List<Advisor> getAll() {
        String sql = "SELECT * FROM advisors";

        return withStatement(sql, stmt -> {
            ResultSet rs = stmt.executeQuery();
            List<Advisor> advisors = new ArrayList<>();
            while (rs.next()) {
                advisors.add(new Advisor(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                ));
            }
            return advisors;
        });
    }
}