package Services;

import Enums.ContractType;
import Models.Contract;
import java.time.LocalDate;

public class ContractService {

    public static void create(ContractType type, LocalDate startDate, LocalDate endDate, Integer clientId) {
        // validate the contract information
        if(type == null) {
            System.out.println("Contract type cannot be empty");
            return;
        }
        if(startDate == null) {
            System.out.println("Start date cannot be empty");
            return;
        }
        if(clientId == null) {
            System.out.println("Client ID cannot be empty");
            return;
        }

        // Validate that end date is after start date if provided
        if(endDate != null && endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date");
            return;
        }

        Contract contract = new Contract(null, type, startDate, endDate, clientId);
        contract.create();
    }
}