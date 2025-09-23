package Views;

import Enums.ContractType;
import Models.Client;
import Models.Contract;
import Services.ClientService;
import Services.ContractService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ContractView extends View {

    public static void showMenu() {
        println("\n=== CONTRACTS MANAGEMENT ===");
        println("1. Create Contract");
        println("2. Search Contract by ID");
        println("3. Delete Contract by ID");
        println("4. Show All Contracts of a Client");
        println("5. Back to Main Menu");
        print("Enter your choice: ");

        int choice = getIntInput();

        switch(choice) {
            case 1:
                createContract();
                pauseBeforeMenu();
                break;
            case 2:
                searchContract();
                pauseBeforeMenu();
                break;
            case 3:
                deleteContract();
                pauseBeforeMenu();
                break;
            case 4:
                showContractsByClient();
                pauseBeforeMenu();
                break;
            case 5:
                return;
            default:
                println("Invalid choice");
        }
    }

    private static void createContract() {
        // Select contract type
        ContractType contractType = selectContractType();
        if (contractType == null) {
            return;
        }

        // Get start date
        print("Enter start date (yyyy-MM-dd): ");
        String startDateStr = getStringInput();
        LocalDate startDate = parseDate(startDateStr);
        if (startDate == null) {
            println("Invalid date format. Please use yyyy-MM-dd");
            return;
        }

        // Get end date (optional)
        print("Enter end date (yyyy-MM-dd) or press Enter to skip: ");
        String endDateStr = getStringInput();
        LocalDate endDate = null;
        if (!endDateStr.trim().isEmpty()) {
            endDate = parseDate(endDateStr);
            if (endDate == null) {
                println("Invalid date format. Please use yyyy-MM-dd");
                return;
            }
        }

        // Select client
        Client client = selectClient();
        if (client != null) {
            ContractService.create(contractType, startDate, endDate, client.getId());
            println("Contract created successfully!");
        }
    }

    private static void searchContract() {
        print("Enter contract ID: ");
        int contractId = getIntInput();

        if(contractId != -1) {
            Contract contract = ContractService.findById(contractId);

            if(contract != null) {
                println("\n=== CONTRACT FOUND ===");
                println("ID: " + contract.getId());
                println("Type: " + contract.getType().name());
                println("Start Date: " + contract.getStartDate());
                println("End Date: " + (contract.getEndDate() != null ? contract.getEndDate() : "Not specified"));
                println("Client ID: " + contract.getClientId());

                // Also show client details
                Client client = ClientService.findById(contract.getClientId());
                if(client != null) {
                    println("\n=== CLIENT DETAILS ===");
                    println("Client Name: " + client.getFirstName() + " " + client.getFamilyName().orElse("[No family name]"));
                    println("Client Email: " + client.getEmail());
                }
            } else {
                println("Contract with ID " + contractId + " not found.");
            }
        }
    }

    private static void deleteContract() {
        print("Enter contract ID to delete: ");
        int deleteContractId = getIntInput();

        // First show the contract details for confirmation
        Contract contract = ContractService.findById(deleteContractId);

        if(contract != null) {
            println("\n=== CONTRACT TO DELETE ===");
            println("ID: " + contract.getId());
            println("Type: " + contract.getType().name());
            println("Start Date: " + contract.getStartDate());
            println("End Date: " + (contract.getEndDate() != null ? contract.getEndDate() : "Not specified"));

            // Show client details
            Client client = ClientService.findById(contract.getClientId());
            if(client != null) {
                println("Client: " + client.getFirstName() + " " + client.getFamilyName().orElse("[No family name]"));
            }

            print("\nAre you sure you want to delete this contract? (y/N): ");
            String confirmation = getStringInput();

            if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                boolean deleted = ContractService.deleteById(deleteContractId);

                if(deleted) {
                    println("Contract deleted successfully!");
                } else {
                    println("Failed to delete contract.");
                }
            } else {
                println("Deletion cancelled.");
            }
        } else {
            println("Contract with ID " + deleteContractId + " not found.");
        }
    }

    private static void showContractsByClient() {
        // First, let user select a client
        Client selectedClient = selectClient();

        if(selectedClient != null) {
            List<Contract> contracts = ContractService.getContractsByClientId(selectedClient.getId());

            if(contracts.isEmpty()) {
                println("No contracts found for client: " + selectedClient.getFirstName() + " " +
                       selectedClient.getFamilyName().orElse("[No family name]"));
            } else {
                println("\n=== CONTRACTS FOR CLIENT: " + selectedClient.getFirstName() + " " +
                       selectedClient.getFamilyName().orElse("[No family name]") + " ===");
                println("Client Email: " + selectedClient.getEmail());
                println("Total contracts: " + contracts.size());
                println("\n--- CONTRACT DETAILS ---");

                contracts.stream()
                        .forEach(contract -> {
                            println("--------------------");
                            println("Contract ID: " + contract.getId());
                            println("Type: " + contract.getType().name());
                            println("Start Date: " + contract.getStartDate());
                            println("End Date: " + (contract.getEndDate() != null ? contract.getEndDate() : "Not specified"));

                            // Check if contract is active
                            LocalDate today = LocalDate.now();
                            boolean isActive = contract.getStartDate().isBefore(today) || contract.getStartDate().isEqual(today);
                            if (contract.getEndDate() != null) {
                                isActive = isActive && (contract.getEndDate().isAfter(today) || contract.getEndDate().isEqual(today));
                            }
                            println("Status: " + (isActive ? "ACTIVE" : "INACTIVE"));
                        });
                println("--------------------");
            }
        }
    }

    private static ContractType selectContractType() {
        println("\n=== SELECT CONTRACT TYPE ===");
        ContractType[] types = ContractType.values();
        for (int i = 0; i < types.length; i++) {
            println((i + 1) + ". " + types[i].name());
        }
        print("Select a contract type: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= types.length) {
            return types[choice - 1];
        } else {
            println("Invalid selection.");
            return null;
        }
    }

    private static Client selectClient() {
        List<Client> clients = ClientService.getAllOrderedByFamilyName();

        if (clients.isEmpty()) {
            println("No clients found. Please create a client first.");
            return null;
        }

        println("\n=== SELECT CLIENT ===");
        for (int i = 1; i <= clients.size(); i++) {
            Client client = clients.get(i - 1);
            System.out.printf(
                    "%d - %s %s (ID: %d)\n",
                    i,
                    client.getFirstName(),
                    client.getFamilyName().orElse("[No family name]"),
                    client.getId()
            );
        }

        print("Select a client: ");
        int choice = getIntInput();

        if (choice >= 1 && choice <= clients.size()) {
            return clients.get(choice - 1);
        } else {
            println("Invalid selection.");
            return null;
        }
    }
}