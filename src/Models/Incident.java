package Models;

import Enums.IncidentType;
import java.time.LocalDate;

public class Incident {
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
}
