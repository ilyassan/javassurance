package Views;

import Enums.ContractType;
import Models.Client;
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
        println("2. Back to Main Menu");
        print("Enter your choice: ");

        int choice = getIntInput();

        switch(choice) {
            case 1:
                createContract();
                pauseBeforeMenu();
                break;
            case 2:
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

    private static LocalDate parseDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}