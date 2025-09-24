package Services;

import DAO.ContractDAO;
import Enums.ContractType;
import Models.Contract;
import java.time.LocalDate;
import java.util.List;

public class ContractService {
    private static final ContractDAO contractDAO = new ContractDAO();

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
        Integer generatedId = contractDAO.save(contract);
        contract.id = generatedId;
    }

    public static Contract findById(int id) {
        return contractDAO.findById(id);
    }

    public static boolean deleteById(int id) {
        Contract contract = contractDAO.findById(id);
        if (contract != null) {
            return contractDAO.deleteById(id);
        } else {
            System.out.println("Contract with ID " + id + " not found.");
            return false;
        }
    }

    public static List<Contract> getAll() {
        return contractDAO.findAll();
    }

    public static List<Contract> getContractsByClientId(int clientId) {
        return contractDAO.findByClientId(clientId);
    }
}