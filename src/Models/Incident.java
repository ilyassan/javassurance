package Models;

import Enums.IncidentType;

import java.time.LocalDateTime;

public class Incident {
    public Integer id;
    public IncidentType type;
    public LocalDateTime date;
    public double cost;
    public String description;
    public Integer contractId;

    Incident(IncidentType type, LocalDateTime date, double cost, String description, Integer contractId) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
