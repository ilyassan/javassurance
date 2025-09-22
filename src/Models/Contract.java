package Models;

import Enums.ContractType;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Contract extends Model {
    public Integer id;
    public ContractType type;
    public LocalDate startDate;
    public LocalDate endDate;
    public Integer clientId;

    public Contract(Integer id, ContractType type, LocalDate startDate, LocalDate endDate, Integer clientId) {
        this.id = id;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientId = clientId;
    }

    public Integer getId() {
        return id;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void create() {
        String sql = "INSERT INTO contracts (type, start_date, end_date, client_id) VALUES (?, ?, ?, ?)";

        this.id = withStatementReturning(sql, stmt -> {
            stmt.setString(1, this.type.name());
            stmt.setDate(2, Date.valueOf(this.startDate));
            if (this.endDate != null) {
                stmt.setDate(3, Date.valueOf(this.endDate));
            } else {
                stmt.setDate(3, null);
            }
            stmt.setInt(4, this.clientId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        });
    }

    public static Contract find(int id) {
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
}