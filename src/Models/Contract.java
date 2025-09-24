package Models;

import Enums.ContractType;
import java.time.LocalDate;

public class Contract {
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
}