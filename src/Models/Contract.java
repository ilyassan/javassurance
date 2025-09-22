package Models;

import Enums.ContractType;
import java.time.LocalDateTime;

public class Contract {
    public String id;
    public ContractType type;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public String clientId;

    public Contract(ContractType type, LocalDateTime startDate, LocalDateTime endDate, String clientId) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}