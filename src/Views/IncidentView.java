package Views;

import Enums.IncidentType;
import Models.Client;
import Models.Contract;
import Services.IncidentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class IncidentView extends View {

    public static void showMenu() {
        println("\n=== Incident MANAGEMENT ===");
        println("1. Create Incident");
        println("5. Back to Main Menu");
        print("Enter your choice: ");

        int choice = getIntInput();

        switch(choice) {
            case 1:
                createIncident();
                pauseBeforeMenu();
                break;
            case 2:
                pauseBeforeMenu();
                break;
            case 3:
                pauseBeforeMenu();
                break;
            case 4:
                pauseBeforeMenu();
                break;
            case 5:
                return;
            default:
                println("Invalid choice");
        }
    }

    private static void createIncident() {
        // Select contract type
        IncidentType incidentType = selectIncidentType();
        if (incidentType == null) {
            return;
        }

        // Get start date
        print("Enter date (yyyy-MM-dd): ");
        String enteredDate = getStringInput();
        LocalDate date = parseDate(enteredDate);
        if (date == null) {
            println("Invalid date format. Please use yyyy-MM-dd");
            return;
        }

        print("Enter cost: ");
        Integer cost = getIntInput();
        if (cost <= 0) {
            println("Invalid cost.");
            return;
        }

        print("Enter a description (optional): ");
        String description = getStringInput();

        // Select contract
        Contract contract = selectContract();
        if (contract != null) {
            IncidentService.create(incidentType, date, description, cost, contract.getId());
            println("Incident created successfully!");
        }
    }

    private static IncidentType selectIncidentType() {
        println("\n=== SELECT INCIDENT TYPE ===");
        IncidentType[] types = IncidentType.values();
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

    private static Contract selectContract() {
        List<Contract> contracts = Contract.getAll();
        List<Client> clients = Client.getAll();

        if (contracts.isEmpty()) {
            println("No contracts found. Please create a contract first.");
            return null;
        }

        println("\n=== SELECT CONTRACT ===");
        for (int i = 1; i <= contracts.size(); i++) {
            Contract contract = contracts.get(i - 1);
            Client client = clients.stream()
                            .filter(cl -> Objects.equals(cl.getId(), contract.getClientId()))
                            .findFirst().orElse(null);
            System.out.printf(
                    "%d - %s | %s %s | Client: %s %s\n",
                    i,
                    contract.getType(),
                    contract.getStartDate(),
                    contract.getEndDate(),
                    client.getFirstName(),
                    client.getLastName()
            );
        }

        print("Select a contract: ");
        int choice = getIntInput();

        if (choice >= 1 && choice <= contracts.size()) {
            return contracts.get(choice - 1);
        } else {
            println("Invalid selection.");
            return null;
        }
    }
}